package com.yaocode.sts.file.plugins.local;

/**
 * 本地存储国际化消息 Key 常量
 * <p>
 * 与 messages.properties 中的 key 对应，支持国际化
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
public interface LocalStorageI18nKeyConstants {

    // ==================== 存储初始化 (800-809) ====================

    /** 存储初始化失败 */
    String INIT_FAILED = "local.storage.init.failed";

    // ==================== 参数校验 (810-819) ====================

    /** 文件输入流为空 */
    String INPUT_STREAM_NULL = "local.storage.input.stream.null";

    /** 文件名为空 */
    String FILE_NAME_EMPTY = "local.storage.file.name.empty";

    /** 文件路径为空 */
    String FILE_PATH_EMPTY = "local.storage.file.path.empty";

    /** 文件大小超限 */
    String FILE_SIZE_EXCEEDED = "local.storage.file.size.exceeded";

    // ==================== 存储操作 (820-839) ====================

    /** 文件上传失败 */
    String UPLOAD_FAILED = "local.storage.upload.failed";

    /** 文件下载失败 */
    String DOWNLOAD_FAILED = "local.storage.download.failed";

    /** 文件删除失败 */
    String DELETE_FAILED = "local.storage.delete.failed";

    /** 文件不存在 */
    String FILE_NOT_EXIST = "local.storage.file.not.exist";

    // ==================== 分片操作 (840-849) ====================

    /** 分片上传失败 */
    String CHUNK_UPLOAD_FAILED = "local.storage.chunk.upload.failed";

    /** 分片合并失败 */
    String CHUNK_MERGE_FAILED = "local.storage.chunk.merge.failed";

    /** 分片目录不存在 */
    String CHUNK_DIR_NOT_FOUND = "local.storage.chunk.dir.not.found";

    /** 分片文件缺失 */
    String CHUNK_FILES_EMPTY = "local.storage.chunk.files.empty";

}
