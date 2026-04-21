package com.yaocode.sts.common.tools;

import com.yaocode.sts.common.basic.constants.SymbolConstants;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 字符串规则类
 * @author: Jin-LiangBo
 * @date: 2026年01月29日 17:54
 */
public class StringUtils {

    /**
     * 逗号分割的字符串转列表
     * @param str 逗号分割的字符串
     * @return java.util.List<java.lang.String>
     */
    public static List<String> convertStringToList(String str) {
        return convertStringToList(str, SymbolConstants.COMMA);
    }

    /**
     * 分隔符分割的字符串转列表
     * @param str 分隔符分割的字符串
     * @return java.util.List<java.lang.String>
     */
    public static List<String> convertStringToList(String str, String delimiter) {
        if (str == null || str.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.stream(str.split(delimiter))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    /**
     * 字符串不能为空
     * @param str 源字符串
     * @param message 错误消息
     * @return java.lang.String
     */
    public static String requireNonEmpty(String str, String message) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
        return str;
    }

    /**
     * 字符串不能为空，去除空格
     * @param str 源字符串
     * @param message 消息
     * @return java.lang.String
     */
    public static String requireNonBlank(String str, String message) {
        if (str == null || str.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
        return str;
    }

}
