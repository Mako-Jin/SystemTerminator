package com.yaocode.sts.common.tools;

/**
 * 数字工具类
 * @author: Jin-LiangBo
 * @date: 2026年02月12日 16:47
 */
public class NumberUtils {

    /**
     * 检查字符串是不是纯数字
     * @param str 目标字符串
     * @return boolean
     */
    public static boolean isNumeric(String str) {
        return str != null && str.matches("\\d+");
    }

}
