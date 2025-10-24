package com.yaocode.sts.auth.domain.entity;

import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.TenantCode;
import com.yaocode.sts.common.domain.model.AbstractAggregate;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 租户信息实体类
 * @author: Jin-LiangBo
 * @date: 2025年10月10日 21:20
 */
@Getter
@Setter
public class TenantInfoEntity extends AbstractAggregate<TenantId> {

    /**
     * 租户名称
     */
    private String tenantName;
    /**
     * 租户编码
     */
    private TenantCode tenantCode;
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
    private Integer allowRegister;
    /**
     * 是否允许注册新用户
     */
    private Integer allowAdd;
    /**
     * 父id
     */
    private String parentId;
    /**
     * 创建者id
     */
    private String createUserId;
    /**
     * 创建者名
     */
    private String createUserName;
    /**
     * 更新者id
     */
    private String updateUserId;
    /**
     * 更新者名
     */
    private String updateUserName;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    public TenantInfoEntity(TenantId tenantId) {
        super(tenantId);
    }

}
