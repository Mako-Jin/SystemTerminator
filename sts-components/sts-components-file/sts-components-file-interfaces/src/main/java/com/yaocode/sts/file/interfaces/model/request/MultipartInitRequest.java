package com.yaocode.sts.file.interfaces.model.request;

import com.yaocode.sts.common.basic.enums.YesNoEnums;
import com.yaocode.sts.file.core.constants.FileI18nKeyConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.Map;

/**
 * 分片上传初始化请求
 * <p>
 * 用于初始化分片上传会话，获取上传ID和分片信息
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
public class MultipartInitRequest {
    /**
     * 文件名
     */
    @NotBlank(message = FileI18nKeyConstants.FILE_NAME_EMPTY)
    private String fileName;

    /**
     * 文件大小（字节）
     */
    @NotNull(message = FileI18nKeyConstants.FILE_SIZE_EMPTY)
    @Positive(message = FileI18nKeyConstants.FILE_SIZE_INVALID)
    private Long fileSize;

    /**
     * 分片大小（字节），默认10MB
     */
    @Positive(message = FileI18nKeyConstants.CHUNK_SIZE_INVALID)
    private Long chunkSize = 10L * 1024L * 1024L;

    /**
     * 文件MD5值
     */
    private String fileMd5;
    /**
     * 文件SHA256值
     */
    private String fileSha256;
    /**
     * 文件类型(MIME)
     */
    private String fileType;
    /**
     * 存储类型
     */
    private Integer storageType;
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
    private Integer isPublic = YesNoEnums.NO.getCode();
    /**
     * 元数据
     */
    private Map<String, String> metadata;
}