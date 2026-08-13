package com.yaocode.sts.file.interfaces.model.request;

import com.yaocode.sts.file.core.constants.FileI18nKeyConstants;
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
    @NotBlank(message = FileI18nKeyConstants.FILE_NAME_EMPTY)
    private String fileName;

    /**
     * 文件MD5值
     */
    @NotBlank(message = FileI18nKeyConstants.FILE_MD5_EMPTY)
    private String fileMd5;

    /**
     * 文件大小（字节）
     */
    @NotNull(message = FileI18nKeyConstants.FILE_SIZE_EMPTY)
    @Positive(message = FileI18nKeyConstants.FILE_SIZE_INVALID)
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
    private Boolean isPublic = Boolean.FALSE;
    /**
     * 元数据
     */
    private Map<String, String> metadata;
    /**
     * 文件类型
     */
    private String fileType;
}