package com.yaocode.sts.file.runtime.model.result;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 访问记录结果
 */
@Data
@Builder
public class AccessRecordResult {
    /** 用户ID */
    private String userId;

    /** 用户名 */
    private String userName;

    /** 操作类型（DOWNLOAD/VIEW/PREVIEW） */
    private String operationType;

    /** IP地址 */
    private String ipAddress;

    /** 用户代理 */
    private String userAgent;

    /** 访问时间 */
    private LocalDateTime accessTime;

    /** 耗时（毫秒） */
    private Long duration;

    /** 是否成功 */
    private Boolean success;
}
