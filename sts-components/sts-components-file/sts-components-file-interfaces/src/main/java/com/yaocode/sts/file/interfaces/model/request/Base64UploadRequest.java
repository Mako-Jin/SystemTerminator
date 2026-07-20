package com.yaocode.sts.file.interfaces.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Map;

/**
 * Base64上传请求
 * <p>
 * 用于将Base64编码的文件内容上传到存储系统
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
public class Base64UploadRequest {
    /**
     * 文件名
     */
    @NotBlank(message = "文件名不能为空")
    private String fileName;

    /**
     * Base64编码的文件内容
     */
    @NotBlank(message = "文件内容不能为空")
    private String base64Content;

    /**
     * 存储类型
     */
    private String storageType;
    /**
     * 业务ID
     */
    private String businessId;
    /**
     * 业务类型
     */
    private String businessType;
    /**
     * 标签（逗号分隔）
     */
    private String tags;
    /**
     * 文件描述
     */
    private String description;
    /**
     * 是否公开
     */
    private Boolean isPublic = false;
    /**
     * 元数据
     */
    private Map<String, String> metadata;
}