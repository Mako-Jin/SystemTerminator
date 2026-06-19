package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.yaocode.sts.common.infrastructure.po.BasePo;

/**
 * 组织结构持久化对象
 * @author: Jin-LiangBo
 * @date: 2025年10月14日 19:08
 */
@Data
@TableName("auth_tbl_org_info")
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
     * 组织机构全称
     */
    private String fullName;
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
    private Integer sort;
    /**
     * 父id
     */
    private String parentId;
    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 组织类型（entity/virtual）
     */
    private Integer organizationType;
    /**
     * 编码路径
     */
    private String organizationCodePath;
    /**
     * 层级
     */
    private Integer level;

    /**
     * 是否有子节点
     */
    private Integer hasChild;
    /**
     * 联系人
     */
    private String contact;
    /**
     * 电话
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
     * 状态
     */
    private Integer status;

    /**
     * 是否主组织
     */
    private Integer isPrimary;
    /**
     * 负责人用户ID
     */
    private String managerId;
    /**
     * 负责人姓名
     */
    private String managerName;
    /**
     * 所属公司ID
     */
    private String companyId;
}
