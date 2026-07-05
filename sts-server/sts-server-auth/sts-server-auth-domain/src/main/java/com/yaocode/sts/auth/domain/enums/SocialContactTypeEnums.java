package com.yaocode.sts.auth.domain.enums;

import lombok.Getter;

/**
 * 社交/第三方联系方式（用于便捷登录/绑定）
 */
@Getter
public enum SocialContactTypeEnums {

    WECHAT(1, "微信"),
    QQ(2, "QQ"),
    ENTERPRISE_WECHAT(3, "企业微信"),
    WEIBO(4, "微博"),
    FEI_SHU(5, "飞书"),
    DING_TALK(6, "钉钉"),
    GITHUB(7, "GitHub"),
    GOOGLE(8, "Google"),
    OTHER(99, "其他");

    private final int code;
    private final String desc;

    SocialContactTypeEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static SocialContactTypeEnums fromCode(int code) {
        for (SocialContactTypeEnums type : values()) {
            if (type.code == code) {
                return type;
            }
        }
        return OTHER;
    }

}
