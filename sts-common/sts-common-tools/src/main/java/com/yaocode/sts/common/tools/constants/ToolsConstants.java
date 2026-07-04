package com.yaocode.sts.common.tools.constants;

/**
 * 工具模块常量类
 * @author: Jin-LiangBo
 * @date: 2026年07月04日
 */
public interface ToolsConstants {

    // ==================== ID生成器常量 ====================
    /**
     * UUID生成器名称
     */
    String UUID_GENERATOR_NAME = "UUID Generator";

    /**
     * 雪花ID生成器名称
     */
    String SNOWFLAKE_GENERATOR_NAME = "Snowflake ID Generator";

    // ==================== 雪花算法常量 ====================
    /**
     * 雪花算法默认工作机器ID
     */
    long DEFAULT_WORKER_ID = 0L;

    /**
     * 雪花算法默认数据中心ID
     */
    long DEFAULT_DATACENTER_ID = 0L;

    /**
     * 雪花算法时间戳位数
     */
    int TIMESTAMP_BITS = 41;

    /**
     * 雪花算法工作机器ID位数
     */
    int WORKER_ID_BITS = 10;

    /**
     * 雪花算法序列号位数
     */
    int SEQUENCE_BITS = 12;

    // ==================== 正则表达式常量 ====================
    /**
     * 纯数字正则表达式
     */
    String REGEX_NUMERIC = "\\d+";

    /**
     * 空白字符正则表达式
     */
    String REGEX_BLANK = "\\s*";
}
