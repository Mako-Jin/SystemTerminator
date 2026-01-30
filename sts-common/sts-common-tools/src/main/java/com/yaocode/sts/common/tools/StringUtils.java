package com.yaocode.sts.common.tools;

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
        return convertStringToList(str, ",");
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

}
