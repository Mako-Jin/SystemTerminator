package com.yaocode.sts.auth.domain.entity;

import com.yaocode.sts.auth.domain.valueobjects.identifiers.ResourceId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.RoleId;
import com.yaocode.sts.common.basic.enums.OppositeEnums;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 角色资源关联实体（属于角色聚合）
 * 对应表：auth_tbl_rel_role_resource
 */
@Getter
public class RoleResourceEntity {

    private final Long relId;              // 关联ID（数据库自增ID）
    private final RoleId roleId;
    private final ResourceId resourceId;
    private OppositeEnums effect;                  // 允许/拒绝
    private Integer priority;              // 优先级
    private final String createUserId;
    private final String createUserName;
    private final LocalDateTime createTime;

    private RoleResourceEntity(Builder builder) {
        this.relId = builder.relId;
        this.roleId = builder.roleId;
        this.resourceId = builder.resourceId;
        this.effect = builder.effect != null ? builder.effect : OppositeEnums.YES;
        this.priority = builder.priority != null ? builder.priority : 0;
        this.createUserId = builder.createUserId;
        this.createUserName = builder.createUserName;
        this.createTime = builder.createTime != null ? builder.createTime : java.time.LocalDateTime.now();
    }

    // ========== 工厂方法 ==========

    public static RoleResourceEntity create(
            RoleId roleId,
            ResourceId resourceId,
            OppositeEnums effect,
            Integer priority
    ) {
        return new Builder()
                .roleId(roleId)
                .resourceId(resourceId)
                .effect(effect)
                .priority(priority)
                .build();
    }

    public static RoleResourceEntity createAllow(RoleId roleId, ResourceId resourceId) {
        return create(roleId, resourceId, OppositeEnums.YES, 0);
    }

    public static RoleResourceEntity createDeny(RoleId roleId, ResourceId resourceId) {
        return create(roleId, resourceId, OppositeEnums.NO, 0);
    }

    public static RoleResourceEntity reconstruct(
            Long relId,
            RoleId roleId,
            ResourceId resourceId,
            OppositeEnums effect,
            Integer priority,
            String createUserId,
            String createUserName,
            java.time.LocalDateTime createTime
    ) {
        return new Builder()
                .relId(relId)
                .roleId(roleId)
                .resourceId(resourceId)
                .effect(effect)
                .priority(priority)
                .createUserId(createUserId)
                .createUserName(createUserName)
                .createTime(createTime)
                .build();
    }

    // ========== 业务行为 ==========

    public void setAllow() {
        this.effect = OppositeEnums.YES;
    }

    public void setDeny() {
        this.effect = OppositeEnums.NO;
    }

    public void updatePriority(Integer priority) {
        this.priority = priority != null ? priority : 0;
    }

    public boolean isAllowed() {
        return effect == OppositeEnums.YES;
    }

    public boolean isDenied() {
        return effect == OppositeEnums.NO;
    }

    // ========== Builder ==========

    public static class Builder {
        private Long relId;
        private RoleId roleId;
        private ResourceId resourceId;
        private OppositeEnums effect;
        private Integer priority;
        private String createUserId;
        private String createUserName;
        private java.time.LocalDateTime createTime;

        public Builder relId(Long relId) {
            this.relId = relId;
            return this;
        }

        public Builder roleId(RoleId roleId) {
            this.roleId = roleId;
            return this;
        }

        public Builder resourceId(ResourceId resourceId) {
            this.resourceId = resourceId;
            return this;
        }

        public Builder effect(OppositeEnums effect) {
            this.effect = effect;
            return this;
        }

        public Builder priority(Integer priority) {
            this.priority = priority;
            return this;
        }

        public Builder createUserId(String createUserId) {
            this.createUserId = createUserId;
            return this;
        }

        public Builder createUserName(String createUserName) {
            this.createUserName = createUserName;
            return this;
        }

        public Builder createTime(java.time.LocalDateTime createTime) {
            this.createTime = createTime;
            return this;
        }

        public RoleResourceEntity build() {
            return new RoleResourceEntity(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleResourceEntity that = (RoleResourceEntity) o;
        return Objects.equals(relId, that.relId) ||
                (roleId != null && resourceId != null &&
                        Objects.equals(roleId, that.roleId) &&
                        Objects.equals(resourceId, that.resourceId));
    }

    @Override
    public int hashCode() {
        return relId != null ? Objects.hash(relId) : Objects.hash(roleId, resourceId);
    }
}