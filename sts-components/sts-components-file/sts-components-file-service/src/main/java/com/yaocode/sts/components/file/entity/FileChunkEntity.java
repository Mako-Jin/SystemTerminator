package com.yaocode.sts.components.file.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文件分片表
 */
@Data
@TableName("file_tbl_file_chunk")
public class FileChunkEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    @TableField("chunk_id")
    private Long chunkId;

    /**
     * 上传会话ID(全局唯一)
     */
    @TableField("upload_id")
    private String uploadId;

    /**
     * 文件ID
     */
    @TableField("file_id")
    private String fileId;

    /**
     * 分片序号(从1开始)
     */
    @TableField("chunk_number")
    private Integer chunkNumber;

    /**
     * 分片大小(字节)
     */
    @TableField("chunk_size")
    private Long chunkSize;

    /**
     * 分片MD5值
     */
    @TableField("chunk_md5")
    private String chunkMd5;

    /**
     * 分片存储路径
     */
    @TableField("chunk_path")
    private String chunkPath;

    /**
     * 存储类型
     */
    @TableField("storage_type")
    private Integer storageType;

    /**
     * 分片状态: 0-待上传 1-上传中 2-已完成 3-失败 4-已取消
     */
    @TableField("chunk_status")
    private Integer chunkStatus;

    /**
     * 上传开始时间
     */
    @TableField("upload_start_time")
    private LocalDateTime uploadStartTime;

    /**
     * 上传完成时间
     */
    @TableField("upload_end_time")
    private LocalDateTime uploadEndTime;

    /**
     * 重试次数
     */
    @TableField("retry_count")
    private Integer retryCount;

    /**
     * 错误信息
     */
    @TableField("error_message")
    private String errorMessage;

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