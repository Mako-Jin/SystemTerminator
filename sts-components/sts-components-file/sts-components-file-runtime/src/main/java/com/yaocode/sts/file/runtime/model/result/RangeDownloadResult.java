package com.yaocode.sts.file.runtime.model.result;

import lombok.Builder;
import lombok.Data;

/**
 * 分段下载结果
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class RangeDownloadResult {
    /** 文件ID */
    private String fileId;

    /** 文件名 */
    private String fileName;

    /** 文件总大小（字节） */
    private Long fileSize;

    /** 起始位置 */
    private Long start;

    /** 结束位置 */
    private Long end;

    /** 本次传输大小（字节） */
    private Long contentLength;

    /** Content-Range 头 */
    private String contentRange;

    /** HTTP状态码 */
    private Integer httpStatus;

    /** 是否支持Range */
    @Builder.Default
    private Boolean supportsRange = true;
}
