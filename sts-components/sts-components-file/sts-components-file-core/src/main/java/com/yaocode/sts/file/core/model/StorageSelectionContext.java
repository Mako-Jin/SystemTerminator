package com.yaocode.sts.file.core.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 存储选择上下文
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class StorageSelectionContext {
    /** 文件大小（字节） */
    private Long fileSize;

    /** 文件扩展名 */
    private String fileExtension;

    /** 文件类型 */
    private String fileType;

    /** 用户指定的存储类型（优先级最高） */
    private Integer specifiedStorage;

    /** 优先存储列表 */
    private List<String> preferredStorages;

    /** 存储性能映射（存储类型 → 性能评分） */
    private Map<String, Double> storagePerformanceMap;

    /** 策略名称 */
    private String strategy;

    /** 存储成本映射 */
    private Map<String, Double> storageCostMap;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;
}
