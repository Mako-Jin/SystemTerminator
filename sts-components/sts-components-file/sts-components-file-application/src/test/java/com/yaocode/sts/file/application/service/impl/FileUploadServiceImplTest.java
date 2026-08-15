package com.yaocode.sts.file.application.service.impl;

import com.yaocode.sts.common.tools.messages.MessageUtils;
import com.yaocode.sts.file.application.converter.FileUploadApplicationConverter;
import com.yaocode.sts.file.application.model.command.FastUploadCommand;
import com.yaocode.sts.file.application.model.command.UploadBatchCommand;
import com.yaocode.sts.file.application.model.command.UploadFileCommand;
import com.yaocode.sts.file.application.model.dto.FileObjectDto;
import com.yaocode.sts.file.application.model.query.FileExistenceQuery;
import com.yaocode.sts.file.application.model.result.FileExistenceResult;
import com.yaocode.sts.file.application.model.result.UploadResult;
import com.yaocode.sts.file.application.service.handler.*;
import com.yaocode.sts.file.core.constants.FileI18nKeyConstants;
import com.yaocode.sts.file.core.enums.FileErrorCodeEnums;
import com.yaocode.sts.file.core.enums.FileExtensionEnums;
import com.yaocode.sts.file.core.enums.FileTypeEnums;
import com.yaocode.sts.file.core.exception.FileUploadException;
import com.yaocode.sts.file.infrastructure.dao.FileBaseInfoDao;
import com.yaocode.sts.file.infrastructure.dao.FileDeduplicationDao;
import com.yaocode.sts.file.infrastructure.entity.FileBasicInfoEntity;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
    @Mock
    private MessageUtils messageUtils;

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

    private static final String FILE_SHA256_1 = "sha256-001";
    private static final String STORAGE_URL_1 = "https://oss.example.com/bucket/doc.pdf";
    private static final String FILE_PATH_1 = "/data/files/doc.pdf";
    private static final String STORAGE_BUCKET = "test-bucket";
    private static final String STORAGE_REGION = "cn-hangzhou";

    private FastUploadCommand buildFastUploadCommand() {
        return FastUploadCommand.builder()
                .fileName(FILE_NAME_1)
                .fileMd5(FILE_MD5_1)
                .fileSize(FILE_SIZE_1)
                .storageType(STORAGE_TYPE)
                .tags(TAGS)
                .description(DESCRIPTION)
                .isPublic(1)
                .tenantId(TENANT_ID)
                .userId(USER_ID)
                .build();
    }

    private FileBasicInfoEntity buildOriginalEntity() {
        FileBasicInfoEntity entity = new FileBasicInfoEntity();
        entity.setFileId(FILE_ID_1);
        entity.setFileName(FILE_NAME_1);
        entity.setFileSize(FILE_SIZE_1);
        entity.setFileMd5(FILE_MD5_1);
        entity.setFileSha256(FILE_SHA256_1);
        entity.setStorageType(STORAGE_TYPE);
        entity.setStorageBucket(STORAGE_BUCKET);
        entity.setStorageRegion(STORAGE_REGION);
        entity.setStorageUrl(STORAGE_URL_1);
        entity.setFilePath(FILE_PATH_1);
        return entity;
    }

    private FileExistenceResult buildExistenceResult() {
        return FileExistenceResult.builder()
                .exists(true)
                .fileId(FILE_ID_1)
                .fileName(FILE_NAME_1)
                .fileSize(FILE_SIZE_1)
                .fileMd5(FILE_MD5_1)
                .fileSha256(FILE_SHA256_1)
                .fileUrl(STORAGE_URL_1)
                .storageType(STORAGE_TYPE)
                .tenantId(TENANT_ID)
                .build();
    }

    /**
     * 配置 messageUtils.resolveExceptionMessage 返回异常消息
     * 供批量上传异常测试使用
     */
    private void setupResolveExceptionMessage() {
        when(messageUtils.resolveExceptionMessage(any(Exception.class)))
                .thenAnswer(invocation -> {
                    Exception e = invocation.getArgument(0);
                    return e.getMessage();
                });
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
            setupResolveExceptionMessage();
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
            setupResolveExceptionMessage();
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
            setupResolveExceptionMessage();
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

    // ==================== 5. fastUpload —— 成功场景 ====================

    @Nested
    @DisplayName("fastUpload —— 秒传成功（生成引用记录）")
    class FastUploadSuccessTests {

        @Test
        @DisplayName("文件已存在 —— 创建引用记录，返回秒传结果")
        void should_createReferenceRecord_when_fileExists() {
            FastUploadCommand command = buildFastUploadCommand();
            FileExistenceResult existenceResult = buildExistenceResult();
            FileBasicInfoEntity originalEntity = buildOriginalEntity();

            when(deduplicationHandler.checkFileExists(FILE_MD5_1, FILE_SIZE_1, STORAGE_TYPE, TENANT_ID))
                    .thenReturn(existenceResult);
            when(fileBaseInfoDao.selectByFileIdAndTenant(FILE_ID_1, TENANT_ID))
                    .thenReturn(originalEntity);
            when(messageUtils.getMessage(FileI18nKeyConstants.STRATEGY_REUSE_SUCCESS))
                    .thenReturn("秒传成功，已复用已有文件");

            UploadResult result = service.fastUpload(command);

            assertThat(result).isNotNull();
            assertThat(result.getFileId()).isNotNull().isNotEqualTo(FILE_ID_1);
            assertThat(result.getFileName()).isEqualTo(FILE_NAME_1);
            assertThat(result.getFileSize()).isEqualTo(FILE_SIZE_1);
            assertThat(result.getFileMd5()).isEqualTo(FILE_MD5_1);
            assertThat(result.getFileSha256()).isEqualTo(FILE_SHA256_1);
            assertThat(result.getFileUrl()).isEqualTo(STORAGE_URL_1);
            assertThat(result.getStorageType()).isEqualTo(STORAGE_TYPE);
            assertThat(result.getTenantId()).isEqualTo(TENANT_ID);
            assertThat(result.getUploadStatus()).isEqualTo(1);
            assertThat(result.getUploadStatusDesc()).isEqualTo("已完成");
            assertThat(result.getIsDuplicate()).isTrue();
            assertThat(result.getDuplicateFileId()).isEqualTo(FILE_ID_1);
            assertThat(result.getSourceFileId()).isEqualTo(FILE_ID_1);
            assertThat(result.getMessage()).isEqualTo("秒传成功，已复用已有文件");
            assertThat(result.getProcessingTime()).isGreaterThanOrEqualTo(0);

            verify(fileBaseInfoDao).save(argThat(ref -> {
                // 验证引用实体的存储信息完全复用原文件
                return ref.getFileId() != null
                        && !ref.getFileId().isEmpty()
                        && ref.getFilePath().equals(FILE_PATH_1)
                        && ref.getFileSize().equals(FILE_SIZE_1)
                        && ref.getFileMd5().equals(FILE_MD5_1)
                        && ref.getFileSha256().equals(FILE_SHA256_1)
                        && ref.getStorageType().equals(STORAGE_TYPE)
                        && ref.getStorageUrl().equals(STORAGE_URL_1)
                        && ref.getFileName().equals(FILE_NAME_1)
                        && ref.getTags().equals(TAGS)
                        && ref.getDescription().equals(DESCRIPTION)
                        && ref.getUploadStatus() == 1
                        && ref.getUploadProgress() == 100;
            }));
        }

        @Test
        @DisplayName("秒传 —— 原实体的存储信息被完整复用")
        void should_reuseAllStorageFields_when_fileExists() {
            FastUploadCommand command = buildFastUploadCommand();
            FileExistenceResult existenceResult = buildExistenceResult();
            FileBasicInfoEntity originalEntity = buildOriginalEntity();
            originalEntity.setStorageMetadata("{\"key\":\"value\"}");
            originalEntity.setFileType(FileTypeEnums.PDF.getCode());
            originalEntity.setFileExtension(FileExtensionEnums.PDF.getCode());

            when(deduplicationHandler.checkFileExists(anyString(), any(), any(), anyString()))
                    .thenReturn(existenceResult);
            when(fileBaseInfoDao.selectByFileIdAndTenant(anyString(), anyString()))
                    .thenReturn(originalEntity);
            when(messageUtils.getMessage(anyString())).thenReturn("秒传成功");

            service.fastUpload(command);

            verify(fileBaseInfoDao).save(argThat(ref ->
                    ref.getStorageMetadata().equals("{\"key\":\"value\"}")
                    && ref.getFileType().equals(FileTypeEnums.PDF.getCode())
                    && ref.getFileExtension().equals(FileExtensionEnums.PDF.getCode())
                    && ref.getStorageBucket().equals(STORAGE_BUCKET)
                    && ref.getStorageRegion().equals(STORAGE_REGION)));
        }

        @Test
        @DisplayName("秒传 —— 业务字段使用命令中的值")
        void should_useCommandFields_when_creatingReference() {
            FastUploadCommand command = FastUploadCommand.builder()
                    .fileName("custom-name.pdf")
                    .fileMd5(FILE_MD5_1)
                    .fileSize(FILE_SIZE_1)
                    .storageType(STORAGE_TYPE)
                    .tags("tag1,tag2")
                    .description("自定义描述")
                    .isPublic(0)
                    .tenantId(TENANT_ID)
                    .userId(USER_ID)
                    .build();

            when(deduplicationHandler.checkFileExists(anyString(), any(), any(), anyString()))
                    .thenReturn(buildExistenceResult());
            when(fileBaseInfoDao.selectByFileIdAndTenant(anyString(), anyString()))
                    .thenReturn(buildOriginalEntity());
            when(messageUtils.getMessage(anyString())).thenReturn("秒传成功");

            service.fastUpload(command);

            verify(fileBaseInfoDao).save(argThat(ref ->
                    ref.getFileName().equals("custom-name.pdf")
                    && ref.getTags().equals("tag1,tag2")
                    && ref.getDescription().equals("自定义描述")
                    && ref.getIsPublic() == 0));
        }
    }

    // ==================== 6. fastUpload —— 异常场景 ====================

    @Nested
    @DisplayName("fastUpload —— 异常场景")
    class FastUploadErrorTests {

        @Test
        @DisplayName("文件不存在 —— 抛出 FILE_NOT_FOUND 异常")
        void should_throwFileNotFound_when_fileNotExists() {
            FastUploadCommand command = buildFastUploadCommand();
            FileExistenceResult notFoundResult = FileExistenceResult.builder()
                    .exists(false)
                    .fileId(null)
                    .build();

            when(deduplicationHandler.checkFileExists(FILE_MD5_1, FILE_SIZE_1, STORAGE_TYPE, TENANT_ID))
                    .thenReturn(notFoundResult);

            assertThatThrownBy(() -> service.fastUpload(command))
                    .isInstanceOf(FileUploadException.class)
                    .satisfies(ex -> {
                        FileUploadException fie = (FileUploadException) ex;
                        assertThat(fie.getCode()).isEqualTo(FileErrorCodeEnums.FILE_NOT_FOUND.getCode());
                    });

            verify(fileBaseInfoDao, never()).save(any());
        }

        @Test
        @DisplayName("查重返回 null —— 抛出 FILE_NOT_FOUND 异常")
        void should_throwFileNotFound_when_deduplicationReturnsNull() {
            FastUploadCommand command = buildFastUploadCommand();

            when(deduplicationHandler.checkFileExists(FILE_MD5_1, FILE_SIZE_1, STORAGE_TYPE, TENANT_ID))
                    .thenReturn(null);

            assertThatThrownBy(() -> service.fastUpload(command))
                    .isInstanceOf(FileUploadException.class);

            verify(fileBaseInfoDao, never()).save(any());
        }

        @Test
        @DisplayName("查重通过但原文件实体不存在 —— 抛出 FILE_NOT_FOUND")
        void should_throwFileNotFound_when_originalEntityMissing() {
            FastUploadCommand command = buildFastUploadCommand();

            when(deduplicationHandler.checkFileExists(FILE_MD5_1, FILE_SIZE_1, STORAGE_TYPE, TENANT_ID))
                    .thenReturn(buildExistenceResult());
            when(fileBaseInfoDao.selectByFileIdAndTenant(FILE_ID_1, TENANT_ID))
                    .thenReturn(null);

            assertThatThrownBy(() -> service.fastUpload(command))
                    .isInstanceOf(FileUploadException.class)
                    .satisfies(ex -> {
                        FileUploadException fie = (FileUploadException) ex;
                        assertThat(fie.getCode()).isEqualTo(FileErrorCodeEnums.FILE_NOT_FOUND.getCode());
                    });

            verify(fileBaseInfoDao, never()).save(any());
        }

        @Test
        @DisplayName("秒传 —— 使用国际化消息")
        void should_useI18nMessage_when_success() {
            FastUploadCommand command = buildFastUploadCommand();

            when(deduplicationHandler.checkFileExists(anyString(), any(), any(), anyString()))
                    .thenReturn(buildExistenceResult());
            when(fileBaseInfoDao.selectByFileIdAndTenant(anyString(), anyString()))
                    .thenReturn(buildOriginalEntity());
            when(messageUtils.getMessage(FileI18nKeyConstants.STRATEGY_REUSE_SUCCESS))
                    .thenReturn("Instant upload successful");

            UploadResult result = service.fastUpload(command);

            assertThat(result.getMessage()).isEqualTo("Instant upload successful");
            verify(messageUtils).getMessage(FileI18nKeyConstants.STRATEGY_REUSE_SUCCESS);
        }
    }

    // ==================== 7. checkFileExists —— 文件存在性检测 ====================
    @Nested
    @DisplayName("checkFileExists —— 文件存在性检测")
    class CheckFileExistsTests {
        @Test
        @DisplayName("文件已存在 —— 返回 exists=true 及完整信息")
        void should_returnExistsTrue_when_fileFound() {
            FileExistenceQuery query = FileExistenceQuery.builder()
                    .fileMd5(FILE_MD5_1)
                    .fileSize(FILE_SIZE_1)
                    .storageType(STORAGE_TYPE)
                    .tenantId(TENANT_ID)
                    .build();
            FileExistenceResult expectedResult = buildExistenceResult();
            when(deduplicationHandler.checkFileExists(FILE_MD5_1, FILE_SIZE_1, STORAGE_TYPE, TENANT_ID))
                    .thenReturn(expectedResult);
            FileExistenceResult result = service.checkFileExists(query);
            assertThat(result).isNotNull();
            assertThat(result.getExists()).isTrue();
            assertThat(result.getFileId()).isEqualTo(FILE_ID_1);
            assertThat(result.getFileName()).isEqualTo(FILE_NAME_1);
            assertThat(result.getFileSize()).isEqualTo(FILE_SIZE_1);
            assertThat(result.getFileMd5()).isEqualTo(FILE_MD5_1);
            assertThat(result.getFileUrl()).isEqualTo(STORAGE_URL_1);
            assertThat(result.getStorageType()).isEqualTo(STORAGE_TYPE);
            assertThat(result.getTenantId()).isEqualTo(TENANT_ID);
        }
        @Test
        @DisplayName("文件不存在 —— 返回 exists=false")
        void should_returnExistsFalse_when_fileNotFound() {
            FileExistenceQuery query = FileExistenceQuery.builder()
                    .fileMd5("md5-not-exist")
                    .fileSize(9999L)
                    .storageType(STORAGE_TYPE)
                    .tenantId(TENANT_ID)
                    .build();
            FileExistenceResult notFoundResult = FileExistenceResult.builder()
                    .exists(false)
                    .fileId(null)
                    .fileSize(9999L)
                    .fileMd5("md5-not-exist")
                    .storageType(STORAGE_TYPE)
                    .tenantId(TENANT_ID)
                    .build();
            when(deduplicationHandler.checkFileExists("md5-not-exist", 9999L, STORAGE_TYPE, TENANT_ID))
                    .thenReturn(notFoundResult);
            FileExistenceResult result = service.checkFileExists(query);
            assertThat(result).isNotNull();
            assertThat(result.getExists()).isFalse();
            assertThat(result.getFileId()).isNull();
            assertThat(result.getFileMd5()).isEqualTo("md5-not-exist");
            assertThat(result.getFileSize()).isEqualTo(9999L);
        }
        @Test
        @DisplayName("验证查询参数正确传递给 deduplicationHandler")
        void should_passCorrectParams_when_checkingExistence() {
            FileExistenceQuery query = FileExistenceQuery.builder()
                    .fileMd5(FILE_MD5_1)
                    .fileSize(FILE_SIZE_1)
                    .storageType(STORAGE_TYPE)
                    .tenantId(TENANT_ID)
                    .build();
            when(deduplicationHandler.checkFileExists(anyString(), any(), any(), anyString()))
                    .thenReturn(buildExistenceResult());
            service.checkFileExists(query);
            verify(deduplicationHandler).checkFileExists(
                    argThat(md5 -> md5.equals(FILE_MD5_1)),
                    argThat(size -> size.equals(FILE_SIZE_1)),
                    argThat(st -> st.equals(STORAGE_TYPE)),
                    argThat(tenant -> tenant.equals(TENANT_ID))
            );
        }
        @Test
        @DisplayName("storageType 为 null 时正确传递")
        void should_passNullStorageType_when_checkingExistence() {
            FileExistenceQuery query = FileExistenceQuery.builder()
                    .fileMd5(FILE_MD5_1)
                    .fileSize(FILE_SIZE_1)
                    .storageType(null)
                    .tenantId(TENANT_ID)
                    .build();
            when(deduplicationHandler.checkFileExists(FILE_MD5_1, FILE_SIZE_1, null, TENANT_ID))
                    .thenReturn(buildExistenceResult());
            FileExistenceResult result = service.checkFileExists(query);
            assertThat(result).isNotNull();
            assertThat(result.getExists()).isTrue();
            verify(deduplicationHandler).checkFileExists(FILE_MD5_1, FILE_SIZE_1, null, TENANT_ID);
        }
    }
}