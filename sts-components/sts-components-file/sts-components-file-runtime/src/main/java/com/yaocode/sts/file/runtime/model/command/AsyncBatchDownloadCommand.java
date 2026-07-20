package com.yaocode.sts.file.runtime.model.command;

import com.yaocode.sts.file.runtime.model.dto.FileDownloadItemDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 异步批量下载命令
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class AsyncBatchDownloadCommand {
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

    /** 回调URL */
    private String callbackUrl;

    /** 回调方法（GET/POST） */
    @Builder.Default
    private String callbackMethod = "POST";

    /** 回调请求头 */
    private Map<String, String> callbackHeaders;

    /** 任务优先级（1-10） */
    @Builder.Default
    private Integer priority = 5;

    /** 超时时间（秒） */
    @Builder.Default
    private Integer timeoutSeconds = 600;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;
}
