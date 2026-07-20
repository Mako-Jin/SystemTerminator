package com.yaocode.sts.file.runtime.model.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

/**
 * 混合云上传结果
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class HybridUploadResult extends UploadResult {
    /** 最终选择的存储类型 */
    private String selectedStorage;

    /** 使用的策略 */
    private String strategy;

    /** 选择原因 */
    private String selectionReason;

    /** 候选存储列表 */
    private List<StorageCandidateResult> candidates;

    /** 决策元数据 */
    private Map<String, Object> decisionMetadata;
}
