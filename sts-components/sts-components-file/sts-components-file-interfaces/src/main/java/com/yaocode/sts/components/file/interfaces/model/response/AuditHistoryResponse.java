package com.yaocode.sts.components.file.interfaces.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AuditHistoryResponse {

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