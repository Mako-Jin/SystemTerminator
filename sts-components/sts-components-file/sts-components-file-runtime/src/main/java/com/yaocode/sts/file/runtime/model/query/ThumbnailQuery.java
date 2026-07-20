package com.yaocode.sts.file.runtime.model.query;

import lombok.Builder;
import lombok.Data;

/**
 * 缩略图查询
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class ThumbnailQuery {
    /** 文件ID */
    private String fileId;

    /** 缩略图宽度 */
    private Integer width;

    /** 缩略图高度 */
    private Integer height;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;
}