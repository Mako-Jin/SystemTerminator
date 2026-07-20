package com.yaocode.sts.file.runtime.model.result;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 审计历史
 */
@Data
@Builder
public class AuditHistoryResult {

    /**
     * 操作类型
     */
    private String action;

    /**
     * 操作备注
     */
    private String comment;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 操作时间
     */
    private LocalDateTime actionTime;
}