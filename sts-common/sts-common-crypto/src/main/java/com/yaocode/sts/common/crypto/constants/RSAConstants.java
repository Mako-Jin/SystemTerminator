package com.yaocode.sts.common.crypto.constants;

import com.yaocode.sts.common.basic.constants.SymbolConstants;

/**
 * RSA 算法常量接口
 * @author: Jin-LiangBo
 * @date: 2026年07月01日
 */
public interface RSAConstants {

    /**
     * RSA 算法名称
     */
    String RSA = "RSA";

    /**
     * 默认密钥长度（2048位，最小安全要求）
     */
    int DEFAULT_KEY_SIZE = 2048;

    /**
     * 推荐密钥长度（4096位，更高安全性）
     */
    int RECOMMENDED_KEY_SIZE = 4096;

    /**
     * RSA OAEP 加密模式（更安全的填充方式）
     */
    String RSA_OAEP = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";

    /**
     * RSA PKCS#1 v1.5 加密模式（兼容旧系统）
     */
    String RSA_PKCS1 = "RSA/ECB/PKCS1Padding";

    /**
     * SHA-256 with RSA 签名算法
     */
    String SIGNATURE_SHA256_RSA = "SHA256withRSA";

    /**
     * SHA-512 with RSA 签名算法
     */
    String SIGNATURE_SHA512_RSA = "SHA512withRSA";

    /**
     * PEM 格式常量 - 换行符
     */
    String LINE_SEPARATOR = SymbolConstants.LINE_SEPARATOR;

    /**
     * PEM 格式常量 - 每行长度
     */
    int PEM_LINE_LENGTH = 64;

    /**
     * PEM 格式常量 - 公钥开始标记
     */
    String PEM_PUBLIC_KEY_BEGIN = "-----BEGIN PUBLIC KEY-----";

    /**
     * PEM 格式常量 - 公钥结束标记
     */
    String PEM_PUBLIC_KEY_END = "-----END PUBLIC KEY-----";

    /**
     * PEM 格式常量 - 私钥开始标记
     */
    String PEM_PRIVATE_KEY_BEGIN = "-----BEGIN PRIVATE KEY-----";

    /**
     * PEM 格式常量 - 私钥结束标记
     */
    String PEM_PRIVATE_KEY_END = "-----END PRIVATE KEY-----";

}
