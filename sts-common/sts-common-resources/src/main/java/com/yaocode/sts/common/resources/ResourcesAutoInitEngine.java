package com.yaocode.sts.common.resources;

import com.yaocode.sts.common.resources.properties.ResourcesConfigProperties;
import com.yaocode.sts.common.resources.services.ResourcesBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 资源自动初始化引擎
 * @author: Jin-LiangBo
 * @date: 2025年11月15日 21:12
 */
public class ResourcesAutoInitEngine implements InitializingBean,  ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(ResourcesAutoInitEngine.class);

    private final Lock reentrantLock = new ReentrantLock();

    protected final ResourcesConfigProperties resourcesConfigProperties;

    private ApplicationContext applicationContext;

    private final ResourcesBuilder resourcesBuilder;

    private static final ExecutorService EXECUTORS = new ThreadPoolExecutor(
            3, 10, 60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(20),
            (ThreadFactory) r -> {
                final AtomicInteger counter = new AtomicInteger(1);

                return new Thread(r, "sts-common-resources-thread-" + counter.getAndIncrement());
            },
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    public ResourcesAutoInitEngine(
            ResourcesConfigProperties resourcesConfigProperties,
            ResourcesBuilder resourcesBuilder
    ) {
        this.resourcesConfigProperties = resourcesConfigProperties;
        this.resourcesBuilder = resourcesBuilder;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        EXECUTORS.submit(this::scanResources);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private void scanResources() {
        this.reentrantLock.lock();
        try {
            Instant start = Instant.now();
            resourcesBuilder.build();
        } finally {
            this.reentrantLock.unlock();
        }
    }

}
