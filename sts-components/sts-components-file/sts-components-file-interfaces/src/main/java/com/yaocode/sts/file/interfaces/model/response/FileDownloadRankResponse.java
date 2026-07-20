package com.yaocode.sts.file.interfaces.model.response;

import lombok.Builder;
import lombok.Data;

/**
 * 下载排行榜
 */
@Data
@Builder
public class FileDownloadRankResponse {

    /**
     * 文件ID
     */
    private String fileId;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件类型
     */
    private String fileType;
    /**
     * 文件大小（字节）
     */
    private Long fileSize;
    /**
     * 下载次数
     */
    private Long downloadCount;
    /**
     * 独立用户数
     */
    private Long uniqueUsers;
    /**
     * 增长率
     */
    private Double growthRate;
    /**
     * 排名
     */
    private Integer rank;
    /**
     * 排名变化（如 +1, -2, --）
     */
    private String rankChange;

}