package com.yaocode.sts.components.file.runtime.model.query;

import lombok.Builder;
import lombok.Data;

/**
 * 文件列表查询
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class FileListQuery {
    /** 文件类型 */
    private String fileType;

    /** 存储类型 */
    private String storageType;

    /** 上传开始时间 */
    private String uploadTimeStart;

    /** 上传结束时间 */
    private String uploadTimeEnd;

    /** 最小文件大小 */
    private Long minSize;

    /** 最大文件大小 */
    private Long maxSize;

    /** 文件状态 */
    private Integer status;

    /** 页码 */
    @Builder.Default
    private Integer page = 1;

    /** 每页数量 */
    @Builder.Default
    private Integer size = 20;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;
}
