package com.yaocode.sts.common.tools;

import java.util.List;

/**
 * 列表规则类
 * @author: Jin-LiangBo
 * @date: 2026年01月29日 17:46
 */
public class ListUtils {

    /**
     * list默认转换成逗号分割的字符串
     * @param list 列表数据
     * @return java.lang.String
     */
    public static String convertListToString(List<String> list) {
        return convertListToString(list, ",");
    }

    /**
     * list默认转换成分隔符分割的字符串
     * @param list 列表数据
     * @param delimiter 分隔符
     * @return java.lang.String
     */
    public static String convertListToString(List<String> list, String delimiter) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return String.join(delimiter, list);
    }

}
