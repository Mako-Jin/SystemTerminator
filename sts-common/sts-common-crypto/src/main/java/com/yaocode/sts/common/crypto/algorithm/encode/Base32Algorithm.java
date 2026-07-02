package com.yaocode.sts.common.crypto.algorithm.encode;

import com.yaocode.sts.common.basic.constants.SymbolConstants;
import com.yaocode.sts.common.crypto.constants.CryptoConstants;
import com.yaocode.sts.common.crypto.constants.CryptoI18nKeyConstants;

import java.nio.charset.StandardCharsets;

/**
 * Base32 编码工具类（纯Java实现，零依赖）
 * <p>Base32是一种二进制到文本的编码方式，使用32个字符(A-Z, 2-7)表示</p>
 * <p>特点：天然URL安全、不区分大小写、适合用于TOTP密钥等场景</p>
 * @author: Jin-LiangBo
 * @date: 2026年06月02日
 */
public final class Base32Algorithm {

    /**
     * Base32 标准字符集（RFC 4648）
     */
    private static final String BASE32_CHARS = CryptoConstants.BASE32_CHARS;

    /**
     * Base32 字符数组
     */
    private static final char[] BASE32_CHAR_ARRAY = BASE32_CHARS.toCharArray();

    /**
     * 填充字符
     */
    private static final char PADDING_CHAR = CryptoConstants.BASE32_PADDING_CHAR;

    /**
     * 私有构造函数，防止实例化
     */
    private Base32Algorithm() {
    }

    // ==================== 编码方法 ====================

    /**
     * Base32 编码（字符串输入）
     * @param input 原始字符串
     * @return Base32 编码字符串
     */
    public static String encode(String input) {
        if (input == null) {
            return null;
        }
        return encode(input.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Base32 编码（字节数组输入）
     * @param input 原始字节数组
     * @return Base32 编码字符串
     */
    public static String encode(byte[] input) {
        if (input == null || input.length == 0) {
            return SymbolConstants.EMPTY_STR;
        }

        StringBuilder sb = new StringBuilder();
        int currentByte = 0;
        int bitsRemaining = 0;

        for (byte b : input) {
            currentByte = (currentByte << 8) | (b & 0xFF);
            bitsRemaining += 8;

            while (bitsRemaining >= 5) {
                bitsRemaining -= 5;
                int charIndex = (currentByte >> bitsRemaining) & 0x1F;
                sb.append(BASE32_CHAR_ARRAY[charIndex]);
            }
        }

        // 处理剩余的位
        if (bitsRemaining > 0) {
            currentByte <<= (5 - bitsRemaining);
            int charIndex = currentByte & 0x1F;
            sb.append(BASE32_CHAR_ARRAY[charIndex]);
        }

        // 添加填充字符
        // Base32: 每5字节输入产生8字符输出
        int outputChars = (input.length * 8 + 4) / 5; // 向上取整
        int padding = (8 - (outputChars % 8)) % 8;
        sb.append(String.valueOf(PADDING_CHAR).repeat(padding));

        return sb.toString();
    }

    // ==================== 解码方法 ====================

    /**
     * Base32 解码（返回字节数组）
     * @param input Base32 编码字符串
     * @return 原始字节数组
     */
    public static byte[] decode(String input) {
        if (input == null || input.isEmpty()) {
            return new byte[0];
        }

        // 移除填充字符并转换为大写
        String normalizedInput = input.toUpperCase().replace(String.valueOf(SymbolConstants.EQUAL_SIGN), SymbolConstants.EMPTY_STR);
        int inputLength = normalizedInput.length();

        if (inputLength == 0) {
            return new byte[0];
        }

        int outputLength = (inputLength * 5) / 8;
        byte[] output = new byte[outputLength];
        int index = 0;
        int currentByte = 0;
        int bitsRemaining = 0;

        for (int i = 0; i < normalizedInput.length(); i++) {
            char c = normalizedInput.charAt(i);
            int charValue = BASE32_CHARS.indexOf(c);

            if (charValue < 0) {
                throw new IllegalArgumentException(CryptoI18nKeyConstants.ERR_BASE32_ILLEGAL_CHAR);
            }

            currentByte = (currentByte << 5) | charValue;
            bitsRemaining += 5;

            if (bitsRemaining >= 8) {
                bitsRemaining -= 8;
                output[index++] = (byte) ((currentByte >> bitsRemaining) & 0xFF);
            }
        }

        return output;
    }

    /**
     * Base32 解码（返回字符串）
     * @param input Base32 编码字符串
     * @return 原始字符串
     */
    public static String decodeToString(String input) {
        byte[] decoded = decode(input);
        return new String(decoded, StandardCharsets.UTF_8);
    }

    // ==================== 无填充编码 ====================

    /**
     * Base32 编码（无填充字符）
     * @param input 原始字符串
     * @return Base32 编码字符串（无填充）
     */
    public static String encodeWithoutPadding(String input) {
        String encoded = encode(input);
        return encoded.replace(BASE32_CHARS, SymbolConstants.EMPTY_STR);
    }

    /**
     * Base32 编码（无填充字符）
     * @param input 原始字节数组
     * @return Base32 编码字符串（无填充）
     */
    public static String encodeWithoutPadding(byte[] input) {
        String encoded = encode(input);
        return encoded.replace(BASE32_CHARS, SymbolConstants.EMPTY_STR);
    }
}