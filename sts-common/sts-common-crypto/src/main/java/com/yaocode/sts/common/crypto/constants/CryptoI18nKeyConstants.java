package com.yaocode.sts.common.crypto.constants;

/**
 * 加密模块国际化消息的key变量
 * @author: Jin-LiangBo
 * @date: 2026年07月01日
 */
public interface CryptoI18nKeyConstants {

    // ==================== 算法分类枚举国际化key ====================
    String ALGORITHM_CATEGORY_HASH = "crypto.algorithm.category.hash";
    String ALGORITHM_CATEGORY_SYMMETRIC = "crypto.algorithm.category.symmetric";
    String ALGORITHM_CATEGORY_ASYMMETRIC = "crypto.algorithm.category.asymmetric";
    String ALGORITHM_CATEGORY_MAC = "crypto.algorithm.category.mac";
    String ALGORITHM_CATEGORY_PRNG = "crypto.algorithm.category.prng";
    String ALGORITHM_CATEGORY_PASSWORD_HASH = "crypto.algorithm.category.password_hash";

    // ==================== 安全等级枚举国际化key ====================
    String SECURITY_LEVEL_HIGH = "crypto.security_level.high";
    String SECURITY_LEVEL_HIGH_ADVICE = "crypto.security_level.high.advice";
    String SECURITY_LEVEL_WEAK = "crypto.security_level.weak";
    String SECURITY_LEVEL_WEAK_ADVICE = "crypto.security_level.weak.advice";
    String SECURITY_LEVEL_INSECURE = "crypto.security_level.insecure";
    String SECURITY_LEVEL_INSECURE_ADVICE = "crypto.security_level.insecure.advice";

    // ==================== 算法类型枚举国际化key ====================
    String ALGORITHM_SHA_256 = "crypto.algorithm.sha_256";
    String ALGORITHM_SHA_512 = "crypto.algorithm.sha_512";
    String ALGORITHM_SM3 = "crypto.algorithm.sm3";
    String ALGORITHM_SHA_1 = "crypto.algorithm.sha_1";
    String ALGORITHM_MD5 = "crypto.algorithm.md5";

    String ALGORITHM_AES_256 = "crypto.algorithm.aes_256";
    String ALGORITHM_AES_128 = "crypto.algorithm.aes_128";
    String ALGORITHM_SM4 = "crypto.algorithm.sm4";
    String ALGORITHM_TRIPLE_DES = "crypto.algorithm.triple_des";
    String ALGORITHM_DES = "crypto.algorithm.des";

    String ALGORITHM_RSA_2048 = "crypto.algorithm.rsa_2048";
    String ALGORITHM_RSA_4096 = "crypto.algorithm.rsa_4096";
    String ALGORITHM_ECC_256 = "crypto.algorithm.ecc_256";
    String ALGORITHM_SM2 = "crypto.algorithm.sm2";
    String ALGORITHM_RSA_1024 = "crypto.algorithm.rsa_1024";

    String ALGORITHM_HMAC_SHA256 = "crypto.algorithm.hmac_sha256";
    String ALGORITHM_HMAC_SHA384 = "crypto.algorithm.hmac_sha384";
    String ALGORITHM_HMAC_SHA512 = "crypto.algorithm.hmac_sha512";
    String ALGORITHM_SHA1PRNG = "crypto.algorithm.sha1prng";

    String ALGORITHM_BCRYPT = "crypto.algorithm.bcrypt";
    String ALGORITHM_SCRYPT = "crypto.algorithm.scrypt";
    String ALGORITHM_ARGON2 = "crypto.algorithm.argon2";
    String ALGORITHM_PBKDF2_SHA256 = "crypto.algorithm.pbkdf2_sha256";

    String ALGORITHM_HS256 = "crypto.algorithm.hs256";
    String ALGORITHM_HS384 = "crypto.algorithm.hs384";
    String ALGORITHM_HS512 = "crypto.algorithm.hs512";

    String ALGORITHM_RS256 = "crypto.algorithm.rs256";
    String ALGORITHM_RS384 = "crypto.algorithm.rs384";
    String ALGORITHM_RS512 = "crypto.algorithm.rs512";

    String ALGORITHM_ES256 = "crypto.algorithm.es256";
    String ALGORITHM_ES384 = "crypto.algorithm.es384";
    String ALGORITHM_ES512 = "crypto.algorithm.es512";

    String ALGORITHM_ED25519 = "crypto.algorithm.ed25519";
    String ALGORITHM_ED448 = "crypto.algorithm.ed448";

    String ALGORITHM_SM2_WITH_SM3 = "crypto.algorithm.sm2_with_sm3";

    // ==================== 实现来源国际化key ====================
    String IMPLEMENTATION_JAVA = "crypto.implementation.java";
    String IMPLEMENTATION_BOUNCY_CASTLE = "crypto.implementation.bouncy_castle";
    String IMPLEMENTATION_SPRING_SECURITY = "crypto.implementation.spring_security";
    String IMPLEMENTATION_JAVA_15 = "crypto.implementation.java_15";

    // ==================== 异常消息国际化key ====================

    // JWT相关
    String ERR_JWT_SERIALIZE_FAILED = "crypto.error.jwt.serialize_failed";
    String ERR_JWT_INVALID_FORMAT = "crypto.error.jwt.invalid_format";
    String ERR_JWT_PARSE_FAILED = "crypto.error.jwt.parse_failed";
    String ERR_JWT_PAYLOAD_EMPTY = "crypto.error.jwt.payload_empty";
    String ERR_JWT_SECRET_EMPTY = "crypto.error.jwt.secret_empty";

    // 密钥相关
    String ERR_KEY_EMPTY = "crypto.error.key.empty";
    String ERR_KEY_INVALID = "crypto.error.key.invalid";
    String ERR_KEY_LENGTH_INVALID = "crypto.error.key.length_invalid";
    String ERR_SM4_KEY_LENGTH_INVALID = "crypto.error.key.sm4_length_invalid";

    // HMAC相关
    String ERR_HMAC_ALGORITHM_UNAVAILABLE = "crypto.error.hmac.algorithm_unavailable";
    String ERR_HMAC_DATA_EMPTY = "crypto.error.hmac.data_empty";

    // 哈希算法相关
    String ERR_HASH_ALGORITHM_UNSUPPORTED = "crypto.error.hash.algorithm_unsupported";

    // 加密/解密相关
    String ERR_AES_GCM_ENCRYPT_FAILED = "crypto.error.aes_gcm.encrypt_failed";
    String ERR_AES_GCM_DECRYPT_FAILED = "crypto.error.aes_gcm.decrypt_failed";
    String ERR_SM4_ENCRYPT_FAILED = "crypto.error.sm4.encrypt_failed";
    String ERR_SM4_DECRYPT_FAILED = "crypto.error.sm4.decrypt_failed";
    String ERR_SM2_SIGN_FAILED = "crypto.error.sm2.sign_failed";
    String ERR_SM2_VERIFY_FAILED = "crypto.error.sm2.verify_failed";

    // Base32相关
    String ERR_BASE32_ILLEGAL_CHAR = "crypto.error.base32.illegal_char";

    // JWT密钥生成相关
    String ERR_JWT_KEY_GENERATE_HMAC_SECRET = "crypto.error.jwt_key.generate_hmac_secret";
    String ERR_JWT_KEY_GENERATE_RSA_PUBLIC = "crypto.error.jwt_key.generate_rsa_public";
    String ERR_JWT_KEY_GENERATE_RSA_PRIVATE = "crypto.error.jwt_key.generate_rsa_private";
    String ERR_JWT_KEY_GENERATE_RSA_KEYPAIR = "crypto.error.jwt_key.generate_rsa_keypair";
    String ERR_JWT_KEY_ALGORITHM_NULL = "crypto.error.jwt_key.algorithm_null";
    String ERR_JWT_KEY_ALGORITHM_UNSUPPORTED = "crypto.error.jwt_key.algorithm_unsupported";

    // Base64相关
    String ERR_BASE64_IMAGE_ENCODE_FAILED = "crypto.error.base64.image_encode_failed";


}
