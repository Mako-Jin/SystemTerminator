package com.yaocode.sts.common.crypto.utils;

import com.yaocode.sts.common.crypto.constants.CryptoConstants;

/**
 * 十六进制编码工具类
 * <p>
 * 提供字节数组与十六进制字符串的相互转换，
 * 供加密模块和文件模块等多个模块复用。
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
public final class HexUtils {

    private HexUtils() {
    }

    /**
     * 字节数组转小写十六进制字符串
     *
     * @param bytes 字节数组
     * @return 十六进制字符串（小写）
     */
    public static String bytesToHex(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(String.format(CryptoConstants.HEX_FORMAT, b));
        }
        return sb.toString();
    }

    /**
     * 字节数组转大写十六进制字符串
     *
     * @param bytes 字节数组
     * @return 十六进制字符串（大写）
     */
    public static String bytesToHexUpper(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(String.format(CryptoConstants.HEX_FORMAT_UPPER, b));
        }
        return sb.toString();
    }

    /**
     * 十六进制字符串转字节数组
     *
     * @param hex 十六进制字符串
     * @return 字节数组
     */
    public static byte[] hexToBytes(String hex) {
        if (hex == null || hex.isEmpty()) {
            return new byte[0];
        }
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }
}
