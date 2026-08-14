package com.yaocode.sts.file.application.service.impl;

import com.yaocode.sts.file.application.converter.FileUploadApplicationConverter;
import com.yaocode.sts.file.application.model.command.UploadBatchCommand;
import com.yaocode.sts.file.application.model.command.UploadFileCommand;
import com.yaocode.sts.file.application.model.dto.FileObjectDto;
import com.yaocode.sts.file.application.model.result.UploadResult;
import com.yaocode.sts.file.application.service.handler.*;
import com.yaocode.sts.file.infrastructure.dao.FileBaseInfoDao;
import com.yaocode.sts.file.infrastructure.dao.FileDeduplicationDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * FileUploadServiceImpl 单元测试
 * 重点覆盖 uploadBatch 批量上传逻辑
 *
 * @author yaocode
 * @since 1.0.0
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("文件上传服务测试")
class FileUploadServiceImplTest {

    @Mock
    private FileUploadValidationHandler validationHandler;
    @Mock
    private FileUploadPreparationHandler preparationHandler;
    @Mock
    private FileStorageSelectionHandler storageSelectionHandler;
    @Mock
    private FileDeduplicationHandler deduplicationHandler;
    @Mock
    private FileUploadExecutionHandler executionHandler;
    @Mock
    private FilePersistenceHandler persistenceHandler;
    @Mock
    private FileUploadCleanupHandler cleanupHandler;
    @Mock
    private FileDeduplicationDao fileDeduplicationDao;
    @Mock
    private FileBaseInfoDao fileBaseInfoDao;
    @Mock
    private FileUploadApplicationConverter converter;

    @Spy
    @InjectMocks
    private FileUploadServiceImpl service;

    // ==================== 通用测试数据 ====================

    private static final String TENANT_ID = "TENANT-001";
    private static final String USER_ID = "USER-001";
    private static final String FILE_ID_1 = "FILE-001";
    private static final String FILE_ID_2 = "FILE-002";
    private static final String FILE_ID_3 = "FILE-003";
    private static final String FILE_NAME_1 = "document.pdf";
    private static final String FILE_NAME_2 = "image.png";
    private static final String FILE_NAME_3 = "video.mp4";
    private static final Long FILE_SIZE_1 = 1024L;
    private static final Long FILE_SIZE_2 = 2048L;
    private static final Long FILE_SIZE_3 = 1048576L;
    private static final String FILE_MD5_1 = "md5-001";
    private static final String FILE_MD5_2 = "md5-002";
    private static final String FILE_MD5_3 = "md5-003";
    private static final Integer STORAGE_TYPE = 1;
    private static final String TAGS = "important,archive";
    private static final String DESCRIPTION = "测试文件";

    private FileObjectDto buildFileDto(String fileName, Long fileSize, String md5) {
        return FileObjectDto.builder()
                .fileName(fileName)
                .fileSize(fileSize)
                .inputStream(new ByteArrayInputStream(new byte[0]))
                .md5(md5)
                .contentType("application/octet-stream")
                .build();
    }

    private UploadBatchCommand buildBatchCommand(List<FileObjectDto> files) {
        return UploadBatchCommand.builder()
                .files(files)
                .storageType(STORAGE_TYPE)
                .tags(TAGS)
                .description(DESCRIPTION)
                .isPublic(1)
                .tenantId(TENANT_ID)
                .userId(USER_ID)
                .build();
    }

    private UploadResult buildUploadResult(String fileId, String fileName, Long fileSize, String md5) {
        return UploadResult.builder()
                .fileId(fileId)
                .fileName(fileName)
                .fileSize(fileSize)
                .fileMd5(md5)
                .storageType(STORAGE_TYPE)
                .uploadStatus(1)
                .uploadStatusDesc("上传成功")
                .isDuplicate(false)
                .processingTime(100L)
                .message("ok")
                .build();
    }

    private UploadFileCommand buildFileCommand(FileObjectDto file) {
        return UploadFileCommand.builder()
                .file(file)
                .fileName(file.getFileName())
                .fileSize(file.getFileSize())
                .fileMd5(file.getMd5())
                .storageType(STORAGE_TYPE)
                .tags(TAGS)
                .description(DESCRIPTION)
                .isPublic(1)
                .enableDeduplication(1)
                .tenantId(TENANT_ID)
                .userId(USER_ID)
                .build();
    }

    // ==================== 1. uploadBatch —— 边界场景 ====================

    @Nested
    @DisplayName("uploadBatch —— 边界场景")
    class BoundaryTests {

        @Test
        @DisplayName("文件列表为 null —— 返回空列表")
        void should_returnEmptyList_when_filesIsNull() {
            UploadBatchCommand command = buildBatchCommand(null);

            List<UploadResult> results = service.uploadBatch(command);

            assertThat(results).isNotNull();
            assertThat(results).isEmpty();
        }

        @Test
        @DisplayName("文件列表为空 —— 返回空列表")
        void should_returnEmptyList_when_filesIsEmpty() {
            UploadBatchCommand command = buildBatchCommand(new ArrayList<>());

            List<UploadResult> results = service.uploadBatch(command);

            assertThat(results).isNotNull();
            assertThat(results).isEmpty();
        }

        @Test
        @DisplayName("null 与空列表都不应调用 converter 和 upload")
        void should_notCallConverterOrUpload_when_filesNullOrEmpty() {
            service.uploadBatch(buildBatchCommand(null));
            service.uploadBatch(buildBatchCommand(new ArrayList<>()));

            verify(converter, never()).buildUploadFileCommand(any(), any());
            verify(service, never()).upload(any(UploadFileCommand.class));
        }
    }

    // ==================== 2. uploadBatch —— 正常场景 ====================

    @Nested
    @DisplayName("uploadBatch —— 正常场景")
    class NormalTests {

        @Test
        @DisplayName("单文件批量上传 —— 成功")
        void should_uploadSingleFile_when_oneFileProvided() {
            FileObjectDto file = buildFileDto(FILE_NAME_1, FILE_SIZE_1, FILE_MD5_1);
            UploadBatchCommand command = buildBatchCommand(List.of(file));
            UploadFileCommand fileCommand = buildFileCommand(file);
            UploadResult result = buildUploadResult(FILE_ID_1, FILE_NAME_1, FILE_SIZE_1, FILE_MD5_1);

            when(converter.buildUploadFileCommand(command, file)).thenReturn(fileCommand);
            doReturn(result).when(service).upload(fileCommand);

            List<UploadResult> results = service.uploadBatch(command);

            assertThat(results).hasSize(1);
            assertThat(results.get(0).getFileId()).isEqualTo(FILE_ID_1);
            assertThat(results.get(0).getFileName()).isEqualTo(FILE_NAME_1);
            assertThat(results.get(0).getUploadStatus()).isEqualTo(1);
            verify(converter).buildUploadFileCommand(command, file);
            verify(service).upload(fileCommand);
        }

        @Test
        @DisplayName("多文件批量上传 —— 全部成功")
        void should_uploadAllFiles_when_allSucceed() {
            FileObjectDto file1 = buildFileDto(FILE_NAME_1, FILE_SIZE_1, FILE_MD5_1);
            FileObjectDto file2 = buildFileDto(FILE_NAME_2, FILE_SIZE_2, FILE_MD5_2);
            FileObjectDto file3 = buildFileDto(FILE_NAME_3, FILE_SIZE_3, FILE_MD5_3);
            UploadBatchCommand command = buildBatchCommand(List.of(file1, file2, file3));

            UploadFileCommand cmd1 = buildFileCommand(file1);
            UploadFileCommand cmd2 = buildFileCommand(file2);
            UploadFileCommand cmd3 = buildFileCommand(file3);

            when(converter.buildUploadFileCommand(command, file1)).thenReturn(cmd1);
            when(converter.buildUploadFileCommand(command, file2)).thenReturn(cmd2);
            when(converter.buildUploadFileCommand(command, file3)).thenReturn(cmd3);

            doReturn(buildUploadResult(FILE_ID_1, FILE_NAME_1, FILE_SIZE_1, FILE_MD5_1)).when(service).upload(cmd1);
            doReturn(buildUploadResult(FILE_ID_2, FILE_NAME_2, FILE_SIZE_2, FILE_MD5_2)).when(service).upload(cmd2);
            doReturn(buildUploadResult(FILE_ID_3, FILE_NAME_3, FILE_SIZE_3, FILE_MD5_3)).when(service).upload(cmd3);

            List<UploadResult> results = service.uploadBatch(command);

            assertThat(results).hasSize(3);
            assertThat(results).extracting(UploadResult::getFileId)
                    .containsExactly(FILE_ID_1, FILE_ID_2, FILE_ID_3);
            assertThat(results).extracting(UploadResult::getFileName)
                    .containsExactly(FILE_NAME_1, FILE_NAME_2, FILE_NAME_3);
            verify(converter, times(3)).buildUploadFileCommand(any(), any());
            verify(service, times(3)).upload(any(UploadFileCommand.class));
        }

        @Test
        @DisplayName("批量上传 —— 验证 converter 接收正确的 batchCommand + file 参数")
        void should_passCorrectParamsToConverter_when_batchUpload() {
            FileObjectDto file = buildFileDto(FILE_NAME_1, FILE_SIZE_1, FILE_MD5_1);
            UploadBatchCommand command = buildBatchCommand(List.of(file));
            UploadFileCommand fileCommand = buildFileCommand(file);

            when(converter.buildUploadFileCommand(command, file)).thenReturn(fileCommand);
            doReturn(buildUploadResult(FILE_ID_1, FILE_NAME_1, FILE_SIZE_1, FILE_MD5_1))
                    .when(service).upload(fileCommand);

            service.uploadBatch(command);

            verify(converter).buildUploadFileCommand(
                    argThat(cmd -> cmd.getStorageType().equals(STORAGE_TYPE)
                            && cmd.getTags().equals(TAGS)
                            && cmd.getTenantId().equals(TENANT_ID)
                            && cmd.getUserId().equals(USER_ID)),
                    argThat(f -> f.getFileName().equals(FILE_NAME_1)
                            && f.getFileSize().equals(FILE_SIZE_1)
                            && f.getMd5().equals(FILE_MD5_1))
            );
        }

        @Test
        @DisplayName("批量上传 —— 验证 upload 被按顺序调用")
        void should_callUploadInOrder_when_batchUpload() {
            FileObjectDto file1 = buildFileDto(FILE_NAME_1, FILE_SIZE_1, FILE_MD5_1);
            FileObjectDto file2 = buildFileDto(FILE_NAME_2, FILE_SIZE_2, FILE_MD5_2);
            UploadBatchCommand command = buildBatchCommand(List.of(file1, file2));

            UploadFileCommand cmd1 = buildFileCommand(file1);
            UploadFileCommand cmd2 = buildFileCommand(file2);
            when(converter.buildUploadFileCommand(command, file1)).thenReturn(cmd1);
            when(converter.buildUploadFileCommand(command, file2)).thenReturn(cmd2);
            doReturn(buildUploadResult(FILE_ID_1, FILE_NAME_1, FILE_SIZE_1, FILE_MD5_1)).when(service).upload(cmd1);
            doReturn(buildUploadResult(FILE_ID_2, FILE_NAME_2, FILE_SIZE_2, FILE_MD5_2)).when(service).upload(cmd2);

            service.uploadBatch(command);

            var order = inOrder(service);
            order.verify(service).upload(cmd1);
            order.verify(service).upload(cmd2);
        }
    }

    // ==================== 3. uploadBatch —— 异常隔离场景 ====================

    @Nested
    @DisplayName("uploadBatch —— 异常隔离（部分失败不影响整体）")
    class ExceptionIsolationTests {

        @Test
        @DisplayName("第二个文件失败 —— 返回3条结果（成功+失败详情）")
        void should_isolateFailure_when_secondFileFails() {
            FileObjectDto file1 = buildFileDto(FILE_NAME_1, FILE_SIZE_1, FILE_MD5_1);
            FileObjectDto file2 = buildFileDto(FILE_NAME_2, FILE_SIZE_2, FILE_MD5_2);
            FileObjectDto file3 = buildFileDto(FILE_NAME_3, FILE_SIZE_3, FILE_MD5_3);
            UploadBatchCommand command = buildBatchCommand(List.of(file1, file2, file3));

            UploadFileCommand cmd1 = buildFileCommand(file1);
            UploadFileCommand cmd2 = buildFileCommand(file2);
            UploadFileCommand cmd3 = buildFileCommand(file3);

            when(converter.buildUploadFileCommand(command, file1)).thenReturn(cmd1);
            when(converter.buildUploadFileCommand(command, file2)).thenReturn(cmd2);
            when(converter.buildUploadFileCommand(command, file3)).thenReturn(cmd3);

            doReturn(buildUploadResult(FILE_ID_1, FILE_NAME_1, FILE_SIZE_1, FILE_MD5_1)).when(service).upload(cmd1);
            doThrow(new RuntimeException("存储失败: " + FILE_NAME_2)).when(service).upload(cmd2);
            doReturn(buildUploadResult(FILE_ID_3, FILE_NAME_3, FILE_SIZE_3, FILE_MD5_3)).when(service).upload(cmd3);

            List<UploadResult> results = service.uploadBatch(command);

            assertThat(results).hasSize(3);
            // 第1个成功
            assertThat(results.get(0).getUploadStatus()).isEqualTo(1);
            assertThat(results.get(0).getFileId()).isEqualTo(FILE_ID_1);
            // 第2个失败
            assertThat(results.get(1).getUploadStatus()).isEqualTo(2);
            assertThat(results.get(1).getFileName()).isEqualTo(FILE_NAME_2);
            assertThat(results.get(1).getMessage()).contains("存储失败");
            // 第3个成功
            assertThat(results.get(2).getUploadStatus()).isEqualTo(1);
            assertThat(results.get(2).getFileId()).isEqualTo(FILE_ID_3);
            verify(service, times(3)).upload(any(UploadFileCommand.class));
        }

        @Test
        @DisplayName("全部文件失败 —— 返回全部失败结果，不抛异常")
        void should_returnAllFailedResults_when_allFilesFail() {
            FileObjectDto file1 = buildFileDto(FILE_NAME_1, FILE_SIZE_1, FILE_MD5_1);
            FileObjectDto file2 = buildFileDto(FILE_NAME_2, FILE_SIZE_2, FILE_MD5_2);
            UploadBatchCommand command = buildBatchCommand(List.of(file1, file2));

            UploadFileCommand cmd1 = buildFileCommand(file1);
            UploadFileCommand cmd2 = buildFileCommand(file2);
            when(converter.buildUploadFileCommand(command, file1)).thenReturn(cmd1);
            when(converter.buildUploadFileCommand(command, file2)).thenReturn(cmd2);
            doThrow(new RuntimeException("存储不可用")).when(service).upload(cmd1);
            doThrow(new RuntimeException("存储不可用")).when(service).upload(cmd2);

            List<UploadResult> results = service.uploadBatch(command);

            assertThat(results).isNotNull();
            assertThat(results).hasSize(2);
            assertThat(results).allMatch(r -> r.getUploadStatus() == 2);
            assertThat(results).extracting(UploadResult::getFileName)
                    .containsExactly(FILE_NAME_1, FILE_NAME_2);
        }

        @Test
        @DisplayName("第一个文件失败 —— 返回2条结果（失败+成功），后续文件仍继续处理")
        void should_continueAfterFailure_when_firstFileFails() {
            FileObjectDto file1 = buildFileDto(FILE_NAME_1, FILE_SIZE_1, FILE_MD5_1);
            FileObjectDto file2 = buildFileDto(FILE_NAME_2, FILE_SIZE_2, FILE_MD5_2);
            UploadBatchCommand command = buildBatchCommand(List.of(file1, file2));

            UploadFileCommand cmd1 = buildFileCommand(file1);
            UploadFileCommand cmd2 = buildFileCommand(file2);
            when(converter.buildUploadFileCommand(command, file1)).thenReturn(cmd1);
            when(converter.buildUploadFileCommand(command, file2)).thenReturn(cmd2);
            doThrow(new RuntimeException("第一个文件失败")).when(service).upload(cmd1);
            doReturn(buildUploadResult(FILE_ID_2, FILE_NAME_2, FILE_SIZE_2, FILE_MD5_2)).when(service).upload(cmd2);

            List<UploadResult> results = service.uploadBatch(command);

            assertThat(results).hasSize(2);
            // 第1个失败
            assertThat(results.get(0).getUploadStatus()).isEqualTo(2);
            assertThat(results.get(0).getFileName()).isEqualTo(FILE_NAME_1);
            assertThat(results.get(0).getMessage()).isEqualTo("第一个文件失败");
            // 第2个成功
            assertThat(results.get(1).getUploadStatus()).isEqualTo(1);
            assertThat(results.get(1).getFileId()).isEqualTo(FILE_ID_2);
            verify(service).upload(cmd1);
            verify(service).upload(cmd2);
        }

        @Test
        @DisplayName("中间文件 converter 抛出异常 —— 返回2条结果（成功+失败详情）")
        void should_returnFailureResult_when_converterThrows() {
            FileObjectDto file1 = buildFileDto(FILE_NAME_1, FILE_SIZE_1, FILE_MD5_1);
            FileObjectDto file2 = buildFileDto(FILE_NAME_2, FILE_SIZE_2, FILE_MD5_2);
            UploadBatchCommand command = buildBatchCommand(List.of(file1, file2));

            UploadFileCommand cmd1 = buildFileCommand(file1);
            when(converter.buildUploadFileCommand(command, file1)).thenReturn(cmd1);
            when(converter.buildUploadFileCommand(command, file2))
                    .thenThrow(new IllegalArgumentException("文件名非法"));
            doReturn(buildUploadResult(FILE_ID_1, FILE_NAME_1, FILE_SIZE_1, FILE_MD5_1)).when(service).upload(cmd1);

            List<UploadResult> results = service.uploadBatch(command);

            assertThat(results).hasSize(2);
            // 第1个成功
            assertThat(results.get(0).getUploadStatus()).isEqualTo(1);
            assertThat(results.get(0).getFileId()).isEqualTo(FILE_ID_1);
            // 第2个失败（converter 异常）
            assertThat(results.get(1).getUploadStatus()).isEqualTo(2);
            assertThat(results.get(1).getFileName()).isEqualTo(FILE_NAME_2);
            assertThat(results.get(1).getMessage()).isEqualTo("文件名非法");
        }
    }

    // ==================== 4. uploadBatch —— 结果验证 ====================

    @Nested
    @DisplayName("uploadBatch —— 结果正确性")
    class ResultValidationTests {

        @Test
        @DisplayName("结果列表与文件顺序一致")
        void should_preserveOrder_when_batchUpload() {
            FileObjectDto file1 = buildFileDto(FILE_NAME_1, FILE_SIZE_1, FILE_MD5_1);
            FileObjectDto file2 = buildFileDto(FILE_NAME_2, FILE_SIZE_2, FILE_MD5_2);
            UploadBatchCommand command = buildBatchCommand(List.of(file1, file2));

            UploadFileCommand cmd1 = buildFileCommand(file1);
            UploadFileCommand cmd2 = buildFileCommand(file2);
            when(converter.buildUploadFileCommand(command, file1)).thenReturn(cmd1);
            when(converter.buildUploadFileCommand(command, file2)).thenReturn(cmd2);
            doReturn(buildUploadResult(FILE_ID_1, FILE_NAME_1, FILE_SIZE_1, FILE_MD5_1)).when(service).upload(cmd1);
            doReturn(buildUploadResult(FILE_ID_2, FILE_NAME_2, FILE_SIZE_2, FILE_MD5_2)).when(service).upload(cmd2);

            List<UploadResult> results = service.uploadBatch(command);

            assertThat(results).extracting(UploadResult::getFileId)
                    .containsExactly(FILE_ID_1, FILE_ID_2);
        }

        @Test
        @DisplayName("每个结果包含正确的文件元数据")
        void should_containCorrectMetadata_when_success() {
            FileObjectDto file = buildFileDto(FILE_NAME_1, FILE_SIZE_1, FILE_MD5_1);
            UploadBatchCommand command = buildBatchCommand(List.of(file));
            UploadFileCommand fileCommand = buildFileCommand(file);

            when(converter.buildUploadFileCommand(command, file)).thenReturn(fileCommand);
            doReturn(buildUploadResult(FILE_ID_1, FILE_NAME_1, FILE_SIZE_1, FILE_MD5_1))
                    .when(service).upload(fileCommand);

            UploadResult result = service.uploadBatch(command).get(0);

            assertThat(result.getFileId()).isEqualTo(FILE_ID_1);
            assertThat(result.getFileName()).isEqualTo(FILE_NAME_1);
            assertThat(result.getFileSize()).isEqualTo(FILE_SIZE_1);
            assertThat(result.getFileMd5()).isEqualTo(FILE_MD5_1);
            assertThat(result.getUploadStatus()).isEqualTo(1);
            assertThat(result.getStorageType()).isEqualTo(STORAGE_TYPE);
        }

        @Test
        @DisplayName("批量上传返回的列表是可变的")
        void should_returnMutableList_when_batchUpload() {
            FileObjectDto file = buildFileDto(FILE_NAME_1, FILE_SIZE_1, FILE_MD5_1);
            UploadBatchCommand command = buildBatchCommand(List.of(file));
            UploadFileCommand fileCommand = buildFileCommand(file);

            when(converter.buildUploadFileCommand(command, file)).thenReturn(fileCommand);
            doReturn(buildUploadResult(FILE_ID_1, FILE_NAME_1, FILE_SIZE_1, FILE_MD5_1))
                    .when(service).upload(fileCommand);

            List<UploadResult> results = service.uploadBatch(command);
            int originalSize = results.size();
            results.add(buildUploadResult("EXTRA", "extra.bin", 100L, "extra-md5"));

            assertThat(results).hasSize(originalSize + 1);
        }
    }
}