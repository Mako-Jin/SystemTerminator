package com.yaocode.sts.file.application.model.result;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 取消分片上传结果
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class CancelMultipartResult {
    /** 上传ID */
    private String uploadId;

    /** 文件ID */
    private String fileId;

    /** 是否成功取消 */
    private Boolean success;

    /** 被清理的分片数量 */
    private Integer cancelledChunks;

    /** 已上传的分片总数 */
    private Integer totalUploadedChunks;

    /** 取消时间 */
    private LocalDateTime cancelledAt;

    /** 取消原因 */
    private String reason;

    /** 操作消息 */
    private String message;
}
