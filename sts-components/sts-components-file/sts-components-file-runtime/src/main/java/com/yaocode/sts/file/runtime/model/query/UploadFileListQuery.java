package com.yaocode.sts.file.runtime.model.query;

import lombok.Builder;
import lombok.Data;

/**
 * 用户文件列表查询
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class UploadFileListQuery {
    /** 页码 */
    private Integer page;

    /** 每页数量 */
    private Integer size;

    /** 文件状态 */
    private Integer fileStatus;

    /** 存储类型 */
    private String storageType;

    /** 文件名（模糊匹配） */
    private String fileName;

    /** 开始时间 */
    private String startTime;

    /** 结束时间 */
    private String endTime;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;
}
