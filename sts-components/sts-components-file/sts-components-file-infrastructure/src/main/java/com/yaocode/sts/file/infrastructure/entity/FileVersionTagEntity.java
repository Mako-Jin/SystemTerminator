package com.yaocode.sts.file.infrastructure.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文件版本标签实体
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class FileVersionTagEntity {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 标签ID（业务主键）
     */
    private String tagId;

    /**
     * 文件ID
     */
    private String fileId;

    /**
     * 版本ID
     */
    private String versionId;

    /**
     * 标签名称
     */
    private String tagName;

    /**
     * 标签类型: 1-发布版 2-里程碑 3-测试版 4-自定义
     */
    private Integer tagType;

    /**
     * 标签描述
     */
    private String tagDescription;

    /**
     * 创建用户ID
     */
    private String createdUserId;

    /**
     * 创建用户名
     */
    private String createdUserName;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;
}
