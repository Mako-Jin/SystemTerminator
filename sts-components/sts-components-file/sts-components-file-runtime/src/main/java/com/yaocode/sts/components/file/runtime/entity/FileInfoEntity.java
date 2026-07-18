package com.yaocode.sts.components.file.runtime.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文件信息主表
 */
@Data
@TableName("file_tbl_file_info")
public class FileInfoEntity {

    /**
     * 文件全局唯一标识(UUID)
     */
    @TableId(type = IdType.ASSIGN_UUID)
    @TableField("file_id")
    private String fileId;

    /**
     * 原始文件名
     */
    @TableField("file_name")
    private String fileName;

    /**
     * 存储路径/对象名
     */
    @TableField("file_path")
    private String filePath;

    /**
     * 文件大小(字节)
     */
    @TableField("file_size")
    private Long fileSize;

    /**
     * 文件MD5值
     */
    @TableField("file_md5")
    private String fileMd5;

    /**
     * 文件SHA256值
     */
    @TableField("file_sha256")
    private String fileSha256;

    /**
     * 文件MIME类型
     */
    @TableField("file_type")
    private Integer fileType;

    /**
     * 文件扩展名
     */
    @TableField("file_extension")
    private Integer fileExtension;

    // ========== 存储信息 ==========

    /**
     * 存储类型(local/minio/oss/rustfs)
     */
    @TableField("storage_type")
    private Integer storageType;

    /**
     * 存储桶/目录
     */
    @TableField("storage_bucket")
    private String storageBucket;

    /**
     * 存储区域
     */
    @TableField("storage_region")
    private String storageRegion;

    /**
     * 访问URL
     */
    @TableField("storage_url")
    private String storageUrl;

    /**
     * 存储端元数据(JSON)
     */
    @TableField("storage_metadata")
    private String storageMetadata;

    // ========== 上传状态 ==========

    /**
     * 上传状态: 0-上传中 1-已完成 2-失败 3-暂停 4-已取消
     */
    @TableField("upload_status")
    private Integer uploadStatus;

    /**
     * 上传进度(百分比)
     */
    @TableField("upload_progress")
    private Integer uploadProgress;

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

    // ========== 业务元数据 ==========

    /**
     * 标签(JSON数组)
     */
    @TableField("tags")
    private String tags;

    /**
     * 文件描述
     */
    @TableField("description")
    private String description;

    // ========== 状态信息 ==========

    /**
     * 文件状态: 1-正常 2-已删除 3-已归档 4-迁移中
     */
    @TableField("file_status")
    private Integer fileStatus;

    /**
     * 是否公开: 0-私有 1-公开
     */
    @TableField("is_public")
    private Integer isPublic;

    /**
     * 是否加密: 0-否 1-是
     */
    @TableField("is_encrypted")
    private Integer isEncrypted;

    /**
     * 是否压缩: 0-否 1-是
     */
    @TableField("is_compressed")
    private Integer isCompressed;

    /**
     * 下载次数
     */
    @TableField("download_count")
    private Long downloadCount;

    /**
     * 查看次数
     */
    @TableField("view_count")
    private Long viewCount;

    // ========== 时间信息 ==========

    /**
     * 上传时间
     */
    @TableField("upload_time")
    private LocalDateTime uploadTime;

    /**
     * 最后访问时间
     */
    @TableField("last_access_time")
    private LocalDateTime lastAccessTime;

    /**
     * 最后修改时间
     */
    @TableField("last_modified_time")
    private LocalDateTime lastModifiedTime;

    /**
     * 过期时间
     */
    @TableField("expire_time")
    private LocalDateTime expireTime;

    /**
     * 删除时间
     */
    @TableField("delete_time")
    private LocalDateTime deleteTime;

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
     * 版本号(乐观锁)
     */
    @Version
    @TableField("version")
    private Integer version;
}