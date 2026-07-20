package com.yaocode.sts.file.runtime.model.command;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * 上传回调命令
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class UploadCallbackCommand {
    /** 任务ID */
    private String taskId;

    /** 文件ID */
    private String fileId;

    /** 文件名 */
    private String fileName;

    /** 文件大小（字节） */
    private Long fileSize;

    /** 文件URL */
    private String fileUrl;

    /** 状态（SUCCESS/FAILED/TIMEOUT） */
    private String status;

    /** 消息 */
    private String message;

    /** 附加数据 */
    private Map<String, Object> data;

    /** 时间戳 */
    private Long timestamp;

    /** 签名 */
    private String signature;

    /** 租户ID */
    private String tenantId;
}
