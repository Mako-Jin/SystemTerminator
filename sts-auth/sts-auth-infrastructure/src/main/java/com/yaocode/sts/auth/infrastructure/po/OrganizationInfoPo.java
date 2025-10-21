package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 组织结构持久化对象
 * @author: Jin-LiangBo
 * @date: 2025年10月14日 19:08
 */
@Data
@TableName("auth_tbl_org")
@EqualsAndHashCode(callSuper = true)
public class OrganizationInfoPo extends BasePo {

    /**
     * 组织机构id
     */
    @TableId
    private String organizationId;
    /**
     * 组织机构名称
     */
    private String organizationName;
    /**
     * 组织机构编码
     */
    private String organizationCode;
    /**
     * 组织机构描述
     */
    private String organizationDesc;
    /**
     * 排序
     */
    private String sort;
    /**
     * 创建者id
     */
    private String parentId;

}
