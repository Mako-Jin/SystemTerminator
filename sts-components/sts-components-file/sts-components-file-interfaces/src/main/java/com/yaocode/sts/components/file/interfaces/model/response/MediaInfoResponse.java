package com.yaocode.sts.components.file.interfaces.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 媒体信息
 */
@Data
@Builder
public class MediaInfoResponse {

    /**
     * 文件ID
     */
    private String fileId;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 媒体类型 video, audio, image
     */
    private String mediaType;
    /**
     * 格式
     */
    private String format;
    /**
     * 时长（秒）
     */
    private Long duration;
    /**
     * 宽度（像素）
     */
    private Integer width;
    /**
     * 高度（像素）
     */
    private Integer height;
    /**
     * 比特率
     */
    private String bitrate;
    /**
     * 编码格式
     */
    private String codec;
    /**
     * 音视频流信息
     */
    private List<MediaStreamResponse> streams;
    /**
     * 扩展元数据
     */
    private Map<String, Object> metadata;

}