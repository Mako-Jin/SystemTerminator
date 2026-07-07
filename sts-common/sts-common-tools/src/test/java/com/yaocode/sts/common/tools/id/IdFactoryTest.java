package com.yaocode.sts.common.tools.id;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * IdFactory 测试类
 * @author: Jin-LiangBo
 * @date: 2026年07月07日
 */
class IdFactoryTest {

    private static final Logger logger = LoggerFactory.getLogger(IdFactoryTest.class);

    /**
     * 测试默认生成方法 - 应使用雪花算法
     */
    @Test
    void testGenerateWithDefaultType() {
        Long result = IdFactory.generate();
        logger.info("Default generated ID: {}", result);
        assertNotNull(result);
        assertTrue(result > 0);
    }

    /**
     * 测试生成 UUID 类型的 ID
     */
    @Test
    void testGenerateWithUUIDType() {
        String result = IdFactory.generate(IdGeneratorType.UUID);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        // UUID 长度验证（Hutool 的 fastSimpleUUID 返回 32 位字符串）
        assertEquals(32, result.length());
    }

    /**
     * 测试生成雪花算法类型的 ID
     */
    @Test
    void testGenerateWithSnowflakeType() {
        Long result = IdFactory.generate(IdGeneratorType.SNOWFLAKE);

        assertNotNull(result);
        assertTrue(result > 0);
    }

    /**
     * 测试传入 null 类型时使用默认类型（雪花算法）
     */
    @Test
    void testGenerateWithNullType() {
        Long result = IdFactory.generate(null);

        assertNotNull(result);
        assertTrue(result > 0);
    }

    /**
     * 测试多次生成 UUID 应返回不同的值
     */
    @Test
    void testGenerateMultipleUUIDs() {
        String id1 = IdFactory.generate(IdGeneratorType.UUID);
        String id2 = IdFactory.generate(IdGeneratorType.UUID);

        assertNotNull(id1);
        assertNotNull(id2);
        assertNotEquals(id1, id2);
    }

    /**
     * 测试多次生成雪花 ID 应返回不同的值
     */
    @Test
    void testGenerateMultipleSnowflakeIds() {
        Long id1 = IdFactory.generate(IdGeneratorType.SNOWFLAKE);
        Long id2 = IdFactory.generate(IdGeneratorType.SNOWFLAKE);

        assertNotNull(id1);
        assertNotNull(id2);
        assertNotEquals(id1, id2);
    }
}

