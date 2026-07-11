package com.yaocode.sts.components.file.interfaces.model.response;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文件审核信息
 */
@Data
public class FileAuditInfoResponse {

    /**
     * 文件ID
     */
    private String fileId;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件大小
     */
    private Long fileSize;
    /**
     * 上传用户ID
     */
    private String uploadUserId;
    /**
     * 上传用户名
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
     * 审核评论
     */
    private String auditComment;
    /**
     * 审核用户ID
     */
    private String auditUserId;
    /**
     * 审核用户名
     */
    private String auditUserName;
    /**
     * 审核时间
     */
    private LocalDateTime auditTime;

}
