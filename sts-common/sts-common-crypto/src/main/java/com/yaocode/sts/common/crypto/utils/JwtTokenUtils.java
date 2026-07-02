package com.yaocode.sts.common.crypto.utils;

import com.yaocode.sts.common.basic.constants.SymbolConstants;
import com.yaocode.sts.common.crypto.algorithm.encode.Base64Algorithm;
import com.yaocode.sts.common.crypto.algorithm.mac.Hmac512Algorithm;
import com.yaocode.sts.common.crypto.constants.CryptoConstants;
import com.yaocode.sts.common.crypto.constants.CryptoI18nKeyConstants;
import com.yaocode.sts.common.crypto.exception.CryptoException;
import com.yaocode.sts.common.tools.JSONUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * JWT Token 工具类
 * <p>
 * 支持 HMAC-SHA512 算法的 JWT 生成、验证和解析
 * </p>
 *
 * @author yaocode
 * @since 0.0.1
 */
public class JwtTokenUtils {

    /**
     * JWT 分隔符
     */
    private static final String DOT = SymbolConstants.DOT;

    /**
     * 转义后的点号正则表达式
     */
    private static final String DOT_REGEX = Pattern.quote(DOT);

    /**
     * 默认算法名称
     */
    private static final String ALG_HS512 = CryptoConstants.JWT_ALGORITHM_HS512;

    /**
     * 私有构造函数，防止实例化
     */
    private JwtTokenUtils() {
    }

    // ==================== HMAC-SHA512 JWT 方法 ====================

    /**
     * 使用 HMAC-SHA512 算法生成 JWT Token
     *
     * @param payload 载荷数据
     * @param secret  密钥
     * @return JWT Token 字符串
     */
    public static String generateTokenWithHmac512(Map<String, Object> payload, String secret) {
        // 参数校验
        if (payload == null) {
            throw new IllegalArgumentException(CryptoI18nKeyConstants.ERR_JWT_PAYLOAD_EMPTY);
        }
        if (secret == null || secret.isBlank()) {
            throw new IllegalArgumentException(CryptoI18nKeyConstants.ERR_JWT_SECRET_EMPTY);
        }

        try {
            // 1. 构建 JWT 头部
            Map<String, Object> header = new HashMap<>();
            header.put(CryptoConstants.JWT_HEADER_ALG, ALG_HS512);
            header.put(CryptoConstants.JWT_HEADER_TYP, CryptoConstants.JWT_TYPE_VALUE);

            // 2. Base64 URL 编码头部和载荷（无填充）
            String encodedHeader = Base64Algorithm.encodeUrlSafeNoPadding(JSONUtils.toJson(header));
            String encodedPayload = Base64Algorithm.encodeUrlSafeNoPadding(JSONUtils.toJson(payload));

            // 3. 拼接头部和载荷
            String data = encodedHeader + DOT + encodedPayload;

            // 4. 使用 HMAC-SHA512 计算签名
            String signature = Hmac512Algorithm.hmacBase64Url(secret, data);

            // 5. 拼接完整的 JWT
            return data + DOT + signature;
        } catch (Exception e) {
            throw new CryptoException(CryptoI18nKeyConstants.ERR_JWT_SERIALIZE_FAILED, e);
        }
    }


    /**
     * 验证 HMAC-SHA512 JWT Token 的签名
     * @param jwt JWT Token 字符串
     * @param secret 密钥
     * @return 是否有效
     */
    public static boolean verifyHmac512Token(String jwt, String secret) {
        if (jwt == null || jwt.isBlank()) {
            return false;
        }

        String[] parts = jwt.split(DOT_REGEX);
        if (parts.length != 3) {
            return false;
        }

        String headerAndPayload = parts[0] + DOT + parts[1];
        String expectedSignature = parts[2];

        // 计算实际签名并比较
        String actualSignature = Hmac512Algorithm.hmacBase64Url(secret, headerAndPayload);
        return actualSignature.equals(expectedSignature);
    }

    /**
     * 解析 HMAC-SHA512 JWT Token 的载荷（不验证签名）
     *
     * @param jwt JWT Token 字符串
     * @return 载荷 Map
     */
    public static Map<String, Object> parseHmac512Token(String jwt) {
        if (jwt == null || jwt.isBlank()) {
            return null;
        }

        String[] parts = jwt.split(DOT_REGEX);
        if (parts.length != 3) {
            throw new CryptoException(CryptoI18nKeyConstants.ERR_JWT_INVALID_FORMAT);
        }

        try {
            String decodedPayload = Base64Algorithm.decodeUrlSafeNoPaddingToStr(parts[1]);
            return JSONUtils.parseMap(decodedPayload);
        } catch (Exception e) {
            throw new CryptoException(CryptoI18nKeyConstants.ERR_JWT_PARSE_FAILED, e);
        }
    }

    /**
     * 验证并解析 HMAC-SHA512 JWT Token
     * @param jwt JWT Token 字符串
     * @param secret 密钥
     * @return 载荷 Map，验证失败返回 null
     */
    public static Map<String, Object> verifyAndParseHmac512Token(String jwt, String secret) {
        if (verifyHmac512Token(jwt, secret)) {
            return parseHmac512Token(jwt);
        }
        return null;
    }

}