package com.yaocode.sts.components.file.web.websocket;

import com.yaocode.sts.common.tools.JSONUtils;
import com.yaocode.sts.components.file.runtime.model.dto.FileObjectDto;
import com.yaocode.sts.components.file.runtime.model.command.UploadFileCommand;
import com.yaocode.sts.components.file.runtime.model.result.UploadResult;
import com.yaocode.sts.components.file.runtime.service.FileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 文件WebSocket处理器
 * <p>
 * 支持通过WebSocket进行文件上传和下载
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
@Slf4j
public class FileWebSocketHandler extends TextWebSocketHandler {

    private final FileUploadService fileUploadService;

    /**
     * 存储所有活跃会话
     */
    private final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    /**
     * 存储会话对应的上传上下文
     */
    private final ConcurrentHashMap<String, UploadContext> uploadContexts = new ConcurrentHashMap<>();

    public FileWebSocketHandler(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String sessionId = session.getId();
        sessions.put(sessionId, session);
        log.info("WebSocket连接建立: {}", sessionId);

        // 发送连接成功消息
        sendMessage(session, WebSocketMessage.success("连接成功", Map.of("sessionId", sessionId)));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        WebSocketMessage wsMessage = JSONUtils.parseObject(payload, WebSocketMessage.class);

        String type = wsMessage.getType();

        switch (type) {
            case "upload_init":
                handleUploadInit(session, wsMessage);
                break;
            case "upload_chunk":
                handleUploadChunk(session, wsMessage);
                break;
            case "upload_complete":
                handleUploadComplete(session, wsMessage);
                break;
            case "download":
                handleDownload(session, wsMessage);
                break;
            case "pause":
                handlePause(session, wsMessage);
                break;
            case "resume":
                handleResume(session, wsMessage);
                break;
            case "progress":
                handleProgress(session, wsMessage);
                break;
            case "cancel":
                handleCancel(session, wsMessage);
                break;
            default:
                sendError(session, "未知消息类型: " + type);
        }
    }

    /**
     * 初始化上传
     */
    private void handleUploadInit(WebSocketSession session, WebSocketMessage message) {
        String fileName = message.getFileName();
        Long fileSize = message.getFileSize();
        String fileMd5 = message.getFileMd5();

        // 初始化上传
        UploadContext context = UploadContext.builder()
                .sessionId(session.getId())
                .fileName(fileName)
                .fileSize(fileSize)
                .fileMd5(fileMd5)
                .uploadId(UUID.randomUUID().toString())
                .totalChunks(message.getTotalChunks() != null ? message.getTotalChunks() :
                        (int) Math.ceil((double) fileSize / (10 * 1024 * 1024)))
                .chunkSize(10 * 1024 * 1024L)
                .build();

        uploadContexts.put(session.getId(), context);

        sendMessage(session, WebSocketMessage.success("上传初始化成功", Map.of(
                "uploadId", context.getUploadId(),
                "totalChunks", context.getTotalChunks(),
                "chunkSize", context.getChunkSize(),
                "message", "可以开始上传分片"
        )));
    }

    /**
     * 上传分片（Base64编码）
     */
    private void handleUploadChunk(WebSocketSession session, WebSocketMessage message) {
        String uploadId = message.getUploadId();
        Integer chunkNumber = message.getChunkNumber();
        String chunkData = message.getChunkData(); // Base64编码的分片数据

        UploadContext context = uploadContexts.get(session.getId());
        if (context == null || !context.getUploadId().equals(uploadId)) {
            sendError(session, "上传会话不存在或已过期");
            return;
        }

        try {
            // 解码Base64
            byte[] data = Base64.getDecoder().decode(chunkData);

            // 存储分片
            context.addChunk(chunkNumber, data);

            // 计算进度
            int completedChunks = context.getCompletedChunks();
            int totalChunks = context.getTotalChunks();
            int progress = (int) ((double) completedChunks / totalChunks * 100);

            // 发送进度
            sendMessage(session, WebSocketMessage.progress(context.getUploadId(), progress));

        } catch (Exception e) {
            log.error("分片上传失败", e);
            sendError(session, "分片上传失败: " + e.getMessage());
        }
    }

    /**
     * 完成上传
     */
    private void handleUploadComplete(WebSocketSession session, WebSocketMessage message) {
        UploadContext context = uploadContexts.get(session.getId());
        if (context == null) {
            sendError(session, "上传会话不存在");
            return;
        }

        try {
            // 合并分片
            byte[] fileData = context.mergeChunks();

            // 构建文件对象
            FileObjectDto fileObject = FileObjectDto.builder()
                    .fileName(context.getFileName())
                    .fileSize((long) fileData.length)
                    .inputStream(new ByteArrayInputStream(fileData))
                    .build();

            // 构建上传命令
            UploadFileCommand command = UploadFileCommand.builder()
                    .file(fileObject)
                    .fileName(context.getFileName())
                    .fileSize((long) fileData.length)
                    .fileMd5(context.getFileMd5())
                    .tenantId("default")
                    .userId("websocket")
                    .build();

            // 执行上传
            UploadResult result = fileUploadService.upload(command);

            // 发送成功消息
            sendMessage(session, WebSocketMessage.success("上传完成", Map.of(
                    "fileId", result.getFileId(),
                    "fileName", result.getFileName(),
                    "fileSize", result.getFileSize(),
                    "fileUrl", result.getFileUrl()
            )));

            // 清理上下文
            uploadContexts.remove(session.getId());

        } catch (Exception e) {
            log.error("完成上传失败", e);
            sendError(session, "完成上传失败: " + e.getMessage());
        }
    }

    /**
     * 下载文件
     */
    private void handleDownload(WebSocketSession session, WebSocketMessage message) {
        String fileId = message.getFileId();

        try {
            // TODO: 实现文件下载
            // 这里需要调用 FileDownloadService 或 FileUploadService 的下载方法
            // byte[] data = fileDownloadService.downloadFile(fileId);

            // 临时模拟
            byte[] data = "模拟文件内容".getBytes();

            // 分片发送
            int chunkSize = 1024 * 1024; // 1MB
            for (int i = 0; i < data.length; i += chunkSize) {
                int end = Math.min(i + chunkSize, data.length);
                byte[] chunk = java.util.Arrays.copyOfRange(data, i, end);

                WebSocketMessage response = WebSocketMessage.builder()
                        .type("download_chunk")
                        .chunkData(Base64.getEncoder().encodeToString(chunk))
                        .chunkNumber(i / chunkSize + 1)
                        .isLast(end == data.length)
                        .build();

                session.sendMessage(new TextMessage(JSONUtils.toJson(response)));
            }

        } catch (Exception e) {
            log.error("下载失败", e);
            sendError(session, "下载失败: " + e.getMessage());
        }
    }

    /**
     * 暂停上传
     */
    private void handlePause(WebSocketSession session, WebSocketMessage message) {
        String uploadId = message.getUploadId();
        UploadContext context = uploadContexts.get(session.getId());

        if (context == null || !context.getUploadId().equals(uploadId)) {
            sendError(session, "上传会话不存在");
            return;
        }

        context.setPaused(true);
        sendMessage(session, WebSocketMessage.success("上传已暂停", Map.of(
                "uploadId", uploadId,
                "message", "上传已暂停，可调用resume恢复"
        )));
    }

    /**
     * 恢复上传
     */
    private void handleResume(WebSocketSession session, WebSocketMessage message) {
        String uploadId = message.getUploadId();
        UploadContext context = uploadContexts.get(session.getId());

        if (context == null || !context.getUploadId().equals(uploadId)) {
            sendError(session, "上传会话不存在");
            return;
        }

        context.setPaused(false);
        sendMessage(session, WebSocketMessage.success("上传已恢复", Map.of(
                "uploadId", uploadId,
                "progress", context.getProgress(),
                "message", "上传已恢复"
        )));
    }

    /**
     * 查询进度
     */
    private void handleProgress(WebSocketSession session, WebSocketMessage message) {
        String uploadId = message.getUploadId();
        UploadContext context = uploadContexts.get(session.getId());

        if (context == null || !context.getUploadId().equals(uploadId)) {
            sendError(session, "上传会话不存在");
            return;
        }

        sendMessage(session, WebSocketMessage.progress(
                context.getUploadId(),
                context.getProgress()
        ));
    }

    /**
     * 取消上传
     */
    private void handleCancel(WebSocketSession session, WebSocketMessage message) {
        String uploadId = message.getUploadId();
        UploadContext context = uploadContexts.get(session.getId());

        if (context != null && context.getUploadId().equals(uploadId)) {
            context.cancel();
            uploadContexts.remove(session.getId());
            sendMessage(session, WebSocketMessage.success("上传已取消", Map.of(
                    "uploadId", uploadId,
                    "message", "上传已取消"
            )));
        } else {
            sendError(session, "上传会话不存在");
        }
    }

    /**
     * 发送消息
     */
    private void sendMessage(WebSocketSession session, WebSocketMessage message) {
        try {
            session.sendMessage(new TextMessage(JSONUtils.toJson(message)));
        } catch (Exception e) {
            log.error("发送WebSocket消息失败", e);
        }
    }

    /**
     * 发送错误消息
     */
    private void sendError(WebSocketSession session, String error) {
        sendMessage(session, WebSocketMessage.error(error));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String sessionId = session.getId();
        sessions.remove(sessionId);

        // 清理上传上下文
        UploadContext context = uploadContexts.remove(sessionId);
        if (context != null) {
            context.cancel();
        }

        log.info("WebSocket连接关闭: {}, 状态: {}", sessionId, status);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        log.error("WebSocket传输错误: {}", session.getId(), exception);
        try {
            session.close(CloseStatus.SERVER_ERROR);
        } catch (Exception e) {
            log.error("关闭WebSocket会话失败", e);
        }
    }
}