package com.yaocode.sts.file.runtime.model.result;

import lombok.Builder;
import lombok.Data;

/**
 * 媒体流信息
 */
@Data
@Builder
public class MediaStreamResult {
    /** 流类型（video/audio/subtitle） */
    private String streamType;

    /** 编码器 */
    private String streamCodec;

    /** 宽度 */
    private Integer width;

    /** 高度 */
    private Integer height;

    /** 语言 */
    private String language;

    /** 比特率 */
    private Integer bitrate;

    /** 帧率 */
    private String frameRate;

    /** 采样率 */
    private Integer sampleRate;

    /** 声道 */
    private Integer channels;
}
