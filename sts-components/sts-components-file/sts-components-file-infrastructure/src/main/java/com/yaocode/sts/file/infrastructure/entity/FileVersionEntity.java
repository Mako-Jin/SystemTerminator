package com.yaocode.sts.file.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文件版本表
 */
@Data
@Builder
@TableName("file_tbl_file_version")
public class FileVersionEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    @TableField("version_id")
    private String versionId;

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

    /**
     * 版本号（从1开始递增）
     */
    private Integer versionNumber;

    /**
     * 版本类型: 1-主要版本 2-次要版本 3-补丁版本
     */
    private Integer versionType;

    /**
     * 版本标签（如 v1.0.0）
     */
    private String versionTag;

    /**
     * 版本备注
     */
    private String versionRemark;

    /**
     * 变更摘要
     */
    private String changeSummary;

    /**
     * 文件SHA-256
     */
    private String fileSha256;

    /**
     * 存储访问URL
     */
    private String storageUrl;

    /**
     * 父版本ID（版本链）
     */
    private String parentVersionId;

    /**
     * 所属分支ID
     */
    private String branchId;

    /**
     * 是否为当前版本: 0-否 1-是
     */
    private Boolean isCurrent;

    /**
     * 是否为最新版本: 0-否 1-是
     */
    private Boolean isLatest;

    /**
     * 是否已删除: 0-否 1-是
     */
    private Boolean isDeleted;

    /**
     * 租户ID
     */
    private String tenantId;

}
