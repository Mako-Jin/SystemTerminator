package com.yaocode.sts.components.file.runtime.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文件秒传表(去重)
 */
@Data
@TableName("file_tbl_file_deduplication")
public class FileDeduplicationEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    @TableField("deduplication_id")
    private Long deduplicationId;

    /**
     * 文件指纹(MD5+Size+存储类型)
     */
    @TableField("fingerprint")
    private String fingerprint;

    /**
     * 已存在的文件ID
     */
    @TableField("file_id")
    private String fileId;

    /**
     * 文件MD5
     */
    @TableField("file_md5")
    private String fileMd5;

    /**
     * 文件大小(字节)
     */
    @TableField("file_size")
    private Long fileSize;

    /**
     * 存储类型
     */
    @TableField("storage_type")
    private Integer storageType;

    /**
     * 引用计数(相同文件被引用的次数)
     */
    @TableField("reference_count")
    private Integer referenceCount;

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
