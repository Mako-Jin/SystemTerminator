package com.yaocode.sts.file.interfaces.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Map;

/**
 * 上传回调请求
 */
@Data
public class UploadCallbackRequest {
    /**
     * 任务ID
     */
    @NotBlank(message = "任务ID不能为空")
    private String taskId;

    /**
     * 文件ID
     */
    private String fileId;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件大小（字节）
     */
    private Long fileSize;
    /**
     * 文件URL
     */
    private String fileUrl;
    /**
     * 任务状态 SUCCESS, FAILED, TIMEOUT
     */
    private String status;
    /**
     * 消息
     */
    private String message;
    /**
     * 附加数据
     */
    private Map<String, Object> data;
    /**
     * 时间戳
     */
    private Long timestamp;
    /**
     * 签名验证
     */
    private String signature;
}