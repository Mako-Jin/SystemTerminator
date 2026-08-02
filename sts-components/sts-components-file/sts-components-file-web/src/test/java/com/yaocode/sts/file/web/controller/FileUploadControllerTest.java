package com.yaocode.sts.file.web.controller;

import com.yaocode.sts.common.web.model.ResultModel;
import com.yaocode.sts.file.application.model.command.UploadFileCommand;
import com.yaocode.sts.file.application.model.query.FileExistenceQuery;
import com.yaocode.sts.file.application.model.result.FileExistenceResult;
import com.yaocode.sts.file.application.model.result.UploadResult;
import com.yaocode.sts.file.application.service.FileUploadService;
import com.yaocode.sts.file.interfaces.model.response.FileExistenceResponse;
import com.yaocode.sts.file.interfaces.model.response.UploadResponse;
import com.yaocode.sts.file.web.converter.FileUploadConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * FileUploadController 单元测试
 *
 * @author yaocode
 * @since 1.0.0
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("文件上传控制器测试")
class FileUploadControllerTest {

    @Mock
    private FileUploadService fileUploadService;

    @Mock
    private FileUploadConverter converter;

    @InjectMocks
    private FileUploadController controller;

    // ==================== 通用 Mock 数据 ====================

    private static final String FILE_NAME = "test.pdf";
    private static final byte[] FILE_CONTENT = "Hello World".getBytes();
    private static final Long FILE_SIZE = (long) FILE_CONTENT.length;
    private static final String FILE_MD5 = "d41d8cd98f00b204e9800998ecf8427e";
    private static final String FILE_ID = "FILE-001";
    private static final String BUCKET = "Bucket";
    private static final Integer STORAGE_TYPE = 1;

    private MockMultipartFile buildFile() {
        return new MockMultipartFile("file", FILE_NAME, "application/pdf", FILE_CONTENT);
    }

    private UploadResult buildUploadResult() {
        return UploadResult.builder()
                .fileId(FILE_ID)
                .fileName(FILE_NAME)
                .fileSize(FILE_SIZE)
                .fileMd5(FILE_MD5)
                .fileUrl("https://cdn.example.com/" + FILE_NAME)
                .storageType(STORAGE_TYPE)
                .uploadStatus(1)
                .uploadStatusDesc("上传成功")
                .isDuplicate(false)
                .processingTime(120L)
                .message("ok")
                .build();
    }

    private UploadResponse buildUploadResponse() {
        return UploadResponse.builder()
                .fileId(FILE_ID)
                .fileName(FILE_NAME)
                .fileSize(FILE_SIZE)
                .fileMd5(FILE_MD5)
                .fileUrl("https://cdn.example.com/" + FILE_NAME)
                .storageType(STORAGE_TYPE)
                .uploadStatus(1)
                .uploadStatusDesc("上传成功")
                .isDuplicate(false)
                .processingTime(120L)
                .message("ok")
                .build();
    }

    // ==================== 1. 普通上传接口 ====================

    @Nested
    @DisplayName("POST /files/v1/upload/single —— 普通单文件上传")
    class UploadFileTests {

        @Test
        @DisplayName("正常上传 —— 最小参数（仅文件）")
        void should_uploadFile_when_onlyFileProvided() {
            MockMultipartFile file = buildFile();
            when(converter.toUploadFileCommand(any(), any(), any(), any(), any(), any(), any(), any()))
                    .thenReturn(UploadFileCommand.builder().build());
            when(fileUploadService.upload(any(UploadFileCommand.class))).thenReturn(buildUploadResult());
            when(converter.toUploadResponse(any(UploadResult.class))).thenReturn(buildUploadResponse());

            ResultModel<UploadResponse> result = controller.uploadFile(
                    file, null, null, null, null, null, null, null
            );

            assertThat(result).isNotNull();
            assertThat(result.getCode()).isNotNull();
            assertThat(result.getData()).isNotNull();
            assertThat(result.getData().getFileId()).isEqualTo(FILE_ID);
            assertThat(result.getData().getFileName()).isEqualTo(FILE_NAME);
            verify(fileUploadService).upload(any(UploadFileCommand.class));
        }

        @Test
        @DisplayName("正常上传 —— 传递全部参数")
        void should_uploadFile_when_allParametersProvided() {
            MockMultipartFile file = buildFile();
            Map<String, String> metadata = new HashMap<>();
            metadata.put("creator", "yaocode");
            metadata.put("version", "v1");

            when(converter.toUploadFileCommand(any(), eq(STORAGE_TYPE), eq(BUCKET),
                    eq(1), eq("tag1,tag2"), eq("desc"), eq(1), eq(metadata)))
                    .thenReturn(UploadFileCommand.builder().build());
            when(fileUploadService.upload(any(UploadFileCommand.class))).thenReturn(buildUploadResult());
            when(converter.toUploadResponse(any(UploadResult.class))).thenReturn(buildUploadResponse());

            ResultModel<UploadResponse> result = controller.uploadFile(
                    file, STORAGE_TYPE, BUCKET, 1, "tag1,tag2", "desc", 1, metadata
            );

            assertThat(result.getData().getFileId()).isEqualTo(FILE_ID);
            verify(fileUploadService).upload(any(UploadFileCommand.class));
        }

        @Test
        @DisplayName("上传空文件 —— 应抛出异常或被拒绝")
        void should_reject_upload_when_fileIsEmpty() {
            MockMultipartFile emptyFile = new MockMultipartFile(
                    "file", "empty.txt", "text/plain", new byte[0]);

            when(converter.toUploadFileCommand(any(), any(), any(), any(), any(), any(), any(), any()))
                    .thenReturn(UploadFileCommand.builder().file(null).build());
            when(fileUploadService.upload(any(UploadFileCommand.class)))
                    .thenThrow(new IllegalArgumentException("文件不能为空"));

            assertThatThrownBy(() -> controller.uploadFile(
                    emptyFile, null, null, null, null, null, null, null
            )).isInstanceOf(IllegalArgumentException.class).hasMessage("文件不能为空");
        }

        @Test
        @DisplayName("上传大文件 —— 100MB 边界")
        void should_uploadFile_when_largeFile() {
            byte[] largeContent = new byte[100 * 1024 * 1024]; // 100MB
            MockMultipartFile file = new MockMultipartFile(
                    "file", "large.bin", "application/octet-stream", largeContent);

            when(converter.toUploadFileCommand(any(), any(), any(), any(), any(), any(), any(), any()))
                    .thenReturn(UploadFileCommand.builder().build());
            when(fileUploadService.upload(any(UploadFileCommand.class))).thenReturn(buildUploadResult());
            when(converter.toUploadResponse(any(UploadResult.class))).thenReturn(buildUploadResponse());

            ResultModel<UploadResponse> result = controller.uploadFile(
                    file, null, null, null, null, null, null, null
            );

            assertThat(result.getData()).isNotNull();
        }

        @Test
        @DisplayName("上传特殊字符文件名 —— 中文、空格、表情")
        void should_uploadFile_when_fileNameHasSpecialChars() {
            MockMultipartFile file = new MockMultipartFile(
                    "file", "文件 (1).pdf", "application/pdf", FILE_CONTENT);

            when(converter.toUploadFileCommand(any(), any(), any(), any(), any(), any(), any(), any()))
                    .thenReturn(UploadFileCommand.builder().build());
            when(fileUploadService.upload(any(UploadFileCommand.class))).thenReturn(buildUploadResult());
            when(converter.toUploadResponse(any(UploadResult.class))).thenReturn(buildUploadResponse());

            ResultModel<UploadResponse> result = controller.uploadFile(
                    file, null, null, null, null, null, null, null
            );

            assertThat(result.getData()).isNotNull();
        }

        @Test
        @DisplayName("上传时 Service 抛出异常 —— 应向上传播")
        void should_propagateException_when_serviceFails() {
            MockMultipartFile file = buildFile();
            when(converter.toUploadFileCommand(any(), any(), any(), any(), any(), any(), any(), any()))
                    .thenReturn(UploadFileCommand.builder().build());
            when(fileUploadService.upload(any(UploadFileCommand.class)))
                    .thenThrow(new RuntimeException("存储服务不可用"));

            assertThatThrownBy(() -> controller.uploadFile(
                    file, null, null, null, null, null, null, null
            )).isInstanceOf(RuntimeException.class).hasMessage("存储服务不可用");
        }

        @Test
        @DisplayName("上传时 Converter 返回 null —— 仍能正常处理")
        void should_handleNullResponseConverter_when_converterReturnsNull() {
            MockMultipartFile file = buildFile();
            when(converter.toUploadFileCommand(any(), any(), any(), any(), any(), any(), any(), any()))
                    .thenReturn(UploadFileCommand.builder().build());
            when(fileUploadService.upload(any(UploadFileCommand.class))).thenReturn(buildUploadResult());
            when(converter.toUploadResponse(any(UploadResult.class))).thenReturn(null);

            ResultModel<UploadResponse> result = controller.uploadFile(
                    file, null, null, null, null, null, null, null
            );

            assertThat(result.getData()).isNull();
        }

        @Test
        @DisplayName("上传 —— isPublic=true 的场景")
        void should_uploadFile_when_publicAccess() {
            MockMultipartFile file = buildFile();
            when(converter.toUploadFileCommand(any(), any(), any(), any(), any(), any(), eq(1), any()))
                    .thenReturn(UploadFileCommand.builder().build());
            when(fileUploadService.upload(any(UploadFileCommand.class))).thenReturn(buildUploadResult());
            when(converter.toUploadResponse(any(UploadResult.class))).thenReturn(buildUploadResponse());

            ResultModel<UploadResponse> result = controller.uploadFile(
                    file, null, null, null, null, null, 1, null
            );

            assertThat(result.getData()).isNotNull();
        }

        @Test
        @DisplayName("上传 —— 启用去重 enableDeduplication=true")
        void should_callServiceWithDeduplication_when_enabled() {
            MockMultipartFile file = buildFile();
            when(converter.toUploadFileCommand(any(), any(), any(), eq(1), any(), any(), any(), any()))
                    .thenReturn(UploadFileCommand.builder().build());
            when(fileUploadService.upload(any(UploadFileCommand.class))).thenReturn(buildUploadResult());
            when(converter.toUploadResponse(any(UploadResult.class))).thenReturn(buildUploadResponse());

            controller.uploadFile(file, null, null, null, null, null, 1, null);

            verify(fileUploadService).upload(any(UploadFileCommand.class));
        }

        @Test
        @DisplayName("上传 —— metadata 包含多个键值对")
        void should_uploadFile_when_complexMetadata() {
            MockMultipartFile file = buildFile();
            Map<String, String> metadata = new HashMap<>();
            for (int i = 0; i < 20; i++) {
                metadata.put("key-" + i, "value-" + i);
            }

            when(converter.toUploadFileCommand(any(), any(), any(), any(), any(), any(), any(), any()))
                    .thenReturn(UploadFileCommand.builder().build());
            when(fileUploadService.upload(any(UploadFileCommand.class))).thenReturn(buildUploadResult());
            when(converter.toUploadResponse(any(UploadResult.class))).thenReturn(buildUploadResponse());

            ResultModel<UploadResponse> result = controller.uploadFile(
                    file, null, null, null, null, null, null, metadata
            );

            assertThat(result.getData()).isNotNull();
        }
    }

    // ==================== 2. 秒传接口（检查文件是否存在）====================

    @Nested
    @DisplayName("POST /files/v1/check —— 秒传检查")
    class CheckFileExistsTests {

        @Test
        @DisplayName("正常检查 —— 文件已存在")
        void should_returnExistsTrue_when_fileExists() {
            FileExistenceResult result = FileExistenceResult.builder()
                    .exists(true)
                    .fileId(FILE_ID)
                    .fileName(FILE_NAME)
                    .fileSize(FILE_SIZE)
                    .fileMd5(FILE_MD5)
                    .fileUrl("https://cdn.example.com/" + FILE_NAME)
                    .storageType(STORAGE_TYPE)
                    .isDuplicate(false)
                    .build();

            FileExistenceResponse response = FileExistenceResponse.builder()
                    .exists(true)
                    .fileId(FILE_ID)
                    .fileName(FILE_NAME)
                    .fileSize(FILE_SIZE)
                    .fileMd5(FILE_MD5)
                    .isDuplicate(false)
                    .build();

            when(converter.toFileExistenceQuery(eq(FILE_MD5), eq(FILE_SIZE), eq(STORAGE_TYPE)))
                    .thenReturn(FileExistenceQuery.builder().fileMd5(FILE_MD5).fileSize(FILE_SIZE).build());
            when(fileUploadService.checkFileExists(any(FileExistenceQuery.class))).thenReturn(result);
            when(converter.toFileExistenceResponse(any(FileExistenceResult.class))).thenReturn(response);

            ResultModel<FileExistenceResponse> r = controller.checkFileExists(FILE_MD5, FILE_SIZE, STORAGE_TYPE);

            assertThat(r.getData()).isNotNull();
            assertThat(r.getData().getExists()).isTrue();
            assertThat(r.getData().getFileId()).isEqualTo(FILE_ID);
        }

        @Test
        @DisplayName("正常检查 —— 文件不存在")
        void should_returnExistsFalse_when_fileNotExists() {
            FileExistenceResult result = FileExistenceResult.builder()
                    .exists(false)
                    .build();
            FileExistenceResponse response = FileExistenceResponse.builder()
                    .exists(false)
                    .build();

            when(converter.toFileExistenceQuery(eq(FILE_MD5), eq(FILE_SIZE), isNull()))
                    .thenReturn(FileExistenceQuery.builder().fileMd5(FILE_MD5).fileSize(FILE_SIZE).build());
            when(fileUploadService.checkFileExists(any(FileExistenceQuery.class))).thenReturn(result);
            when(converter.toFileExistenceResponse(any(FileExistenceResult.class))).thenReturn(response);

            ResultModel<FileExistenceResponse> r = controller.checkFileExists(FILE_MD5, FILE_SIZE, null);

            assertThat(r.getData().getExists()).isFalse();
        }

        @Test
        @DisplayName("边界 —— 文件大小为 0")
        void should_handleZeroSize_when_fileSizeIsZero() {
            FileExistenceResponse response = FileExistenceResponse.builder().exists(false).build();
            when(converter.toFileExistenceQuery(eq(FILE_MD5), eq(0L), any()))
                    .thenReturn(FileExistenceQuery.builder().build());
            when(fileUploadService.checkFileExists(any(FileExistenceQuery.class)))
                    .thenReturn(FileExistenceResult.builder().exists(false).build());
            when(converter.toFileExistenceResponse(any(FileExistenceResult.class))).thenReturn(response);

            ResultModel<FileExistenceResponse> r = controller.checkFileExists(FILE_MD5, 0L, STORAGE_TYPE);

            assertThat(r.getData()).isNotNull();
        }

        @Test
        @DisplayName("边界 —— 超大文件大小")
        void should_handleLargeSize_when_fileSizeIsVeryLarge() {
            long veryLarge = 1024L * 1024L * 1024L * 1024L; // 1TB
            FileExistenceResponse response = FileExistenceResponse.builder().exists(false).build();
            when(converter.toFileExistenceQuery(eq(FILE_MD5), eq(veryLarge), any()))
                    .thenReturn(FileExistenceQuery.builder().build());
            when(fileUploadService.checkFileExists(any(FileExistenceQuery.class)))
                    .thenReturn(FileExistenceResult.builder().exists(false).build());
            when(converter.toFileExistenceResponse(any(FileExistenceResult.class))).thenReturn(response);

            ResultModel<FileExistenceResponse> r = controller.checkFileExists(FILE_MD5, veryLarge, STORAGE_TYPE);

            assertThat(r.getData()).isNotNull();
        }

        @Test
        @DisplayName("边界 —— 存储类型为 null")
        void should_handleNullStorageType_when_storageTypeIsNotProvided() {
            FileExistenceResponse response = FileExistenceResponse.builder().exists(false).build();
            when(converter.toFileExistenceQuery(any(), any(), isNull()))
                    .thenReturn(FileExistenceQuery.builder().build());
            when(fileUploadService.checkFileExists(any(FileExistenceQuery.class)))
                    .thenReturn(FileExistenceResult.builder().exists(false).build());
            when(converter.toFileExistenceResponse(any(FileExistenceResult.class))).thenReturn(response);

            ResultModel<FileExistenceResponse> r = controller.checkFileExists(FILE_MD5, FILE_SIZE, null);

            assertThat(r.getData()).isNotNull();
        }

        @Test
        @DisplayName("异常 —— Service 抛出异常")
        void should_propagateException_when_checkFails() {
            when(converter.toFileExistenceQuery(any(), any(), any()))
                    .thenReturn(FileExistenceQuery.builder().build());
            when(fileUploadService.checkFileExists(any(FileExistenceQuery.class)))
                    .thenThrow(new RuntimeException("数据库异常"));

            assertThatThrownBy(() -> controller.checkFileExists(FILE_MD5, FILE_SIZE, STORAGE_TYPE))
                    .isInstanceOf(RuntimeException.class).hasMessage("数据库异常");
        }

        @Test
        @DisplayName("场景 —— 重复文件存在时返回 duplicateFiles 信息")
        void should_returnDuplicateInfo_when_fileIsDuplicate() {
            FileExistenceResult result = FileExistenceResult.builder()
                    .exists(true)
                    .fileId(FILE_ID)
                    .isDuplicate(true)
                    .build();
            FileExistenceResponse response = FileExistenceResponse.builder()
                    .exists(true)
                    .fileId(FILE_ID)
                    .isDuplicate(true)
                    .build();

            when(converter.toFileExistenceQuery(any(), any(), any()))
                    .thenReturn(FileExistenceQuery.builder().build());
            when(fileUploadService.checkFileExists(any(FileExistenceQuery.class))).thenReturn(result);
            when(converter.toFileExistenceResponse(any(FileExistenceResult.class))).thenReturn(response);

            ResultModel<FileExistenceResponse> r = controller.checkFileExists(FILE_MD5, FILE_SIZE, STORAGE_TYPE);

            assertThat(r.getData().getExists()).isTrue();
            assertThat(r.getData().getIsDuplicate()).isTrue();
        }

        @Test
        @DisplayName("参数校验 —— MD5 为空字符串应抛异常")
        void should_throwException_when_md5IsBlank() {
            // 注意：@NotBlank 校验通常由 Spring Validation 在调用前处理
            // 控制器方法体内没有显式校验，因此空字符串会走到 service
            when(converter.toFileExistenceQuery(eq(""), any(), any()))
                    .thenReturn(FileExistenceQuery.builder().fileMd5("").build());
            when(fileUploadService.checkFileExists(any(FileExistenceQuery.class)))
                    .thenThrow(new IllegalArgumentException("文件MD5不能为空"));

            assertThatThrownBy(() -> controller.checkFileExists("", FILE_SIZE, STORAGE_TYPE))
                    .isInstanceOf(IllegalArgumentException.class).hasMessage("文件MD5不能为空");
        }
    }

    // ==================== 3. Controller 依赖交互测试 ====================

    @Nested
    @DisplayName("依赖交互与调用顺序测试")
    class InteractionTests {

        @Test
        @DisplayName("上传流程 —— 应按 converter → service → converter 顺序调用")
        void should_callDependenciesInOrder_when_uploadFile() {
            MockMultipartFile file = buildFile();
            when(converter.toUploadFileCommand(any(), any(), any(), any(), any(), any(), any(), any()))
                    .thenReturn(UploadFileCommand.builder().build());
            when(fileUploadService.upload(any(UploadFileCommand.class))).thenReturn(buildUploadResult());
            when(converter.toUploadResponse(any(UploadResult.class))).thenReturn(buildUploadResponse());

            controller.uploadFile(file, null, null, null, null, null, null, null);

            var order = inOrder(converter, fileUploadService);
            order.verify(converter).toUploadFileCommand(any(), any(), any(), any(), any(), any(), any(), any());
            order.verify(fileUploadService).upload(any(UploadFileCommand.class));
            order.verify(converter).toUploadResponse(any(UploadResult.class));
        }

        @Test
        @DisplayName("检查流程 —— 应按 converter → service → converter 顺序调用")
        void should_callDependenciesInOrder_when_checkFileExists() {
            when(converter.toFileExistenceQuery(any(), any(), any()))
                    .thenReturn(FileExistenceQuery.builder().build());
            when(fileUploadService.checkFileExists(any(FileExistenceQuery.class)))
                    .thenReturn(FileExistenceResult.builder().exists(false).build());
            when(converter.toFileExistenceResponse(any(FileExistenceResult.class)))
                    .thenReturn(FileExistenceResponse.builder().exists(false).build());

            controller.checkFileExists(FILE_MD5, FILE_SIZE, STORAGE_TYPE);

            var order = inOrder(converter, fileUploadService);
            order.verify(converter).toFileExistenceQuery(any(), any(), any());
            order.verify(fileUploadService).checkFileExists(any(FileExistenceQuery.class));
            order.verify(converter).toFileExistenceResponse(any(FileExistenceResult.class));
        }

        @Test
        @DisplayName("多次上传 —— 每次应独立调用 service")
        void should_callServiceMultipleTimes_when_uploadInvokedMultipleTimes() {
            MockMultipartFile file = buildFile();
            when(converter.toUploadFileCommand(any(), any(), any(), any(), any(), any(), any(), any()))
                    .thenReturn(UploadFileCommand.builder().build());
            when(fileUploadService.upload(any(UploadFileCommand.class))).thenReturn(buildUploadResult());
            when(converter.toUploadResponse(any(UploadResult.class))).thenReturn(buildUploadResponse());

            controller.uploadFile(file, null, null, null, null, null, null, null);
            controller.uploadFile(file, null, null, null, null, null, null, null);
            controller.uploadFile(file, null, null, null, null, null, null, null);

            verify(fileUploadService, times(3)).upload(any(UploadFileCommand.class));
        }
    }
}
