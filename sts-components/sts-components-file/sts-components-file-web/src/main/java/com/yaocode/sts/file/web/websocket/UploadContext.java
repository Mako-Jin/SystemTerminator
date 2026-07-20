package com.yaocode.sts.file.web.websocket;

import lombok.Builder;
import lombok.Data;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 上传上下文
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class UploadContext {
    /** 会话ID */
    private String sessionId;

    /** 上传ID */
    private String uploadId;

    /** 文件名 */
    private String fileName;

    /** 文件大小（字节） */
    private Long fileSize;

    /** 文件MD5 */
    private String fileMd5;

    /** 总分片数 */
    private Integer totalChunks;

    /** 分片大小（字节） */
    private Long chunkSize;

    /** 已完成的分数 */
    private Integer completedChunks;

    /** 是否暂停 */
    private boolean paused;

    /** 是否取消 */
    private boolean cancelled;

    /** 分片数据（内存存储，适合小文件） */
    @Builder.Default
    private ConcurrentHashMap<Integer, byte[]> chunks = new ConcurrentHashMap<>();

    /**
     * 添加分片
     */
    public synchronized void addChunk(Integer chunkNumber, byte[] data) {
        if (cancelled) {
            throw new IllegalStateException("上传已取消");
        }
        chunks.put(chunkNumber, data);
        completedChunks = chunks.size();
    }

    /**
     * 合并分片
     */
    public byte[] mergeChunks() {
        if (chunks.size() != totalChunks) {
            throw new IllegalStateException("分片不完整: " + chunks.size() + "/" + totalChunks);
        }

        byte[] result = new byte[chunkSize.intValue() * totalChunks];
        for (int i = 1; i <= totalChunks; i++) {
            byte[] chunk = chunks.get(i);
            if (chunk == null) {
                throw new IllegalStateException("分片缺失: " + i);
            }
            System.arraycopy(chunk, 0, result, (i - 1) * chunkSize.intValue(), chunk.length);
        }
        return result;
    }

    /**
     * 获取进度
     */
    public int getProgress() {
        if (totalChunks == null || totalChunks == 0) {
            return 0;
        }
        return (int) ((double) (completedChunks != null ? completedChunks : 0) / totalChunks * 100);
    }

    /**
     * 取消上传
     */
    public void cancel() {
        this.cancelled = true;
        this.chunks.clear();
    }
}
