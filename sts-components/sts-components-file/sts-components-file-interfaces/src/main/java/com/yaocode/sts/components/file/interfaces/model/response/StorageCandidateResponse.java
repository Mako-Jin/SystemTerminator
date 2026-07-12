package com.yaocode.sts.components.file.interfaces.model.response;

import lombok.Data;

/**
 * 存储候选
 */
@Data
public class StorageCandidateResponse {

    /**
     * 存储类型
     */
    private String storageType;
    /**
     * 存储类型描述
     */
    private String storageTypeDesc;
    /**
     * 综合得分
     */
    private Double score;
    /**
     * 成本（单位/GB）
     */
    private Double cost;
    /**
     * 性能得分
     */
    private Double performance;
    /**
     * 可靠性得分
     */
    private Double reliability;
    /**
     * 推荐说明
     */
    private String recommendation;

}