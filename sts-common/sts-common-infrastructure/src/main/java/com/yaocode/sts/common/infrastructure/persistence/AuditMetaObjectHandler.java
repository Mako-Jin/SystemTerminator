package com.yaocode.sts.common.infrastructure.persistence;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.yaocode.sts.common.domain.context.RequestContextHolder;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import com.yaocode.sts.common.domain.valueobject.UserId;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * MyBatis-Plus 审计字段自动填充处理器
 * <p>
 * 自动填充以下字段：
 * <ul>
 *   <li>createTime / updateTime — 插入时填充，服务器本地时间</li>
 *   <li>createUserId / createUserName — 插入时填充，来自当前请求上下文</li>
 *   <li>updateUserId / updateUserName — 插入和更新时均填充</li>
 *   <li>tenantId — 插入时填充，多租户场景</li>
 * </ul>
 * <p>
 * 字段来源：{@link RequestContextHolder} 统一提供当前请求的用户/租户信息，
 */
public class AuditMetaObjectHandler implements MetaObjectHandler {

    private static final Logger logger = LoggerFactory.getLogger(AuditMetaObjectHandler.class);

    // ==========================================
    // 插入时自动填充
    // ==========================================

    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();

        // ---- 时间字段（插入时同时填充 createTime 和 updateTime） ----
        fillTimeFields(metaObject, now);

        // ---- 用户信息（创建者） ----
        fillUserFields(metaObject, true);

        // ---- 租户信息（多租户场景） ----
        fillTenantField(metaObject);

        // ---- 更新者信息（插入时也需要填充 updateUserId/updateUserName） ----
        fillUserFields(metaObject, false);
    }

    // ==========================================
    // 更新时自动填充
    // ==========================================

    @Override
    public void updateFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();

        // ---- 时间字段 ----
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, now);
        this.strictUpdateFill(metaObject, "update_time", LocalDateTime.class, now);

        // ---- 更新者 ----
        fillUserFields(metaObject, false);
    }

    // ==========================================
    // 私有辅助方法
    // ==========================================

    /**
     * 填充时间字段
     */
    private void fillTimeFields(MetaObject metaObject, LocalDateTime now) {
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, now);
        this.strictInsertFill(metaObject, "create_time", LocalDateTime.class, now);
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, now);
        this.strictInsertFill(metaObject, "update_time", LocalDateTime.class, now);
    }

    /**
     * 填充用户字段
     *
     * @param isCreate true=填充 createUserId/createUserName，false=填充 updateUserId/updateUserName
     */
    private void fillUserFields(MetaObject metaObject, boolean isCreate) {
        UserId userId = RequestContextHolder.getUserId();
        if (Objects.isNull(userId)) {
            logger.warn("审计填充: UserId 为空，使用默认用户");
            if (isCreate) {
                this.strictInsertFill(metaObject, "createUserId", String.class, "default");
                this.strictInsertFill(metaObject, "create_user_id", String.class, "default");
            } else {
                this.strictInsertFill(metaObject, "updateUserId", String.class, "default");
                this.strictInsertFill(metaObject, "update_user_id", String.class, "default");
            }
            return;
        }
        if (isCreate) {
            this.strictInsertFill(metaObject, "createUserId", String.class, userId.getValue());
            this.strictInsertFill(metaObject, "create_user_id", String.class, userId.getValue());
        } else {
            this.strictInsertFill(metaObject, "updateUserId", String.class, userId.getValue());
            this.strictInsertFill(metaObject, "update_user_id", String.class, userId.getValue());
        }

        var username = RequestContextHolder.getUsername();
        if (username != null) {
            String name = username.getValue();
            if (name != null) {
                if (isCreate) {
                    this.strictInsertFill(metaObject, "createUserName", String.class, name);
                    this.strictInsertFill(metaObject, "create_user_name", String.class, name);
                } else {
                    this.strictInsertFill(metaObject, "updateUserName", String.class, name);
                    this.strictInsertFill(metaObject, "update_user_name", String.class, name);
                }
            }
        }
    }

    /**
     * 填充租户字段（多租户场景）
     */
    private void fillTenantField(MetaObject metaObject) {
        TenantId tenantId = RequestContextHolder.getTenantId();
        if (Objects.isNull(tenantId)) {
            logger.warn("审计填充: TenantId 为空，使用默认租户");
            this.strictInsertFill(metaObject, "tenantId", String.class, "default");
            this.strictInsertFill(metaObject, "tenant_id", String.class, "default");
            return;
        }
        this.strictInsertFill(metaObject, "tenantId", String.class, tenantId.getValue());
        this.strictInsertFill(metaObject, "tenant_id", String.class, tenantId.getValue());
    }
}