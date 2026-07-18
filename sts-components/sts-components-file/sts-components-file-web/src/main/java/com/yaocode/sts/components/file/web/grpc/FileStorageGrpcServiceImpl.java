package com.yaocode.sts.components.file.web.grpc;

import com.google.protobuf.ByteString;
import com.yaocode.sts.components.file.runtime.model.command.UploadFileCommand;
import com.yaocode.sts.components.file.runtime.model.dto.FileObjectDto;
import com.yaocode.sts.components.file.runtime.model.result.UploadResult;
import com.yaocode.sts.components.file.runtime.service.FileUploadService;
import com.yaocode.sts.components.file.runtime.service.FileDownloadService;
import com.yaocode.sts.components.file.interfaces.proto.*;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.grpc.server.service.GrpcService;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.util.Arrays;

/**
 * gRPC文件存储服务实现
 * 使用 Spring gRPC Starter 实现所有接口
 *
 * @author yaocode
 * @since 1.0.0
 */
@Slf4j
@GrpcService
@RequiredArgsConstructor
public class FileStorageGrpcServiceImpl extends FileStorageServiceGrpc.FileStorageServiceImplBase {

    private final FileUploadService fileUploadService;
    private final FileDownloadService fileDownloadService;
//    private final FileInfoService fileInfoService;
//    private final FileDeleteService fileDeleteService;

    // ==================== 文件上传 ====================
    @Override
    public void uploadFile(UploadRequest request, StreamObserver<UploadResponse> responseObserver) {
        try {
            log.info("gRPC上传文件: {}, 大小: {} bytes", request.getFileName(), request.getFileSize());

            // 参数校验
            if (!StringUtils.hasText(request.getFileName())) {
                throw new IllegalArgumentException("文件名不能为空");
            }
            if (request.getContent().isEmpty()) {
                throw new IllegalArgumentException("文件内容不能为空");
            }

            // 构建文件对象
            FileObjectDto fileObject = FileObjectDto.builder()
                    .fileName(request.getFileName())
                    .fileSize(request.getFileSize())
                    .inputStream(new ByteArrayInputStream(request.getContent().toByteArray()))
                    .build();

            // 构建上传命令
            UploadFileCommand command = UploadFileCommand.builder()
                    .file(fileObject)
                    .fileName(request.getFileName())
                    .fileSize(request.getFileSize())
                    .storageType(request.getStorageType())
                    .businessId(request.getBusinessId())
                    .businessType(request.getBusinessType())
                    .metadata(request.getMetadataMap())
                    .tenantId(extractTenantId(request))
                    .userId(extractUserId(request))
                    .build();

            // 执行上传
            UploadResult result = fileUploadService.upload(command);

            // 构建响应
            UploadResponse response = UploadResponse.newBuilder()
                    .setFileId(result.getFileId() != null ? result.getFileId() : "")
                    .setFileName(result.getFileName() != null ? result.getFileName() : "")
                    .setFileSize(result.getFileSize() != null ? result.getFileSize() : 0L)
                    .setFileMd5(result.getFileMd5() != null ? result.getFileMd5() : "")
                    .setFileUrl(result.getFileUrl() != null ? result.getFileUrl() : "")
                    .setStorageType(result.getStorageType() != null ? result.getStorageType() : "")
                    .setUploadStatus(result.getUploadStatus() != null ? result.getUploadStatus() : 1)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

            log.info("gRPC上传文件成功: fileId={}, fileName={}", result.getFileId(), result.getFileName());

        } catch (IllegalArgumentException e) {
            log.warn("gRPC上传参数错误: {}", e.getMessage());
            responseObserver.onError(io.grpc.Status.INVALID_ARGUMENT
                    .withDescription(e.getMessage())
                    .asRuntimeException());
        } catch (Exception e) {
            log.error("gRPC上传失败", e);
            responseObserver.onError(io.grpc.Status.INTERNAL
                    .withDescription("上传文件失败: " + e.getMessage())
                    .asRuntimeException());
        }
    }

    // ==================== 分片上传初始化 ====================
    @Override
    public void initMultipartUpload(InitMultipartRequest request,
                                    StreamObserver<InitMultipartResponse> responseObserver) {
        try {
            log.info("初始化分片上传: fileName={}, size={}, chunks={}",
                    request.getFileName(), request.getFileSize(),
                    (request.getFileSize() + request.getChunkSize() - 1) / request.getChunkSize());

            // TODO: 调用实际的初始化服务
            // 这里需要实现分片上传的初始化逻辑

            int totalChunks = (int) ((request.getFileSize() + request.getChunkSize() - 1) / request.getChunkSize());
            String uploadId = generateUploadId(request);

            InitMultipartResponse response = InitMultipartResponse.newBuilder()
                    .setUploadId(uploadId)
                    .setFileId(generateFileId())
                    .setTotalChunks(totalChunks)
                    .setChunkSize(request.getChunkSize())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

            log.info("分片上传初始化成功: uploadId={}, totalChunks={}", uploadId, totalChunks);

        } catch (Exception e) {
            log.error("初始化分片上传失败", e);
            responseObserver.onError(e);
        }
    }

    // ==================== 上传分片 ====================
    @Override
    public void uploadPart(UploadPartRequest request, StreamObserver<UploadPartResponse> responseObserver) {
        try {
            log.info("上传分片: uploadId={}, chunkNumber={}, size={} bytes",
                    request.getUploadId(), request.getChunkNumber(), request.getChunkData().size());

            // TODO: 实现分片上传逻辑

            // 模拟分片上传
            // boolean success = multipartUploadService.uploadPart(
            //     request.getUploadId(),
            //     request.getChunkNumber(),
            //     request.getChunkData().toByteArray()
            // );

            UploadPartResponse response = UploadPartResponse.newBuilder()
                    .setUploadId(request.getUploadId())
                    .setChunkNumber(request.getChunkNumber())
                    .setSuccess(true)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (Exception e) {
            log.error("上传分片失败: uploadId={}, chunkNumber={}", request.getUploadId(), request.getChunkNumber(), e);
            responseObserver.onError(e);
        }
    }

    // ==================== 完成分片上传 ====================
    @Override
    public void completeMultipartUpload(CompleteMultipartRequest request,
                                        StreamObserver<UploadResponse> responseObserver) {
        try {
            log.info("完成分片上传: uploadId={}", request.getUploadId());

            // TODO: 实现完成分片上传逻辑
            // UploadResult result = multipartUploadService.complete(request.getUploadId());

            // 模拟响应
            UploadResponse response = UploadResponse.newBuilder()
                    .setFileId("file_" + System.currentTimeMillis())
                    .setFileName("completed_file.pdf")
                    .setFileSize(1024L * 1024)
                    .setFileMd5("md5_hash")
                    .setFileUrl("http://storage.example.com/file.pdf")
                    .setStorageType("minio")
                    .setUploadStatus(2) // 已完成
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

            log.info("分片上传完成: uploadId={}", request.getUploadId());

        } catch (Exception e) {
            log.error("完成分片上传失败", e);
            responseObserver.onError(e);
        }
    }

    // ==================== 文件下载（流式） ====================
    @Override
    public void downloadFile(DownloadRequest request, StreamObserver<DownloadResponse> responseObserver) {
        try {
            log.info("gRPC下载文件: fileId={}", request.getFileId());

            if (!StringUtils.hasText(request.getFileId())) {
                throw new IllegalArgumentException("文件ID不能为空");
            }

            // 调用下载服务获取文件流
            // FileDownloadResult downloadResult = fileDownloadService.download(request.getFileId());
            // InputStream inputStream = downloadResult.getInputStream();

            // 模拟下载数据（实际应使用真实文件流）
            byte[] data = generateMockFileData(request.getFileId());
            int chunkSize = 1024 * 1024; // 1MB per chunk
            int totalChunks = (data.length + chunkSize - 1) / chunkSize;

            for (int i = 0; i < totalChunks; i++) {
                int start = i * chunkSize;
                int end = Math.min(start + chunkSize, data.length);
                byte[] chunk = Arrays.copyOfRange(data, start, end);

                DownloadResponse response = DownloadResponse.newBuilder()
                        .setChunkData(ByteString.copyFrom(chunk))
                        .setChunkNumber(i + 1)
                        .setIsLast(i == totalChunks - 1)
                        .setTotalSize(data.length)
                        .build();

                responseObserver.onNext(response);

                // 可选：添加小延迟避免内存溢出
                if (i % 10 == 0) {
                    Thread.sleep(10);
                }
            }

            responseObserver.onCompleted();
            log.info("gRPC下载文件完成: fileId={}, totalChunks={}, totalSize={}",
                    request.getFileId(), totalChunks, data.length);

        } catch (IllegalArgumentException e) {
            log.warn("gRPC下载参数错误: {}", e.getMessage());
            responseObserver.onError(io.grpc.Status.INVALID_ARGUMENT
                    .withDescription(e.getMessage())
                    .asRuntimeException());
        } catch (Exception e) {
            log.error("gRPC下载失败: fileId={}", request.getFileId(), e);
            responseObserver.onError(io.grpc.Status.INTERNAL
                    .withDescription("下载文件失败: " + e.getMessage())
                    .asRuntimeException());
        }
    }

    // ==================== 删除文件 ====================
    @Override
    public void deleteFile(DeleteRequest request, StreamObserver<DeleteResponse> responseObserver) {
        try {
            log.info("gRPC删除文件: fileId={}, storageType={}", request.getFileId(), request.getStorageType());

            if (!StringUtils.hasText(request.getFileId())) {
                throw new IllegalArgumentException("文件ID不能为空");
            }

            // 调用删除服务
            // boolean deleted = fileDeleteService.delete(request.getFileId(), request.getStorageType());
            boolean deleted = true; // 模拟

            DeleteResponse response = DeleteResponse.newBuilder()
                    .setSuccess(deleted)
                    .setMessage(deleted ? "删除成功" : "删除失败")
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

            log.info("gRPC删除文件{}: fileId={}", deleted ? "成功" : "失败", request.getFileId());

        } catch (IllegalArgumentException e) {
            log.warn("gRPC删除参数错误: {}", e.getMessage());
            responseObserver.onError(io.grpc.Status.INVALID_ARGUMENT
                    .withDescription(e.getMessage())
                    .asRuntimeException());
        } catch (Exception e) {
            log.error("gRPC删除失败: fileId={}", request.getFileId(), e);
            responseObserver.onError(e);
        }
    }

    // ==================== 获取文件信息 ====================
    @Override
    public void getFileInfo(FileInfoRequest request, StreamObserver<FileInfoResponse> responseObserver) {
        try {
            log.info("gRPC获取文件信息: fileId={}", request.getFileId());

            if (!StringUtils.hasText(request.getFileId())) {
                throw new IllegalArgumentException("文件ID不能为空");
            }

            // 调用文件信息服务
            // FileInfo fileInfo = fileInfoService.getFileInfo(request.getFileId());

            // 模拟文件信息
            FileInfoResponse response = FileInfoResponse.newBuilder()
                    .setFileId(request.getFileId())
                    .setFileName("example_" + request.getFileId() + ".pdf")
                    .setFileSize(2048L * 1024)
                    .setFileMd5("d41d8cd98f00b204e9800998ecf8427e")
                    .setStorageType("minio")
                    .setFileUrl("http://storage.example.com/" + request.getFileId())
                    .setFileStatus(1) // 1: 正常
                    .setUploadTime(System.currentTimeMillis())
                    .setDownloadCount(42)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

            log.info("gRPC获取文件信息成功: fileId={}", request.getFileId());

        } catch (IllegalArgumentException e) {
            log.warn("gRPC获取文件信息参数错误: {}", e.getMessage());
            responseObserver.onError(io.grpc.Status.INVALID_ARGUMENT
                    .withDescription(e.getMessage())
                    .asRuntimeException());
        } catch (Exception e) {
            log.error("gRPC获取文件信息失败: fileId={}", request.getFileId(), e);
            responseObserver.onError(e);
        }
    }

    // ==================== 秒传检测 ====================
    @Override
    public void checkFileExists(CheckFileRequest request, StreamObserver<CheckFileResponse> responseObserver) {
        try {
            log.info("gRPC秒传检测: md5={}, size={}", request.getFileMd5(), request.getFileSize());

            if (!StringUtils.hasText(request.getFileMd5())) {
                throw new IllegalArgumentException("文件MD5不能为空");
            }

            // 调用秒传检测服务
            // CheckResult checkResult = fileCheckService.checkExists(request.getFileMd5(), request.getFileSize());

            // 模拟检测结果
            boolean exists = false; // 模拟不存在
            CheckFileResponse.Builder builder = CheckFileResponse.newBuilder()
                    .setExists(exists);

            if (exists) {
                builder.setFileId("file_12345")
                        .setFileName("existing_file.pdf")
                        .setFileUrl("http://storage.example.com/existing_file.pdf");
            }

            CheckFileResponse response = builder.build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

            log.info("gRPC秒传检测完成: exists={}", exists);

        } catch (IllegalArgumentException e) {
            log.warn("gRPC秒传检测参数错误: {}", e.getMessage());
            responseObserver.onError(io.grpc.Status.INVALID_ARGUMENT
                    .withDescription(e.getMessage())
                    .asRuntimeException());
        } catch (Exception e) {
            log.error("gRPC秒传检测失败", e);
            responseObserver.onError(e);
        }
    }

    // ==================== 健康检查 ====================
    @Override
    public void healthCheck(HealthRequest request, StreamObserver<HealthResponse> responseObserver) {
        HealthResponse response = HealthResponse.newBuilder()
                .setStatus("UP")
                .setVersion("1.0.0")
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
        log.debug("gRPC健康检查: UP");
    }

    // ==================== 辅助方法 ====================

    private String extractTenantId(UploadRequest request) {
        return request.getMetadataMap().getOrDefault("tenantId", "default");
    }

    private String extractUserId(UploadRequest request) {
        return request.getMetadataMap().getOrDefault("userId", "system");
    }

    private String generateUploadId(InitMultipartRequest request) {
        return "upload_" + System.currentTimeMillis() + "_" + request.getFileMd5().substring(0, 8);
    }

    private String generateFileId() {
        return "file_" + System.currentTimeMillis() + "_" + System.nanoTime() % 10000;
    }

    private byte[] generateMockFileData(String fileId) {
        // 模拟生成 10MB 测试数据
        int size = 10 * 1024 * 1024;
        byte[] data = new byte[size];
        for (int i = 0; i < size; i++) {
            data[i] = (byte) (i % 256);
        }
        return data;
    }
}