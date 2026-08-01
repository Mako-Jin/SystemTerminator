package com.yaocode.sts.file.interfaces.model.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

/**
 * 批量获取版本信息请求
 */
@Data
public class VersionsBatchRequest {

    @NotEmpty(message = "版本ID列表不能为空")
    private List<String> versionIds;
}
