package com.yaocode.sts.auth.infrastructure.constants;

/**
 * 认证基础设施模块常量接口
 *
 * @author: yaocode
 * @date: 2025年10月
 */
public interface AuthInfrastructureConstants {

    // ==================== JWT 相关常量 ====================

    /**
     * JWT 标准字段常量
     */
    String CLAIM_ISS = "iss";
    String CLAIM_SUB = "sub";
    String CLAIM_AUD = "aud";
    String CLAIM_IAT = "iat";
    String CLAIM_EXP = "exp";
    String CLAIM_JTI = "jti";

    /**
     * 自定义字段常量
     */
    String CLAIM_USER_ID = "userId";
    String CLAIM_USERNAME = "username";
    String CLAIM_CLIENT_ID = "clientId";
    String CLAIM_DEVICE_ID = "deviceId";
    String CLAIM_SERIES = "series";
    String CLAIM_TOKEN_TYPE = "tokenType";

    /**
     * Token 类型字段名
     */
    String TOKEN_TYPE_FIELD = "token_type";

    // ==================== 异常消息 Key ====================

    /**
     * JWT 配置相关异常
     */
    String ERROR_JWT_ALGORITHM_REQUIRED = "auth.infrastructure.jwt.algorithm.required";
    String ERROR_JWT_ALGORITHM_UNSUPPORTED = "auth.infrastructure.jwt.algorithm.unsupported";
    String ERROR_JWT_ALGORITHM_NOT_NULL = "auth.infrastructure.jwt.algorithm.not.null";
    String ERROR_JWT_TTL_POSITIVE = "auth.infrastructure.jwt.ttl.positive";
    String ERROR_JWT_CONFIG_TYPE_UNSUPPORTED = "auth.infrastructure.jwt.config.type.unsupported";
    String ERROR_JWT_PAYLOAD_NOT_EMPTY = "auth.infrastructure.jwt.payload.not.empty";
    String ERROR_JWT_SECRET_NOT_CONFIGURED = "auth.infrastructure.jwt.secret.not.configured";
    String ERROR_JWT_GENERATION_FAILED = "auth.infrastructure.jwt.generation.failed";

    // ==================== 默认值 ====================

    /**
     * 默认 issuer
     */
    String DEFAULT_ISSUER = "sts";

    /**
     * 默认 audience
     */
    String DEFAULT_AUDIENCE = "sts-api";

    /**
     * 默认 TTL（秒）- 1小时
     */
    long DEFAULT_TTL_SECONDS = 3600L;

    /**
     * 默认过期时间（秒）- 1小时
     */
    long DEFAULT_EXPIRE_SECONDS = 3600L;

    // ==================== 算法类型判断 ====================

    /**
     * RSA 算法前缀
     */
    String RSA_ALGORITHM_PREFIX_RS = "RS";
    String RSA_ALGORITHM_PREFIX_PS = "PS";
    String RSA_ALGORITHM_RSA = "RSA";
    String RSA_ALGORITHM_RSA_OAEP = "RSA_OAEP";

    /**
     * HMAC 算法前缀
     */
    String HMAC_ALGORITHM_PREFIX_HS = "HS";
    String HMAC_ALGORITHM_PREFIX_HMAC = "HMAC";

    // ==================== 配置属性名 ====================

    /**
     * JWT 配置属性名
     */
    String PROPERTY_ALGORITHM = "algorithm";
    String PROPERTY_TTL = "ttl";
    String PROPERTY_ISSUER = "issuer";
    String PROPERTY_AUDIENCE = "audience";
    String PROPERTY_SECRET = "secret";
    String PROPERTY_SECRET_PATH = "secret-path";
    String PROPERTY_PUBLIC_KEY = "public-key";
    String PROPERTY_PRIVATE_KEY = "private-key";
    String PROPERTY_PUBLIC_KEY_PATH = "public-key-path";
    String PROPERTY_PRIVATE_KEY_PATH = "private-key-path";

    // ==================== 时间格式 ====================

    /**
     * ISO-8601 时间格式前缀
     */
    String ISO_8601_PREFIX_P = "P";
    String ISO_8601_PREFIX_PT = "PT";

    /**
     * 简单时间格式后缀
     */
    String DURATION_SUFFIX_SECONDS = "s";
    String DURATION_SUFFIX_MINUTES = "m";
    String DURATION_SUFFIX_HOURS = "h";
    String DURATION_SUFFIX_DAYS = "d";

    /**
     * 语言参数名
     */
    String LANG_PARAM_NAME = "lang";
}