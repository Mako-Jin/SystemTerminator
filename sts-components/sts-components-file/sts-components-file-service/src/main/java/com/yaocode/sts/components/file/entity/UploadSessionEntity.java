package com.yaocode.sts.components.file.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 上传会话表
 */
@Data
@TableName("upload_session")
public class UploadSessionEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    @TableField("session_id")
    private Long sessionId;

    /**
     * 上传会话ID
     */
    @TableField("upload_id")
    private String uploadId;

    /**
     * 文件ID
     */
    @TableField("file_id")
    private String fileId;

    /**
     * 存储类型
     */
    @TableField("storage_type")
    private String storageType;

    /**
     * 总分片数
     */
    @TableField("total_chunks")
    private Integer totalChunks;

    /**
     * 每个分片大小(字节)
     */
    @TableField("chunk_size")
    private Long chunkSize;

    /**
     * 已完成分片数
     */
    @TableField("completed_chunks")
    private Integer completedChunks;

    /**
     * 会话状态: 0-进行中 1-已完成 2-已取消 3-已超时
     */
    @TableField("upload_status")
    private Integer uploadStatus;

    /**
     * 最后活跃时间
     */
    @TableField("last_active_time")
    private LocalDateTime lastActiveTime;

    /**
     * 过期时间
     */
    @TableField("expire_time")
    private LocalDateTime expireTime;

    // ========== 审计信息 ==========

    /**
     * 创建人ID
     */
    @TableField("created_user_id")
    private String createdUserId;

    /**
     * 创建人名称
     */
    @TableField("update_user_name")
    private String createdUserName;

    /**
     * 更新人ID
     */
    @TableField("updated_user_id")
    private String updatedUserId;

    /**
     * 更新人名称
     */
    @TableField("updated_user_name")
    private String updatedUserName;

    /**
     * 创建时间
     */
    @TableField(value = "created_time", fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    @TableField(value = "updated_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedTime;
}
