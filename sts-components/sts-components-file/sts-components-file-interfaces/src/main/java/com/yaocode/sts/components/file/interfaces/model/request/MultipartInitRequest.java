package com.yaocode.sts.components.file.interfaces.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.Map;

/**
 * 分片上传初始化请求
 */
@Data
public class MultipartInitRequest {
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
     * 分片大小（字节），默认10MB
     */
    @Positive(message = "分片大小必须大于0")
    private Long chunkSize = 10 * 1024 * 1024L;

    /**
     * 文件MD5值
     */
    private String fileMd5;
    /**
     * 文件类型
     */
    private String fileType;
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