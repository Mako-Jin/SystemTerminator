package com.yaocode.sts.components.file.web.websocket;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * WebSocket消息
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class WebSocketMessage {
    /** 消息类型 */
    private String type;

    /** 消息ID */
    private String messageId;

    /** 数据 */
    private Map<String, Object> data;

    /** 时间戳 */
    private Long timestamp;

    /** 是否成功 */
    private Boolean success;

    /** 错误信息 */
    private String error;

    // ============ 上传相关字段 ============

    /** 文件名 */
    private String fileName;

    /** 文件大小（字节） */
    private Long fileSize;

    /** 文件MD5 */
    private String fileMd5;

    /** 上传ID */
    private String uploadId;

    /** 分片序号 */
    private Integer chunkNumber;

    /** 总分片数 */
    private Integer totalChunks;

    /** 分片数据（Base64编码） */
    private String chunkData;

    /** 进度百分比 */
    private Integer progress;

    // ============ 下载相关字段 ============

    /** 文件ID */
    private String fileId;

    /** 是否最后一个分片 */
    private Boolean isLast;

    // ============ 静态工厂方法 ============

    /**
     * 创建成功消息
     */
    public static WebSocketMessage success(String message, Map<String, Object> data) {
        return WebSocketMessage.builder()
                .type("success")
                .success(true)
                .data(data)
                .timestamp(System.currentTimeMillis())
                .build();
    }

    /**
     * 创建错误消息
     */
    public static WebSocketMessage error(String error) {
        return WebSocketMessage.builder()
                .type("error")
                .success(false)
                .error(error)
                .timestamp(System.currentTimeMillis())
                .build();
    }

    /**
     * 创建进度消息
     */
    public static WebSocketMessage progress(String uploadId, int progress) {
        return WebSocketMessage.builder()
                .type("progress")
                .success(true)
                .uploadId(uploadId)
                .progress(progress)
                .timestamp(System.currentTimeMillis())
                .build();
    }
}
