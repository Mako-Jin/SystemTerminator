package com.yaocode.sts.file.runtime.model.query;

import lombok.Builder;
import lombok.Data;

/**
 * 文件存在性查询（秒传检测）
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class FileExistenceQuery {
    /** 文件MD5 */
    private String fileMd5;

    /** 文件大小（字节） */
    private Long fileSize;

    /** 存储类型 */
    private Integer storageType;

    /** 租户ID */
    private String tenantId;
}
