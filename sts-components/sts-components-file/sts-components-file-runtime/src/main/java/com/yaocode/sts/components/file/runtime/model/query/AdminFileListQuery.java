package com.yaocode.sts.components.file.runtime.model.query;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 管理端文件列表查询
 */
@Data
@Builder
public class AdminFileListQuery {

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件状态
     */
    private Integer fileStatus;

    /**
     * 存储类型
     */
    private String storageType;

    /**
     * 业务ID
     */
    private String businessId;

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 上传用户ID
     */
    private String uploadUserId;

    /**
     * 上传用户名称
     */
    private String uploadUserName;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 最小文件大小（字节）
     */
    private Long minSize;

    /**
     * 最大文件大小（字节）
     */
    private Long maxSize;

    /**
     * 标签列表
     */
    private List<String> tags;

    /**
     * 页码
     */
    private Integer page;

    /**
     * 每页大小
     */
    private Integer size;

    /**
     * 排序字段
     */
    private String orderBy;

    /**
     * 排序方向
     */
    private String orderDirection;

    /**
     * 租户ID
     */
    private String tenantId;
}