package com.yaocode.sts.file.runtime.model.result;

import lombok.Builder;
import lombok.Data;

/**
 * 上传排行榜
 */
@Data
@Builder
public class TopUploaderResult {

    /** 用户ID */
    private String userId;

    /** 用户名 */
    private String userName;

    /** 文件数 */
    private Long fileCount;

    /** 总大小 */
    private Long totalSize;

}
