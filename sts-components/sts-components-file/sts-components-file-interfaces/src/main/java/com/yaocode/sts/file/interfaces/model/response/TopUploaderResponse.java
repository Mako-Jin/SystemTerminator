package com.yaocode.sts.file.interfaces.model.response;

import lombok.Builder;
import lombok.Data;

/**
 * 上传排行榜
 */
@Data
@Builder
public class TopUploaderResponse {

    /**
     * 用户ID
     */
    private String userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 文件数量
     */
    private Long fileCount;
    /**
     * 总大小（字节）
     */
    private Long totalSize;

}