package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 租户持久化对象
 * @author: Jin-LiangBo
 * @date: 2025年10月10日 20:48
 */

@Data
@TableName("auth_tbl_tenant")
@EqualsAndHashCode(callSuper = true)
public class TenantInfoPo extends BasePo {

    /**
     * 租户id
     */
    @TableId
    private String tenantId;
    /**
     * 租户名称
     */
    private String tenantName;
    /**
     * 租户编码
     */
    private String tenantCode;
    /**
     * 租户描述
     */
    private String tenantDesc;
    /**
     * 租户状态
     */
    private Integer tenantStatus;
    /**
     * 租户状态
     */
    private String tenantLevel;
    /**
     * 是否允许注册新用户
     */
    private String allowRegister;
    /**
     * 父id
     */
    private String parentId;

}
