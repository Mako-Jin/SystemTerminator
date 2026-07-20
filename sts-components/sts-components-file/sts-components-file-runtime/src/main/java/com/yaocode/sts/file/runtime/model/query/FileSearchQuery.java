package com.yaocode.sts.file.runtime.model.query;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 文件搜索查询
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class FileSearchQuery {
    /** 关键词（文件名、描述） */
    private String keyword;

    /** 文件名（精确匹配） */
    private String fileName;

    /** 文件名（模糊匹配） */
    private String fileNameLike;

    /** 文件类型列表 */
    private List<String> fileTypes;

    /** 存储类型列表 */
    private List<String> storageTypes;

    /** 文件状态列表 */
    private List<Integer> statuses;

    /** 标签列表 */
    private List<String> tags;

    /** 上传开始时间 */
    private String uploadTimeStart;

    /** 上传结束时间 */
    private String uploadTimeEnd;

    /** 最后访问开始时间 */
    private String lastAccessStart;

    /** 最后访问结束时间 */
    private String lastAccessEnd;

    /** 创建开始时间 */
    private String createdTimeStart;

    /** 创建结束时间 */
    private String createdTimeEnd;

    /** 最小文件大小 */
    private Long minSize;

    /** 最大文件大小 */
    private Long maxSize;

    /** 上传用户ID */
    private String uploadUserId;

    /** 上传用户名 */
    private String uploadUserName;

    /** 业务ID */
    private String businessId;

    /** 业务类型 */
    private String businessType;

    /** 排序字段 */
    private String orderBy;

    /** 排序方向（asc/desc） */
    private String orderDirection;

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
