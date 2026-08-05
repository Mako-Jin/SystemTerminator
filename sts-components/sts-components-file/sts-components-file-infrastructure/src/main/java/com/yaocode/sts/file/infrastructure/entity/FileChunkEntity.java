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
 * 文件分片表
 */
@Data
@TableName("file_tbl_file_chunk")
@EqualsAndHashCode(callSuper = true)
public class FileChunkEntity extends BasePo {

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
}