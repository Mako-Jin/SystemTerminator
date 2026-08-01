package com.yaocode.sts.file.application.model.dto;

import com.yaocode.sts.file.application.model.command.UploadFileCommand;
import com.yaocode.sts.file.application.model.result.FileExistenceResult;
import com.yaocode.sts.file.application.model.result.UploadResult;
import com.yaocode.sts.file.core.enums.StorageTypeEnums;
import com.yaocode.sts.file.core.model.FileExistenceContext;
import com.yaocode.sts.file.core.model.FileUploadContext;
import lombok.Builder;
import lombok.Data;

import java.nio.file.Path;

/**
 * 文件上传上下文
 * <p>
 * 在整个上传流程的各个 Handler 之间传递状态，
 * 避免每个 Handler 都需要独立的入参和返回值。
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class FileUploadDto {

    // ========== 输入 ==========

    /** 原始上传命令 */
    private UploadFileCommand command;

    // ========== 准备阶段产出 ==========

    /** 文件唯一ID */
    private String fileId;

    /** 存储类型（由 StorageSelectionHandler 产出） */
    private StorageTypeEnums storageType;

    /** 临时文件路径 */
    private Path tempFile;

    /** 文件MD5 */
    private String fileMd5;

    /** 文件SHA256 */
    private String fileSha256;

    /** 实际文件大小（字节） */
    private long actualFileSize;

    // ========== 去重阶段产出 ==========

    /** 是否为重复文件 */
    private boolean duplicate;

    /** 已存在文件的检查结果 */
    private FileExistenceResult existenceResult;

    /** 上传上下文（用于策略执行） */
    private FileUploadContext uploadContext;

    /** 已存在文件上下文（用于策略执行） */
    private FileExistenceContext existenceContext;

    // ========== 执行阶段产出 ==========

    /** 是否已完成（策略短路时为 true） */
    private boolean completed;

    /** 文件存储路径 */
    private String filePath;

    /** 文件访问URL */
    private String fileUrl;

    // ========== 持久化阶段产出 ==========

    /** 最终返回结果 */
    private UploadResult result;

    // ========== 监控 ==========

    /** 开始时间戳 */
    private long startTime;

    /**
     * 获取处理耗时（毫秒）
     */
    public long getProcessingTime() {
        return System.currentTimeMillis() - startTime;
    }

}
