package com.yaocode.sts.components.file.interfaces.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.Map;

/**
 * 秒传请求
 * <p>
 * 用于秒传场景，直接获取已存在文件的信息，无需重新上传
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
public class FastUploadRequest {
    /**
     * 文件名
     */
    @NotBlank(message = "文件名不能为空")
    private String fileName;

    /**
     * 文件MD5值
     */
    @NotBlank(message = "文件MD5不能为空")
    private String fileMd5;

    /**
     * 文件大小（字节）
     */
    @NotNull(message = "文件大小不能为空")
    @Positive(message = "文件大小必须大于0")
    private Long fileSize;

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
     * 文件类型
     */
    private String fileType;
}