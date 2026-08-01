package com.yaocode.sts.file.interfaces.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 版本对比请求
 */
@Data
public class VersionCompareRequest {

    @NotBlank(message = "源版本ID不能为空")
    private String versionId1;

    @NotBlank(message = "目标版本ID不能为空")
    private String versionId2;
}
