package com.yaocode.sts.file.runtime.model.result;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 完整性检查结果
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class IntegrityCheckResult {
    /** 文件ID */
    private String fileId;

    /** 文件名 */
    private String fileName;

    /** 是否完整 */
    private Boolean isIntegrity;

    /** 存储的MD5 */
    private String storedMd5;

    /** 计算的MD5 */
    private String computedMd5;

    /** 文件大小（字节） */
    private Long fileSize;

    /** 是否损坏 */
    private Boolean isCorrupted;

    /** 检查方法（MD5/SHA256） */
    private String checkMethod;

    /** 检查时间 */
    private LocalDateTime checkTime;

    /** 消息 */
    private String message;
}
