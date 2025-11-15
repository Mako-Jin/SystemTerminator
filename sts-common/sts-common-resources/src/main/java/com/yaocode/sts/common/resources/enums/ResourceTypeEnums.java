package com.yaocode.sts.common.resources.enums;

/**
 * 资源类型枚举类
 * @author: Jin-LiangBo
 * @date: 2025年11月15日 15:21
 */
public enum ResourceTypeEnums {

    /**
     * 系统
     */
    SYSTEM(0, "系统"),

    /**
     * 服务
     */
    SERVER(1, "服务"),

    /**
     * 服务
     */
    SERVICE(2, "服务"),

    /**
     * 模块
     */
    MODULE(3, "模块"),

    /**
     * 页面
     */
    PAGES(4, "页面"),

    /**
     * 接口
     */
    API(5, "接口"),

    /**
     * 数据
     */
    DATA(6, "数据"),
    ;

    private final Integer code;

    private final String desc;

    ResourceTypeEnums(Integer code, String desc) {
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
