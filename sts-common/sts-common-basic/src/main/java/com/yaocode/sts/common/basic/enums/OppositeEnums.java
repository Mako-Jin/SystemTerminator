package com.yaocode.sts.common.basic.enums;

/**
 * 0和1对立面枚举类
 * @author: Jin-LiangBo
 * @date: 2025年10月17日 22:16
 */
public enum OppositeEnums {

    /**
     * 正，已，有
     */
    TRUE(1, "已"),

    /**
     * 反，未，无
     */
    FALSE(0, "未"),

    /**
     * 正，已，有
     */
    YES(1, "是"),

    /**
     * 反，未，无
     */
    NO(0, "否"),

    /**
     * 禁用
     */
    DISABLED(0, "禁用"),
    /**
     * 启用
     */
    ENABLED(1, "启用");
    ;

    private final Integer code;

    private final String desc;

    OppositeEnums(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
