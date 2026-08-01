package com.yaocode.sts.file.interfaces.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 创建版本标签请求
 */
@Data
public class CreateVersionTagRequest {

    @NotBlank(message = "文件ID不能为空")
    private String fileId;

    @NotBlank(message = "版本ID不能为空")
    private String versionId;

    @NotBlank(message = "标签名称不能为空")
    private String tagName;

    /**
     * 标签类型: 1-发布版 2-里程碑 3-测试版 4-自定义
     */
    private Integer tagType = 1;

    private String tagDescription;

    private String tenantId;
    private String userId;
    private String userName;
}
