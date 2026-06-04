package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yaocode.sts.common.infrastructure.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("auth_tbl_company")
@EqualsAndHashCode(callSuper = true)
public class CompanyInfoPo extends BasePo {

    /**
     * 公司id
     */
    @TableId
    private String companyId;
    /**
     * 关联租户
     */
    private String tenantId;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 公司全称
     */
    private String fullName;
    /**
     * 国家
     */
    private String country;

    /**
     * 地区/省份
     */
    private String region;

    /**
     * 城市
     */
    private String locality;

    /**
     * 街道
     */
    private String street;

    /**
     * 详细地址
     */
    private String address;
    /**
     * 联系人
     */
    private String contact;

    /**
     * 邮政编码
     */
    private String postalCode;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 传真
     */
    private String fax;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 公司描述
     */
    private String description;

    /**
     * 公司Logo
     */
    private String logo;

    /**
     * 域名
     */
    private String domain;

    /**
     * 前端标题
     */
    private String frontTitle;

    /**
     * 控制台域名
     */
    private String consoleDomain;

    /**
     * 控制台标题
     */
    private String consoleTitle;

    /**
     * 默认URI
     */
    private String defaultUri;
}
