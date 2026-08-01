package com.yaocode.sts.file.infrastructure.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 文件版本差异实体
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileVersionDiffEntity {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 差异ID（业务主键）
     */
    private String diffId;

    /**
     * 文件ID
     */
    private String fileId;

    /**
     * 源版本ID
     */
    private String fromVersionId;

    /**
     * 目标版本ID
     */
    private String toVersionId;

    /**
     * 差异类型: 1-全量 2-增量 3-二进制差异
     */
    private Integer diffType;

    /**
     * 差异数据存储路径
     */
    private String diffPath;

    /**
     * 差异数据大小（字节）
     */
    private Long diffSize;

    /**
     * 变更类型: 1-新增 2-修改 3-删除 4-重命名
     */
    private Integer changeType;

    /**
     * 变更百分比
     */
    private Double changePercentage;

    /**
     * 新增行数
     */
    private Integer addedLines;

    /**
     * 删除行数
     */
    private Integer deletedLines;

    /**
     * 修改行数
     */
    private Integer modifiedLines;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;
}
