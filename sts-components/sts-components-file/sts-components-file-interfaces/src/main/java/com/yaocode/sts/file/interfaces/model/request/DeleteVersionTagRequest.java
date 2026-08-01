package com.yaocode.sts.file.interfaces.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 删除版本标签请求
 */
@Data
public class DeleteVersionTagRequest {

    @NotBlank(message = "标签ID不能为空")
    private String tagId;

    private String tenantId;
    private String userId;
}
