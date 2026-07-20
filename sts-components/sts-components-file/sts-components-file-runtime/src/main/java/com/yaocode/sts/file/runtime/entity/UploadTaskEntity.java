package com.yaocode.sts.file.runtime.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 异步上传任务表
 */
@Data
@TableName("file_tbl_upload_task")
public class UploadTaskEntity {

    /**
     * 任务ID(全局唯一)
     */
    @TableId(type = IdType.ASSIGN_UUID)
    @TableField("task_id")
    private String taskId;

    /**
     * 文件ID(上传完成后生成)
     */
    @TableField("file_id")
    private String fileId;

    /**
     * 文件名
     */
    @TableField("file_name")
    private String fileName;

    /**
     * 文件大小(字节)
     */
    @TableField("file_size")
    private Long fileSize;

    /**
     * 文件MD5
     */
    @TableField("file_md5")
    private String fileMd5;

    /**
     * 目标存储类型
     */
    @TableField("storage_type")
    private Integer storageType;

    /**
     * 任务状态: 0-排队 1-处理中 2-已完成 3-失败 4-已取消 5-超时
     */
    @TableField("task_status")
    private Integer taskStatus;

    /**
     * 任务类型: upload-上传 delete-删除 convert-转换 copy-复制
     */
    @TableField("task_type")
    private Integer taskType;

    /**
     * 任务进度(百分比)
     */
    @TableField("task_progress")
    private Integer taskProgress;

    /**
     * 错误信息
     */
    @TableField("error_message")
    private String errorMessage;

    /**
     * 结果数据(JSON格式)
     */
    @TableField("result_data")
    private String resultData;

    /**
     * 队列名称
     */
    @TableField("queue_name")
    private String queueName;

    /**
     * 优先级(数字越大越高)
     */
    @TableField("priority")
    private Integer priority;

    /**
     * 计划执行时间
     */
    @TableField("scheduled_time")
    private LocalDateTime scheduledTime;

    /**
     * 开始执行时间
     */
    @TableField("start_time")
    private LocalDateTime startTime;

    /**
     * 执行完成时间
     */
    @TableField("end_time")
    private LocalDateTime endTime;

    /**
     * 超时时间(秒)
     */
    @TableField("timeout_seconds")
    private Integer timeoutSeconds;

    /**
     * 已重试次数
     */
    @TableField("retry_count")
    private Integer retryCount;

    /**
     * 最大重试次数
     */
    @TableField("max_retry_count")
    private Integer maxRetryCount;

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
