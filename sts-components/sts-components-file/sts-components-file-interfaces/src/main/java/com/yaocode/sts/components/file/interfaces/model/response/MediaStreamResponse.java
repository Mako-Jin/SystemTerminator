package com.yaocode.sts.components.file.interfaces.model.response;

import lombok.Data;

/**
 * 媒体流信息
 */
@Data
public class MediaStreamResponse {

    /**
     * 流类型 video, audio, subtitle
     */
    private String type;
    /**
     * 编码格式
     */
    private String codec;
    /**
     * 宽度（像素）
     */
    private Integer width;
    /**
     * 高度（像素）
     */
    private Integer height;
    /**
     * 语言
     */
    private String language;
    /**
     * 比特率
     */
    private Integer bitrate;
    /**
     * 帧率
     */
    private String frameRate;

}