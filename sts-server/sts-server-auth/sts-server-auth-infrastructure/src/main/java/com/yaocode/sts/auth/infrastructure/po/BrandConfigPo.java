package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yaocode.sts.common.infrastructure.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("auth_tbl_brand_config")
@EqualsAndHashCode(callSuper = true)
public class BrandConfigPo extends BasePo {

    @TableId
    private String brandId;
    /**
     * 关联目标类型：TENANT, COMPANY, PLATFORM
     */
    private String targetType;

    /**
     * 关联目标ID
     */
    private String targetId;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * Logo URL
     */
    private String logoUrl;

    /**
     * 登录页标题
     */
    private String loginTitle;

    /**
     * 机构名称
     */
    private String institution;

    /**
     * 版权信息
     */
    private String copyright;

    /**
     * 主题颜色
     */
    private String primaryColor;

    /**
     * 登录页背景图
     */
    private String loginBackgroundUrl;

    /**
     * 登录页欢迎语
     */
    private String welcomeMessage;

    /**
     * 是否启用
     */
    private Integer isEnabled;

    /**
     * 优先级（数字越大优先级越高）
     */
    private Integer priority;

    /**
     * 版本号
     */
    private String version;

    /**
     * 登录页副标题
     */
    private String subtitle;

    /**
     * 登录页底部链接文本
     */
    private String footerLinkText;

    /**
     * 登录页底部链接URL
     */
    private String footerLinkUrl;

    /**
     * 是否删除0：没有删，1：被删
     */
    private Integer isDeleted;
}
