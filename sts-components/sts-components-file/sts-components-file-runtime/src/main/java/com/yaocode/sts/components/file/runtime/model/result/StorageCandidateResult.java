package com.yaocode.sts.components.file.runtime.model.result;

import lombok.Builder;
import lombok.Data;

/**
 * 存储候选
 */
@Data
@Builder
public class StorageCandidateResult {

    /** 存储类型 */
    private String storageType;

    /** 存储类型描述 */
    private String storageTypeDesc;

    /** 综合得分 */
    private Double score;

    /** 成本（单位/GB） */
    private Double cost;

    /** 性能得分 */
    private Double performance;

    /** 可靠性得分 */
    private Double reliability;

    /** 推荐说明 */
    private String recommendation;

}
