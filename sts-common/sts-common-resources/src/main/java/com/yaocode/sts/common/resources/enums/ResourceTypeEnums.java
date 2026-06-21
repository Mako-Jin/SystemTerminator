package com.yaocode.sts.common.resources.enums;

import com.yaocode.sts.common.resources.annotation.ApiResources;
import com.yaocode.sts.common.resources.annotation.ModuleResources;
import com.yaocode.sts.common.resources.annotation.ServerResources;
import com.yaocode.sts.common.resources.annotation.ServiceResources;
import com.yaocode.sts.common.resources.annotation.SystemResources;
import lombok.Getter;

/**
 * 资源类型枚举类
 * @author: Jin-LiangBo
 * @date: 2025年11月15日 15:21
 */
@Getter
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
     * 菜单
     */
    MENUS(4, "菜单"),

    /**
     * 页面
     */
    PAGES(5, "页面"),

    /**
     * 接口
     */
    API(6, "接口"),

    /**
     * 按钮
     */
    BUTTON(7, "按钮"),

    /**
     * 数据
     */
    DATA(8, "数据"),
    /**
     * 文件
     */
    FILE(9, "文件"),
    ;

    private final Integer code;

    private final String desc;

    ResourceTypeEnums(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String createDefaultResourceCode(Class<?> clazz) {
        if (SystemResources.class.equals(clazz)) {
            return "0".repeat(3);
        } else if (ServerResources.class.equals(clazz)) {
            return "0".repeat(6);
        } else if (ServiceResources.class.equals(clazz)) {
            return "0".repeat(9);
        } else if (ModuleResources.class.equals(clazz)) {
            return "0".repeat(12);
        }  else if (ApiResources.class.equals(clazz)) {
            return "0".repeat(15);
        }
        throw new IllegalArgumentException();
    }

    public static ResourceTypeEnums fromCode(Integer code) {
        for (ResourceTypeEnums type : ResourceTypeEnums.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown resource type: " + code);
    }

    public boolean isMenu() {
        return this == PAGES || this == MODULE;
    }

    public boolean isApi() {
        return this == API;
    }

}
