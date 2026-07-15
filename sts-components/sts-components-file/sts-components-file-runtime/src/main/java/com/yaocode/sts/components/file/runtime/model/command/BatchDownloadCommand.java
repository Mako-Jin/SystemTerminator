package com.yaocode.sts.components.file.runtime.model.command;

import com.yaocode.sts.components.file.runtime.model.dto.FileDownloadItemDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 批量下载命令
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class BatchDownloadCommand {
    /** 文件ID列表 */
    private List<FileDownloadItemDto> files;

    /** ZIP文件名 */
    private String zipFileName;

    /** 是否保留目录结构 */
    @Builder.Default
    private Boolean preserveStructure = true;

    /** 是否包含元数据 */
    @Builder.Default
    private Boolean includeMetadata = false;

    /** 最大文件数限制 */
    @Builder.Default
    private Integer maxFileCount = 100;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;
}
