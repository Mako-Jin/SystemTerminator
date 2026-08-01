package com.yaocode.sts.file.application.model.command;

import lombok.Builder;
import lombok.Data;

import java.io.InputStream;

/**
 * 创建版本命令
 */
@Data
@Builder
public class CreateVersionCommand {
    private String fileId;
    private String fileName;
    private Long fileSize;
    private String fileMd5;
    private String fileSha256;
    private Integer versionType;
    private String versionName;
    private String versionRemark;
    private String changeSummary;
    private String branchId;
    private Boolean setAsCurrent;
    private String tenantId;
    private String userId;
    private String userName;
    /**
     * 文件内容（字节数组）
     */
    private InputStream fileContent;
}
