package com.yaocode.sts.components.file.interfaces.model.response;

import lombok.Data;

/**
 * 趋势数据
 */
@Data
public class TrendDataResponse {

    /**
     * 日期
     */
    private String date;
    /**
     * 上传数
     */
    private Long uploadCount;
    /**
     * 下载数
     */
    private Long downloadCount;
    /**
     * 总大小
     */
    private Long totalSize;
    /**
     * 独立用户数
     */
    private Long uniqueUsers;

}
