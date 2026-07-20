package com.yaocode.sts.file.interfaces.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 文件审核信息
 */
@Data
@Builder
public class FileAuditInfoResponse {

    /**
     * 文件ID
     */
    private String fileId;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件大小（字节）
     */
    private Long fileSize;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 上传用户ID
     */
    private String uploadUserId;

    /**
     * 上传用户名称
     */
    private String uploadUserName;

    /**
     * 上传时间
     */
    private LocalDateTime uploadTime;

    /**
     * 审核状态 0-待审核, 1-通过, 2-拒绝
     */
    private Integer auditStatus;

    /**
     * 审核状态描述
     */
    private String auditStatusDesc;

    /**
     * 审核评论
     */
    private String auditComment;

    /**
     * 审核用户ID
     */
    private String auditUserId;

    /**
     * 审核用户名称
     */
    private String auditUserName;

    /**
     * 审核时间
     */
    private LocalDateTime auditTime;

    /**
     * 审核历史列表
     */
    private List<AuditHistoryResponse> auditHistory;

}