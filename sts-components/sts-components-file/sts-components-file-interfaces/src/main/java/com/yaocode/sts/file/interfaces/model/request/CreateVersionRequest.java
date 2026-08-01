package com.yaocode.sts.file.interfaces.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * 创建版本请求
 */
@Data
public class CreateVersionRequest {

    @NotBlank(message = "文件ID不能为空")
    private String fileId;

    @NotBlank(message = "文件名不能为空")
    private String fileName;

    @NotNull(message = "文件大小不能为空")
    @Positive(message = "文件大小必须大于0")
    private Long fileSize;

    @NotBlank(message = "文件MD5不能为空")
    private String fileMd5;

    private String fileSha256;

    /**
     * 版本类型: 1-主要版本 2-次要版本 3-补丁版本
     */
    private Integer versionType = 1;

    private String versionName;

    private String versionRemark;

    private String changeSummary;

    private String branchId;

    private Boolean setAsCurrent = true;

    private String tenantId;
    private String userId;
    private String userName;
}
