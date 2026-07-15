package com.yaocode.sts.components.file.interfaces.model.request;

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
    @NotBlank(message = "上传ID不能为空")
    private String uploadId;

    /**
     * 文件ID
     */
    @NotBlank(message = "文件ID不能为空")
    private String fileId;

    /**
     * 取消原因
     */
    private String reason;
}