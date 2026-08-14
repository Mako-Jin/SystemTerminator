package com.yaocode.sts.common.infrastructure.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 基础字段
 * @author: Jin-LiangBo
 * @date: 2025年10月14日 19:30
 */
@Data
public class BasePo {

    /**
     * 创建者id
     */
    @TableField(value = "create_user_id", fill = FieldFill.INSERT)
    private String createUserId;
    /**
     * 创建者名
     */
    @TableField(value = "create_user_name", fill = FieldFill.INSERT)
    private String createUserName;
    /**
     * 更新者id
     */
    @TableField(value = "update_user_id", fill = FieldFill.INSERT_UPDATE)
    private String updateUserId;
    /**
     * 更新者名
     */
    @TableField(value = "update_user_name", fill = FieldFill.INSERT_UPDATE)
    private String updateUserName;
    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /** 租户ID（多租户场景自动填充） */
    @TableField(value = "tenant_id", fill = FieldFill.INSERT)
    private String tenantId;

    // ========== 逻辑删除 ==========

    /** 是否删除 0-未删除 1-已删除 */
    @TableLogic
    @TableField(value = "is_deleted", select = false)
    private Integer isDeleted = 0;

}
