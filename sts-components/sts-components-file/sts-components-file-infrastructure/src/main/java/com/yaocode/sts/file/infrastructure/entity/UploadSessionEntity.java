package com.yaocode.sts.file.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yaocode.sts.common.infrastructure.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 上传会话表
 */
@Data
@TableName("file_tbl_upload_session")
@EqualsAndHashCode(callSuper = true)
public class UploadSessionEntity extends BasePo {

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
     * 文件名
     */
    @TableField("file_name")
    private String fileName;

    /**
     * 文件大小（字节）
     */
    @TableField("file_size")
    private Long fileSize;

    /**
     * 存储类型
     */
    @TableField("storage_type")
    private Integer storageType;

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

    /**
     * 文件MD5
     */
    @TableField("file_md5")
    private String fileMd5;
    /**
     * 文件SHA256值
     */
    @TableField("file_sha256")
    private String fileSha256;
    /**
     * 文件类型(MIME)
     */
    @TableField("file_type")
    private Integer fileType;
    /**
     * 文件标签(逗号分隔或JSON数组)
     */
    @TableField("tags")
    private String tags;
    /**
     * 文件描述
     */
    @TableField("description")
    private String description;
    /**
     * 是否公开: 0-否 1-是
     */
    @TableField("is_public")
    private Integer isPublic;
    /**
     * 元数据(JSON格式)
     */
    @TableField("metadata")
    private String metadata;
}