package com.yaocode.sts.common.crypto.constants;

import com.yaocode.sts.common.basic.constants.SymbolConstants;

/**
 * 加密模块通用常量接口
 * @author: Jin-LiangBo
 * @date: 2026年07月01日
 */
public interface CryptoConstants {

    // ==================== Base32 常量 ====================
    String BASE32_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567";
    char BASE32_PADDING_CHAR = SymbolConstants.EQUAL_SIGN;
    int BASE32_BITS_PER_CHAR = 5;
    int BASE32_CHARS_PER_LINE = 64;

    // ==================== Base64 常量 ====================
    int BASE64_LINE_LENGTH = 76;

    // ==================== 哈希算法常量 ====================
    String SHA_256 = "SHA-256";
    String SHA_384 = "SHA-384";
    String SHA_512 = "SHA-512";

    // ==================== SM2 常量 ====================
    String SM2_CURVE_NAME = "sm2p256v1";
    String SM2_DEFAULT_USER_ID = "1234567812345678";

    // ==================== SM3 常量 ====================
    int SM3_DIGEST_LENGTH = 32;  // 字节

    // ==================== SM4 常量 ====================
    String SM4 = "SM4";
    int SM4_KEY_SIZE = 16;  // 位
    int SM4_BLOCK_SIZE = 16;  // 字节
    int SM4_IV_LENGTH = 16;      // 字节

    // ==================== AES 常量 ====================
    String AES = "AES";
    String AES_GCM = "AES/GCM/NoPadding";
    int AES_GCM_TAG_LENGTH = 128;  // 位
    int AES_GCM_IV_LENGTH = 12;    // 字节
    int AES_128_KEY_SIZE = 16;     // 字节
    int AES_256_KEY_SIZE = 32;     // 字节

    // ==================== HMAC 常量 ====================
    String HMAC_SHA512 = "HmacSHA512";
    int HMAC_SHA512_OUTPUT_LENGTH = 64;         // 字节
    int HMAC_SHA512_RECOMMENDED_KEY_LENGTH = 64; // 字节
    int HMAC_SHA512_MIN_KEY_LENGTH = 32;        // 字节


    // ==================== 编码常量 ====================
    String UTF_8 = "UTF-8";

    // ==================== JWT 常量 ====================
    String JWT_ALGORITHM_HS256 = "HS256";
    String JWT_ALGORITHM_HS384 = "HS384";
    String JWT_ALGORITHM_HS512 = "HS512";
    String JWT_ALGORITHM_RS256 = "RS256";
    String JWT_ALGORITHM_RS384 = "RS384";
    String JWT_ALGORITHM_RS512 = "RS512";
    String JWT_ALGORITHM_ES256 = "ES256";
    String JWT_ALGORITHM_ES384 = "ES384";
    String JWT_ALGORITHM_ES512 = "ES512";
    String JWT_ALGORITHM_ED25519 = "Ed25519";
    String JWT_ALGORITHM_SM2 = "SM2";

    // ==================== JWT 相关常量 ====================
    String JWT_HEADER_ALG = "alg";
    String JWT_HEADER_TYP = "typ";
    String JWT_TYPE_VALUE = "JWT";

    // ==================== 字符常量 ====================
    String UTF8_BOM = "\uFEFF";

    String HEX_FORMAT = "%02x";
    String HEX_FORMAT_UPPER = "%02X";  // 大写版本

}
