package com.yaocode.sts.components.file.interfaces.model.request;

import lombok.Data;

import java.util.List;

/**
 * 文件搜索请求
 */
@Data
public class FileSearchRequest {

    /**
     * 关键词（文件名、描述）
     */
    private String keyword;
    /**
     * 文件名（精确匹配）
     */
    private String fileName;
    /**
     * 文件名（模糊匹配）
     */
    private String fileNameLike;
    /**
     * 文件类型列表
     */
    private List<String> fileTypes;
    /**
     * 存储类型列表
     */
    private List<String> storageTypes;
    /**
     * 文件状态列表
     */
    private List<Integer> statuses;
    /**
     * 标签列表
     */
    private List<String> tags;

    /**
     * 上传开始时间
     */
    private String uploadTimeStart;
    /**
     * 上传结束时间
     */
    private String uploadTimeEnd;
    /**
     * 最后访问开始时间
     */
    private String lastAccessStart;
    /**
     * 最后访问结束时间
     */
    private String lastAccessEnd;
    /**
     * 创建开始时间
     */
    private String createdTimeStart;
    /**
     * 创建结束时间
     */
    private String createdTimeEnd;

    /**
     * 最小文件大小（字节）
     */
    private Long minSize;
    /**
     * 最大文件大小（字节）
     */
    private Long maxSize;

    /**
     * 上传用户ID
     */
    private String uploadUserId;
    /**
     * 上传用户名
     */
    private String uploadUserName;

    /**
     * 业务ID
     */
    private String businessId;
    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 排序字段
     */
    private String orderBy;
    /**
     * 排序方向 asc/desc
     */
    private String orderDirection;

    /**
     * 页码，默认1
     */
    private Integer page = 1;
    /**
     * 每页大小，默认20
     */
    private Integer size = 20;

}