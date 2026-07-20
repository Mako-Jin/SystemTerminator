package com.yaocode.sts.file.interfaces.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Map;

/**
 * URL拉取请求
 * <p>
 * 用于从指定URL拉取文件并上传到存储系统
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
public class UrlFetchRequest {
    /**
     * 文件URL
     */
    @NotBlank(message = "文件URL不能为空")
    @Pattern(regexp = "^https?://.*$", message = "URL必须以http或https开头")
    private String fileUrl;

    /**
     * 文件名（不指定则从URL自动提取）
     */
    private String fileName;
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
     * 超时时间（秒），默认60
     */
    private Integer timeout = 60;
    /**
     * 自定义请求头
     */
    private Map<String, String> headers;
    /**
     * 元数据
     */
    private Map<String, String> metadata;
}