package com.yaocode.sts.file.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yaocode.sts.common.infrastructure.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 文件秒传表(去重)
 */
@Data
@TableName("file_tbl_file_deduplication")
@EqualsAndHashCode(callSuper = true)
public class FileDeduplicationEntity extends BasePo {

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
     * 文件SHA-256
     */
    @TableField("file_sha256")
    private String fileSha256;

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
}
