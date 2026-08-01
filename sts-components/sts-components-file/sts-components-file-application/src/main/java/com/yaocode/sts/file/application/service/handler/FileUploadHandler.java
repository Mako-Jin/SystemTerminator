package com.yaocode.sts.file.application.service.handler;

import com.yaocode.sts.file.application.model.dto.FileUploadDto;

/**
 * 文件上传处理器接口
 * <p>
 * 采用责任链模式，每个 Handler 负责上传流程中的一个阶段。
 * 通过 {@link FileUploadDto} 在各 Handler 之间传递状态。
 * </p>
 *
 * <p>Handler 执行顺序：</p>
 * <ol>
 *   <li>{@link FileUploadValidationHandler}    - 参数校验</li>
 *   <li>{@link FileUploadPreparationHandler}   - 临时文件创建 + 哈希计算</li>
 *   <li>{@link FileStorageSelectionHandler}   - 存储类型选择</li>
 *   <li>{@link FileDeduplicationHandler}       - 重复文件检查与策略处理</li>
 *   <li>{@link FileUploadExecutionHandler}     - 实际文件上传</li>
 *   <li>{@link FilePersistenceHandler}   - 数据持久化</li>
 * </ol>
 *
 * @author yaocode
 * @since 1.0.0
 */
public interface FileUploadHandler {

    /**
     * 执行处理
     *
     * @param context 上传上下文
     */
    void handle(FileUploadDto context);

    /**
     * 处理器名称（用于日志）
     */
    default String getName() {
        return getClass().getSimpleName();
    }
}
