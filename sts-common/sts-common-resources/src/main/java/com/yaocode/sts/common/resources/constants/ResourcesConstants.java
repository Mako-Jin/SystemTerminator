package com.yaocode.sts.common.resources.constants;

/**
 * 常量接口
 * @author: Jin-LiangBo
 * @date: 2025年12月02日 23:14
 */
public interface ResourcesConstants {

    // ==================== 配置属性key ====================
    /**
     * 应用名称的key
     */
    String APPLICATION_NAME_KEY = "spring.application.name";

    /**
     * 应用的全局配置路径
     */
    String SERVLET_CONTEXT_PATH_KEY = "server.servlet.context-path";

    // ==================== 默认值 ====================
    /**
     * 默认资源名称
     */
    String DEFAULT_RESOURCE_NAME = "DefaultResource";

    /**
     * 默认资源描述
     */
    String DEFAULT_RESOURCE_DESC = "默认资源分类";

    /**
     * 默认资源版本
     */
    String DEFAULT_RESOURCE_VERSION = "0.0.0.0";

    // ==================== 资源编码规则 ====================
    /**
     * 系统级别编码长度
     */
    int SYSTEM_CODE_LENGTH = 3;

    /**
     * 服务级别编码长度
     */
    int SERVER_CODE_LENGTH = 6;

    /**
     * 服务接口级别编码长度
     */
    int SERVICE_CODE_LENGTH = 9;

    /**
     * 模块级别编码长度
     */
    int MODULE_CODE_LENGTH = 12;

    /**
     * API级别编码长度
     */
    int API_CODE_LENGTH = 15;

    // ==================== 线程池配置 ====================
    /**
     * 资源扫描线程池前缀
     */
    String THREAD_POOL_NAME_PREFIX = "sts-common-resources-thread-";

    /**
     * 线程池核心线程数
     */
    int THREAD_POOL_CORE_SIZE = 3;

    /**
     * 线程池最大线程数
     */
    int THREAD_POOL_MAX_SIZE = 10;

    /**
     * 线程池空闲超时时间（秒）
     */
    long THREAD_POOL_KEEP_ALIVE_SECONDS = 60L;

    /**
     * 线程池队列容量
     */
    int THREAD_POOL_QUEUE_CAPACITY = 20;
}
