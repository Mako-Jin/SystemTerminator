package com.yaocode.sts.common.tools.id;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;


/**
 * id 工厂
 * @author: Jin-LiangBo
 * @date: 2025年10月12日 12:31
 */
public class IdFactory {

    private final static IdGeneratorType DEFAULT_TYPE = IdGeneratorType.SNOWFLAKE;

    private final static Map<IdGeneratorType, IdGenerator<?>> GENERATORS = new ConcurrentHashMap<>() {{
        put(IdGeneratorType.UUID, new UUIDGenerator());
        put(IdGeneratorType.SNOWFLAKE, new SnowflakeIdGenerator());
    }};

    public static <T> T generate() {
        return generate(DEFAULT_TYPE);
    }

    @SuppressWarnings("unchecked")
    public static <T> T generate(IdGeneratorType type) {
        if (Objects.isNull(type)) {
            type = DEFAULT_TYPE;
        }
        IdGenerator<T> generator = (IdGenerator<T>) GENERATORS.get(type);
        if (generator == null) {
            throw new IllegalArgumentException("No generator found for type: " + type);
        }
        return generator.generate();
    }

}
