package com.yaocode.sts.components.file.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文件版本表
 */
@Data
@TableName("file_version")
public class FileVersionEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    @TableField("version_id")
    private Long versionId;

    /**
     * 文件ID
     */
    @TableField("file_id")
    private String fileId;

    /**
     * 版本号(从1开始)
     */
    @TableField("version")
    private Integer version;

    /**
     * 版本名称
     */
    @TableField("version_name")
    private String versionName;

    /**
     * 文件存储路径
     */
    @TableField("file_path")
    private String filePath;

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
     * 变更日志
     */
    @TableField("change_log")
    private String changeLog;

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
