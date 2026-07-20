package com.yaocode.sts.file.interfaces.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Map;

/**
 * 完成分片上传请求
 * <p>
 * 用于合并所有已上传的分片，完成文件上传
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
public class CompleteMultipartRequest {
    /**
     * 上传ID
     */
    @NotBlank(message = "上传ID不能为空")
    private String uploadId;

    /**
     * 文件ID
     */
    @NotBlank(message = "文件ID不能为空")
    private String fileId;

    /**
     * 文件名（可覆盖原文件名）
     */
    private String fileName;
    /**
     * 文件描述
     */
    private String description;
    /**
     * 标签（逗号分隔）
     */
    private String tags;
    /**
     * 是否公开
     */
    private Boolean isPublic;
    /**
     * 元数据
     */
    private Map<String, String> metadata;
}