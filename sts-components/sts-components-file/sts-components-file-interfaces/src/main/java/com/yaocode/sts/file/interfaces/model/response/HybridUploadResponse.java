package com.yaocode.sts.file.interfaces.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

/**
 * 混合云上传响应
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class HybridUploadResponse extends UploadResponse {
    /**
     * 最终选择的存储类型
     */
    private String selectedStorage;
    /**
     * 使用的策略
     */
    private String strategy;
    /**
     * 选择原因
     */
    private String selectionReason;
    /**
     * 候选存储列表
     */
    private List<StorageCandidateResponse> candidates;
    /**
     * 决策元数据
     */
    private Map<String, Object> decisionMetadata;
}