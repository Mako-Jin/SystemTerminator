package com.yaocode.sts.common.crypto.enums;


import lombok.Getter;

/**
 * 加密算法类型枚举
 * 包含哈希算法、对称加密、非对称加密、消息认证码等
 * @author: Jin-LiangBo
 * @date: 2026年06月01日
 */
@Getter
public enum AlgorithmTypeEnums {

    // ==================== 哈希算法 (HASH) ====================

    SHA_256(
            "SHA-256",
            "SHA-256",
            AlgorithmCategoryEnums.HASH,
            SecurityLevelEnums.HIGH,
            true,
            false,
            "安全哈希算法256位",
            "Java标准算法"
    ),

    SHA_512(
            "SHA-512",
            "SHA-512",
            AlgorithmCategoryEnums.HASH,
            SecurityLevelEnums.HIGH,
            true,
            false,
            "安全哈希算法512位",
            "Java标准算法"
    ),

    SM3(
            "SM3",
            "SM3",
            AlgorithmCategoryEnums.HASH,
            SecurityLevelEnums.HIGH,
            true,
            true,
            "国密SM3哈希算法",
            "BouncyCastle"
    ),

    SHA_1(
            "SHA-1",
            "SHA-1",
            AlgorithmCategoryEnums.HASH,
            SecurityLevelEnums.INSECURE,
            false,
            false,
            "安全哈希算法160位（已不安全）",
            "Java标准算法"
    ),

    MD5(
            "MD5",
            "MD5",
            AlgorithmCategoryEnums.HASH,
            SecurityLevelEnums.INSECURE,
            false,
            false,
            "消息摘要算法（已不安全）",
            "Java标准算法"
    ),

    // ==================== 对称加密 (SYMMETRIC) ====================

    AES_256(
            "AES-256",
            "AES",
            AlgorithmCategoryEnums.SYMMETRIC,
            SecurityLevelEnums.HIGH,
            true,
            false,
            "高级加密标准256位",
            "Java标准算法"
    ),

    AES_128(
            "AES-128",
            "AES",
            AlgorithmCategoryEnums.SYMMETRIC,
            SecurityLevelEnums.HIGH,
            true,
            false,
            "高级加密标准128位",
            "Java标准算法"
    ),

    SM4(
            "SM4",
            "SM4",
            AlgorithmCategoryEnums.SYMMETRIC,
            SecurityLevelEnums.HIGH,
            true,
            true,
            "国密SM4对称加密算法",
            "BouncyCastle"
    ),

    TRIPLE_DES(
            "3DES",
            "DESede",
            AlgorithmCategoryEnums.SYMMETRIC,
            SecurityLevelEnums.WEAK,
            false,
            false,
            "三重DES加密（较弱）",
            "Java标准算法"),

    DES(
            "DES",
            "DES",
            AlgorithmCategoryEnums.SYMMETRIC,
            SecurityLevelEnums.INSECURE,
            false,
            false,
            "数据加密标准（已不安全）",
            "Java标准算法"
    ),

    // ==================== 非对称加密 (ASYMMETRIC) ====================

    RSA_2048(
            "RSA-2048",
            "RSA",
            AlgorithmCategoryEnums.ASYMMETRIC,
            SecurityLevelEnums.HIGH,
            true,
            false,
            "RSA算法2048位",
            "Java标准算法"
    ),

    RSA_4096(
            "RSA-4096",
            "RSA",
            AlgorithmCategoryEnums.ASYMMETRIC,
            SecurityLevelEnums.HIGH,
            true,
            false,
            "RSA算法4096位",
            "Java标准算法"
    ),

    ECC_256(
            "ECC-256",
            "EC",
            AlgorithmCategoryEnums.ASYMMETRIC,
            SecurityLevelEnums.HIGH,
            true,
            false,
            "椭圆曲线加密256位",
            "Java标准算法"
    ),

    SM2(
            "SM2",
            "SM2",
            AlgorithmCategoryEnums.ASYMMETRIC,
            SecurityLevelEnums.HIGH,
            true,
            true,
            "国密SM2非对称加密算法",
            "BouncyCastle"
    ),

    RSA_1024(
            "RSA-1024",
            "RSA",
            AlgorithmCategoryEnums.ASYMMETRIC,
            SecurityLevelEnums.INSECURE,
            false,
            false,
            "RSA算法1024位（已不安全）",
            "Java标准算法"
    ),

    // ==================== 消息认证码 (MAC) ====================

    HMAC_SHA256(
            "HMAC-SHA256",
            "HmacSHA256",
            AlgorithmCategoryEnums.MAC,
            SecurityLevelEnums.HIGH,
            true,
            false,
            "基于SHA-256的消息认证码",
            "Java标准算法"
    ),

    HMAC_SHA384(
            "HMAC-SHA384",
            "HmacSHA384",
            AlgorithmCategoryEnums.MAC,
            SecurityLevelEnums.HIGH,
            true,
            false,
            "基于SHA-384的消息认证码",
            "Java标准算法"
    ),

    HMAC_SHA512(
            "HMAC-SHA512",
            "HmacSHA512",
            AlgorithmCategoryEnums.MAC,
            SecurityLevelEnums.HIGH,
            true,
            false,
            "基于SHA-512的消息认证码",
            "Java标准算法"
    ),

    // ==================== 伪随机数生成 (PRNG) ====================

    SHA1PRNG(
            "SHA1PRNG",
            "SHA1PRNG",
            AlgorithmCategoryEnums.PRNG,
            SecurityLevelEnums.HIGH,
            true,
            false,
            "基于SHA-1的伪随机数生成器",
            "Java标准算法"
    ),

    // ==================== 密码哈希算法 (PASSWORD_HASH) ====================

    BCRYPT(
            "BCrypt",
            "BCrypt",
            AlgorithmCategoryEnums.PASSWORD_HASH,
            SecurityLevelEnums.HIGH,
            true,
            false,
            "基于Blowfish的密码哈希算法，自动加盐",
            "Spring Security Crypto"
    ),
    SCRYPT(
            "SCrypt",
            "SCrypt",
            AlgorithmCategoryEnums.PASSWORD_HASH,
            SecurityLevelEnums.HIGH,
            true,
            false,
            "内存密集型密码哈希算法，抗ASIC攻击",
            "Spring Security Crypto"
    ),
    ARGON2(
            "Argon2",
            "Argon2",
            AlgorithmCategoryEnums.PASSWORD_HASH,
            SecurityLevelEnums.HIGH,
            true,
            false,
            "内存密集型密码哈希算法，2015年密码哈希竞赛冠军",
            "BouncyCastle"
    ),
    PBKDF2_SHA256(
            "PBKDF2-SHA256",
            "PBKDF2WithHmacSHA256",
            AlgorithmCategoryEnums.PASSWORD_HASH,
            SecurityLevelEnums.HIGH,
            true,
            false,
            "基于HMAC-SHA256的密钥派生函数，NIST标准",
            "Java标准算法"
    ),

    // ----- HMAC 系列 -----
    HS256(
            "HS256",
            "HmacSHA256",
            AlgorithmCategoryEnums.MAC,
            SecurityLevelEnums.HIGH,
            true,
            false,
            "HMAC with SHA-256 (JWT签名算法)",
            "Java标准算法"
    ),

    HS384(
            "HS384",
            "HmacSHA384",
            AlgorithmCategoryEnums.MAC,
            SecurityLevelEnums.HIGH,
            true,
            false,
            "HMAC with SHA-384 (JWT签名算法)",
            "Java标准算法"
    ),

    HS512(
            "HS512",
            "HmacSHA512",
            AlgorithmCategoryEnums.MAC,
            SecurityLevelEnums.HIGH,
            true,
            false,
            "HMAC with SHA-512 (JWT签名算法)",
            "Java标准算法"
    ),

    // ----- RSA 系列（RSA + SHA）-----
    RS256(
            "RS256",
            "SHA256withRSA",
            AlgorithmCategoryEnums.ASYMMETRIC,
            SecurityLevelEnums.HIGH,
            true,
            false,
            "RSA with SHA-256 (JWT签名算法)，推荐密钥2048位",
            "Java标准算法"
    ),

    RS384(
            "RS384",
            "SHA384withRSA",
            AlgorithmCategoryEnums.ASYMMETRIC,
            SecurityLevelEnums.HIGH,
            true,
            false,
            "RSA with SHA-384 (JWT签名算法)，推荐密钥2048位",
            "Java标准算法"
    ),

    RS512(
            "RS512",
            "SHA512withRSA",
            AlgorithmCategoryEnums.ASYMMETRIC,
            SecurityLevelEnums.HIGH,
            true,
            false,
            "RSA with SHA-512 (JWT签名算法)，推荐密钥2048位",
            "Java标准算法"
    ),

    // ----- ECDSA 系列 -----
    ES256(
            "ES256",
            "SHA256withECDSA",
            AlgorithmCategoryEnums.ASYMMETRIC,
            SecurityLevelEnums.HIGH,
            true,
            false,
            "ECDSA with SHA-256 (JWT签名算法)，推荐密钥256位",
            "Java标准算法"
    ),

    ES384(
            "ES384",
            "SHA384withECDSA",
            AlgorithmCategoryEnums.ASYMMETRIC,
            SecurityLevelEnums.HIGH,
            true,
            false,
            "ECDSA with SHA-384 (JWT签名算法)，推荐密钥384位",
            "Java标准算法"
    ),

    ES512(
            "ES512",
            "SHA512withECDSA",
            AlgorithmCategoryEnums.ASYMMETRIC,
            SecurityLevelEnums.HIGH,
            true,
            false,
            "ECDSA with SHA-512 (JWT签名算法)，推荐密钥521位",
            "Java标准算法"
    ),

    // ----- EdDSA 系列 -----
    ED25519(
            "Ed25519",
            "Ed25519",
            AlgorithmCategoryEnums.ASYMMETRIC,
            SecurityLevelEnums.HIGH,
            true,
            false,
            "Edwards-curve Digital Signature Algorithm (JWT签名算法)，密钥32字节",
            "BouncyCastle/Java 15+"
    ),

    ED448(
            "Ed448",
            "Ed448",
            AlgorithmCategoryEnums.ASYMMETRIC,
            SecurityLevelEnums.HIGH,
            true,
            false,
            "Edwards-curve Digital Signature Algorithm (JWT签名算法)，密钥57字节",
            "BouncyCastle/Java 15+"
    ),

    // ----- 国密系列 -----
    SM2_WITH_SM3(
            "SM2-SM3",
            "SM3withSM2",
            AlgorithmCategoryEnums.ASYMMETRIC,
            SecurityLevelEnums.HIGH,
            true,
            true,
            "国密SM2 with SM3 (JWT签名算法)",
            "BouncyCastle"
    ),
    ;

    // ==================== 枚举字段 ====================

    /**
     * 算法显示名称（如 SHA-256）
     */
    private final String displayName;

    /**
     * Java算法名称（用于MessageDigest/Cipher等API）
     */
    private final String javaAlgorithmName;

    /**
     * 算法分类
     */
    private final AlgorithmCategoryEnums category;

    /**
     * 安全等级
     */
    private final SecurityLevelEnums securityLevelEnums;

    /**
     * 是否推荐使用
     */
    private final boolean recommended;

    /**
     * 是否为国产算法（国密）
     */
    private final boolean chineseStandard;

    /**
     * 算法描述
     */
    private final String description;

    /**
     * 实现来源（Java标准/BouncyCastle等）
     */
    private final String implementationSource;

    // ==================== 构造函数 ====================

    AlgorithmTypeEnums(
            String displayName, String javaAlgorithmName, AlgorithmCategoryEnums category,
            SecurityLevelEnums securityLevelEnums, boolean recommended, boolean chineseStandard,
            String description, String implementationSource
    ) {
        this.displayName = displayName;
        this.javaAlgorithmName = javaAlgorithmName;
        this.category = category;
        this.securityLevelEnums = securityLevelEnums;
        this.recommended = recommended;
        this.chineseStandard = chineseStandard;
        this.description = description;
        this.implementationSource = implementationSource;
    }

    /**
     * 根据显示名称获取枚举
     */
    public static AlgorithmTypeEnums fromDisplayName(String displayName) {
        if (displayName == null || displayName.isBlank()) {
            return null;
        }
        for (AlgorithmTypeEnums algorithm : values()) {
            if (algorithm.displayName.equalsIgnoreCase(displayName)) {
                return algorithm;
            }
        }
        return null;
    }

    /**
     * 根据Java算法名称获取枚举
     */
    public static AlgorithmTypeEnums fromJavaAlgorithmName(String javaName) {
        if (javaName == null || javaName.isBlank()) {
            return null;
        }
        for (AlgorithmTypeEnums algorithm : values()) {
            if (algorithm.javaAlgorithmName.equalsIgnoreCase(javaName)) {
                return algorithm;
            }
        }
        return null;
    }

    /**
     * 获取所有推荐使用的算法
     */
    public static AlgorithmTypeEnums[] getRecommendedAlgorithms() {
        return java.util.Arrays.stream(values())
                .filter(AlgorithmTypeEnums::isRecommended)
                .toArray(AlgorithmTypeEnums[]::new);
    }

    /**
     * 获取所有国密算法
     */
    public static AlgorithmTypeEnums[] getChineseStandardAlgorithms() {
        return java.util.Arrays.stream(values())
                .filter(AlgorithmTypeEnums::isChineseStandard)
                .toArray(AlgorithmTypeEnums[]::new);
    }

    /**
     * 获取指定分类的算法
     */
    public static AlgorithmTypeEnums[] getByCategory(AlgorithmCategoryEnums category) {
        if (category == null) {
            return new AlgorithmTypeEnums[0];
        }
        return java.util.Arrays.stream(values())
                .filter(a -> a.category == category)
                .toArray(AlgorithmTypeEnums[]::new);
    }

}
