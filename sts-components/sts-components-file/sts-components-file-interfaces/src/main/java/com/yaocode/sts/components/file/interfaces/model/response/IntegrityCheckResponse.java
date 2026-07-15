package com.yaocode.sts.components.file.interfaces.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 完整性检查VO
 */
@Data
@Builder
public class IntegrityCheckResponse {

    /**
     * 文件ID
     */
    private String fileId;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 是否完整
     */
    private Boolean isIntegrity;
    /**
     * 存储的MD5值
     */
    private String storedMd5;
    /**
     * 计算的MD5值
     */
    private String computedMd5;
    /**
     * 文件大小（字节）
     */
    private Long fileSize;
    /**
     * 是否已损坏
     */
    private Boolean isCorrupted;
    /**
     * 校验方法 MD5, SHA256
     */
    private String checkMethod;
    /**
     * 校验时间
     */
    private LocalDateTime checkTime;
    /**
     * 消息
     */
    private String message;

}