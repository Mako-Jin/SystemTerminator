package com.yaocode.sts.file.interfaces.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 版本回滚响应
 * <p>
 * 包含回滚操作的详细信息，包括回滚前后的版本对比
 * </p>
 */
@Data
@Builder
public class VersionRollbackResponse {

    // ========== 回滚后的版本信息 ==========

    /**
     * 回滚后新创建的版本ID
     */
    private String newVersionId;

    /**
     * 回滚后的版本号
     */
    private Integer newVersionNumber;

    /**
     * 回滚后的版本标签
     */
    private String newVersionTag;

    /**
     * 回滚后的版本备注
     */
    private String newVersionRemark;

    /**
     * 回滚后的文件大小
     */
    private Long fileSize;

    /**
     * 回滚后的文件MD5
     */
    private String fileMd5;

    /**
     * 回滚后的文件SHA-256
     */
    private String fileSha256;

    /**
     * 回滚后的文件URL
     */
    private String fileUrl;

    /**
     * 回滚后的分支ID
     */
    private String branchId;

    /**
     * 回滚后的分支名称
     */
    private String branchName;

    // ========== 回滚前的版本信息 ==========

    /**
     * 回滚前的版本ID（被替换的版本）
     */
    private String oldVersionId;

    /**
     * 回滚前的版本号
     */
    private Integer oldVersionNumber;

    /**
     * 回滚前的版本标签
     */
    private String oldVersionTag;

    /**
     * 回滚前的版本备注
     */
    private String oldVersionRemark;

    /**
     * 回滚前的文件大小
     */
    private Long oldFileSize;

    /**
     * 回滚前的文件MD5
     */
    private String oldFileMd5;

    // ========== 目标版本信息（回滚到的版本） ==========

    /**
     * 目标版本ID（回滚到的版本）
     */
    private String targetVersionId;

    /**
     * 目标版本号
     */
    private Integer targetVersionNumber;

    /**
     * 目标版本标签
     */
    private String targetVersionTag;

    /**
     * 目标版本备注
     */
    private String targetVersionRemark;

    // ========== 回滚统计信息 ==========

    /**
     * 回滚原因
     */
    private String rollbackReason;

    /**
     * 回滚操作人
     */
    private String operatorUserName;

    /**
     * 回滚时间
     */
    private LocalDateTime rollbackTime;

    /**
     * 回滚是否成功
     */
    private Boolean success;

    /**
     * 回滚消息
     */
    private String message;

    /**
     * 版本差异统计
     */
    private RollbackDiffStats diffStats;

    /**
     * 影响的文件列表（如果是批量回滚）
     */
    private List<AffectedFileInfo> affectedFiles;

    // ========== 内部类 ==========

    /**
     * 回滚差异统计
     */
    @Data
    @Builder
    public static class RollbackDiffStats {
        /**
         * 变更类型: FULL_ROLLBACK, PARTIAL_ROLLBACK
         */
        private String rollbackType;

        /**
         * 变更行数
         */
        private Integer changedLines;

        /**
         * 变更百分比
         */
        private Double changePercentage;

        /**
         * 新增行数
         */
        private Integer addedLines;

        /**
         * 删除行数
         */
        private Integer deletedLines;

        /**
         * 修改行数
         */
        private Integer modifiedLines;
    }

    /**
     * 受影响的文件信息（批量回滚时使用）
     */
    @Data
    @Builder
    public static class AffectedFileInfo {
        private String fileId;
        private String fileName;
        private String oldVersionId;
        private String newVersionId;
        private Integer oldVersionNumber;
        private Integer newVersionNumber;
        private String status;  // SUCCESS, FAILED, SKIPPED
        private String message;
    }
}
