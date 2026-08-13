package com.yaocode.sts.file.interfaces.model.request;

import com.yaocode.sts.file.core.constants.FileI18nKeyConstants;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 取消分片上传请求
 * <p>
 * 用于取消正在进行的分片上传会话
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
public class CancelMultipartRequest {
    /**
     * 上传ID
     */
    @NotBlank(message = FileI18nKeyConstants.UPLOAD_ID_EMPTY)
    private String uploadId;

    /**
     * 文件ID
     */
    @NotBlank(message = FileI18nKeyConstants.FILE_ID_EMPTY)
    private String fileId;

    /**
     * 取消原因
     */
    private String reason;
}