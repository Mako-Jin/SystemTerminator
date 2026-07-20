package com.yaocode.sts.file.runtime.model.command;


import lombok.Builder;
import lombok.Data;

/**
 * 清理临时文件命令
 */
@Data
@Builder
public class CleanTempCommand {

    /**
     * 过期小时数
     */
    private Integer hours;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 用户ID
     */
    private String userId;
}