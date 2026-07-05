package com.yaocode.sts.components.flow.core.job;

/**
 * 作业调度器
 * 类似 Camunda 的 JobExecutor
 *
 * 参考 Camunda: org.camunda.bpm.engine.impl.jobexecutor.JobExecutor
 */
@Slf4j
public class JobScheduler {

    private final ProcessEngine processEngine;
    private final JobRepository jobRepository;
    private final JobExecutor jobExecutor;

    // 调度配置
    private final long scanInterval;      // 扫描间隔（毫秒）
    private final int maxJobsPerScan;     // 每次扫描最大作业数
    private final int corePoolSize;       // 核心线程数
    private final int maxPoolSize;        // 最大线程数
    private final long keepAliveTime;     // 线程存活时间

    // 线程池
    private ExecutorService executorService;
    private ScheduledExecutorService schedulerService;

    // 运行状态
    private final AtomicBoolean running = new AtomicBoolean(false);
    private ScheduledFuture<?> scheduledFuture;

    public JobScheduler(ProcessEngine processEngine) {
        this.processEngine = processEngine;
        this.jobRepository = processEngine.getJobRepository();
        this.jobExecutor = new JobExecutor(processEngine);

        // 从配置读取参数
        ProcessEngineConfiguration config = processEngine.getConfiguration();
        this.scanInterval = config.getJobScanInterval();
        this.maxJobsPerScan = config.getBatchSize();
        this.corePoolSize = config.getJobExecutorThreadPoolSize();
        this.maxPoolSize = corePoolSize * 2;
        this.keepAliveTime = 60L;

        initThreadPools();
    }

    /**
     * 初始化线程池
     */
    private void initThreadPools() {
        // 执行线程池
        this.executorService = new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                keepAliveTime,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1000),
                new ThreadFactory() {
                    private int counter = 0;

                    @Override
                    public Thread newThread(Runnable r) {
                        Thread t = new Thread(r, "job-executor-" + (++counter));
                        t.setDaemon(true);
                        return t;
                    }
                },
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        // 调度线程池（单线程）
        this.schedulerService = Executors.newSingleThreadScheduledExecutor(
                r -> {
                    Thread t = new Thread(r, "job-scheduler");
                    t.setDaemon(true);
                    return t;
                }
        );
    }

    /**
     * 启动调度器
     */
    public void start() {
        if (running.compareAndSet(false, true)) {
            log.info("JobScheduler 启动，扫描间隔={}ms", scanInterval);
            scheduledFuture = schedulerService.scheduleAtFixedRate(
                    this::scanAndExecute,
                    scanInterval,
                    scanInterval,
                    TimeUnit.MILLISECONDS
            );
        }
    }

    /**
     * 停止调度器
     */
    public void stop() {
        if (running.compareAndSet(true, false)) {
            log.info("JobScheduler 停止");

            if (scheduledFuture != null) {
                scheduledFuture.cancel(true);
                scheduledFuture = null;
            }

            if (schedulerService != null) {
                schedulerService.shutdown();
                try {
                    if (!schedulerService.awaitTermination(5, TimeUnit.SECONDS)) {
                        schedulerService.shutdownNow();
                    }
                } catch (InterruptedException e) {
                    schedulerService.shutdownNow();
                    Thread.currentThread().interrupt();
                }
            }

            if (executorService != null) {
                executorService.shutdown();
                try {
                    if (!executorService.awaitTermination(30, TimeUnit.SECONDS)) {
                        executorService.shutdownNow();
                    }
                } catch (InterruptedException e) {
                    executorService.shutdownNow();
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    /**
     * 扫描并执行待处理作业
     */
    private void scanAndExecute() {
        if (!running.get()) {
            return;
        }

        try {
            // 1. 清理过期锁
            cleanExpiredLocks();

            // 2. 获取待执行作业
            List<Job> jobs = jobRepository.findDueJobs(LocalDateTime.now(), maxJobsPerScan);

            if (jobs.isEmpty()) {
                return;
            }

            log.debug("扫描到 {} 个待执行作业", jobs.size());

            // 3. 提交到执行线程池
            for (Job job : jobs) {
                submitJob(job);
            }

        } catch (Exception e) {
            log.error("作业扫描执行异常", e);
        }
    }

    /**
     * 提交作业到执行线程池
     */
    private void submitJob(Job job) {
        executorService.submit(() -> {
            try {
                // 锁定作业
                boolean locked = jobRepository.lockJob(
                        job.getJobId(),
                        getLockOwner(),
                        LocalDateTime.now().plusSeconds(60)
                );

                if (!locked) {
                    log.debug("作业已被其他节点锁定: jobId={}", job.getJobId());
                    return;
                }

                // 执行作业
                jobExecutor.execute(job);

            } catch (Exception e) {
                log.error("作业执行异常: jobId={}", job.getJobId(), e);
                // 失败处理由 JobExecutor 内部处理
            }
        });
    }

    /**
     * 清理过期锁
     */
    private void cleanExpiredLocks() {
        try {
            int cleaned = jobRepository.cleanupExpiredLocks(LocalDateTime.now());
            if (cleaned > 0) {
                log.debug("清理了 {} 个过期锁", cleaned);
            }
        } catch (Exception e) {
            log.error("清理过期锁异常", e);
        }
    }

    /**
     * 获取锁定者标识（节点ID）
     */
    private String getLockOwner() {
        return processEngine.getConfiguration().getNodeId() != null
                ? processEngine.getConfiguration().getNodeId()
                : "node-" + System.getProperty("pid", "unknown");
    }

    /**
     * 手动触发扫描（用于测试或紧急触发）
     */
    public void triggerScan() {
        scanAndExecute();
    }

    /**
     * 立即执行指定作业（忽略调度）
     */
    public void executeImmediately(String jobId) {
        Job job = jobRepository.findById(jobId);
        if (job != null) {
            executorService.submit(() -> {
                jobExecutor.execute(job);
            });
        }
    }

    /**
     * 获取队列大小
     */
    public int getQueueSize() {
        if (executorService instanceof ThreadPoolExecutor) {
            return ((ThreadPoolExecutor) executorService).getQueue().size();
        }
        return 0;
    }

    /**
     * 获取活跃线程数
     */
    public int getActiveCount() {
        if (executorService instanceof ThreadPoolExecutor) {
            return ((ThreadPoolExecutor) executorService).getActiveCount();
        }
        return 0;
    }

    /**
     * 是否正在运行
     */
    public boolean isRunning() {
        return running.get();
    }
}
