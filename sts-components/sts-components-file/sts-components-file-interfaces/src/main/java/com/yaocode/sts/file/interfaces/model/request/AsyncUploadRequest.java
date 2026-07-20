package com.yaocode.sts.file.interfaces.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.Map;

/**
 * 异步上传请求
 * <p>
 * 用于提交异步上传任务，支持通过Base64内容或URL拉取方式上传文件
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
public class AsyncUploadRequest {
    /**
     * 文件名
     */
    @NotBlank(message = "文件名不能为空")
    private String fileName;

    /**
     * 文件大小（字节）
     */
    @NotNull(message = "文件大小不能为空")
    @Positive(message = "文件大小必须大于0")
    private Long fileSize;

    /**
     * 文件MD5值
     */
    private String fileMd5;
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

    /**
     * Base64编码的文件内容
     */
    private String fileContentBase64;
    /**
     * 文件URL（从URL拉取）
     */
    private String fileUrl;

    /**
     * 回调URL
     */
    private String callbackUrl;
    /**
     * 回调方法，默认POST
     */
    private String callbackMethod = "POST";
    /**
     * 回调请求头
     */
    private Map<String, String> callbackHeaders;

    /**
     * 优先级 1-10，数字越大优先级越高，默认5
     */
    private Integer priority = 5;
    /**
     * 超时时间（秒），默认300
     */
    private Integer timeoutSeconds = 300;
    /**
     * 完成后是否删除源文件
     */
    private Boolean deleteAfterComplete = false;
}