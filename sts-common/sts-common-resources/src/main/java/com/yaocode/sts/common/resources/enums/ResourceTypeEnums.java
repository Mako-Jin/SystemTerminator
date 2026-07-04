package com.yaocode.sts.common.resources.enums;

import com.yaocode.sts.common.resources.annotation.ApiResources;
import com.yaocode.sts.common.resources.annotation.ModuleResources;
import com.yaocode.sts.common.resources.annotation.ServerResources;
import com.yaocode.sts.common.resources.annotation.ServiceResources;
import com.yaocode.sts.common.resources.annotation.SystemResources;
import com.yaocode.sts.common.resources.constants.ResourcesConstants;
import com.yaocode.sts.common.resources.constants.ResourcesI18nKeyConstants;
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
    SYSTEM(0, ResourcesI18nKeyConstants.RESOURCE_TYPE_SYSTEM),

    /**
     * 服务
     */
    SERVER(1, ResourcesI18nKeyConstants.RESOURCE_TYPE_SERVER),

    /**
     * 服务
     */
    SERVICE(2, ResourcesI18nKeyConstants.RESOURCE_TYPE_SERVICE),

    /**
     * 模块
     */
    MODULE(3, ResourcesI18nKeyConstants.RESOURCE_TYPE_MODULE),

    /**
     * 菜单
     */
    MENUS(4, ResourcesI18nKeyConstants.RESOURCE_TYPE_MENUS),

    /**
     * 页面
     */
    PAGES(5, ResourcesI18nKeyConstants.RESOURCE_TYPE_PAGES),

    /**
     * 接口
     */
    API(6, ResourcesI18nKeyConstants.RESOURCE_TYPE_API),

    /**
     * 按钮
     */
    BUTTON(7, ResourcesI18nKeyConstants.RESOURCE_TYPE_BUTTON),

    /**
     * 数据
     */
    DATA(8, ResourcesI18nKeyConstants.RESOURCE_TYPE_DATA),
    /**
     * 文件
     */
    FILE(9, ResourcesI18nKeyConstants.RESOURCE_TYPE_FILE),
    ;

    private final Integer code;

    private final String desc;

    ResourceTypeEnums(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String createDefaultResourceCode(Class<?> clazz) {
        if (SystemResources.class.equals(clazz)) {
            return "0".repeat(ResourcesConstants.SYSTEM_CODE_LENGTH);
        } else if (ServerResources.class.equals(clazz)) {
            return "0".repeat(ResourcesConstants.SERVER_CODE_LENGTH);
        } else if (ServiceResources.class.equals(clazz)) {
            return "0".repeat(ResourcesConstants.SERVICE_CODE_LENGTH);
        } else if (ModuleResources.class.equals(clazz)) {
            return "0".repeat(ResourcesConstants.MODULE_CODE_LENGTH);
        } else if (ApiResources.class.equals(clazz)) {
            return "0".repeat(ResourcesConstants.API_CODE_LENGTH);
        }
        throw new IllegalArgumentException();
    }

    public static ResourceTypeEnums fromCode(Integer code) {
        for (ResourceTypeEnums type : ResourceTypeEnums.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException(ResourcesI18nKeyConstants.ERR_UNKNOWN_RESOURCE_TYPE);
    }

    public boolean isMenu() {
        return this == PAGES || this == MODULE;
    }

    public boolean isApi() {
        return this == API;
    }

}
