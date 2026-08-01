package com.yaocode.sts.file.core.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 文件存在性检查结果
 * 用于判断文件是否已存在及其详细信息
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class FileExistenceContext {

    /** 文件是否存在 */
    private Boolean exists;

    /** 文件ID */
    private String fileId;

    /** 文件名 */
    private String fileName;

    /** 文件大小 */
    private Long fileSize;

    /** 文件MD5 */
    private String fileMd5;

    /** 文件SHA-256 */
    private String fileSha256;

    /** 文件URL */
    private String fileUrl;

    /** 存储类型 */
    private Integer storageType;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;

    /** 是否重复（有多个相同文件） */
    private Boolean isDuplicate;

    /** 版本号 */
    private Integer versionNumber;

    /** 重复文件列表 */
    private List<FileInfoContext> duplicateFiles;

}
