package com.yaocode.sts.components.file.interfaces.model.request;

import lombok.Data;

/**
 * 文件查询请求
 */
@Data
public class FileListQueryRequest {

    /**
     * 文件名（模糊匹配）
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
     * 上传开始时间
     */
    private String startTime;
    /**
     * 上传结束时间
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
     * 页码，默认1
     */
    private Integer page = 1;
    /**
     * 每页大小，默认20
     */
    private Integer size = 20;
    /**
     * 排序字段
     */
    private String orderBy;
    /**
     * 排序方向 asc/desc
     */
    private String orderDirection;

}