package com.yaocode.sts.file.core.constants;

import com.yaocode.sts.common.basic.constants.BasicI18nKeyConstants;

/**
 * 文件国际化消息Key常量
 * <p>
 * 定义所有国际化消息的Key，与 messages.properties 中的 key 对应
 * </p>
 *
 * @author Jin-LiangBo
 * @date 2026年04月21日 15:14
 */
public interface FileI18nKeyConstants extends BasicI18nKeyConstants {
    // ==================== 文件 (100-199) ====================
    String FILE_UPLOAD_ERROR = "file.upload.error";
    String FILE_NOT_FOUND = "file.not.found";
    String FILE_EMPTY = "file.empty";
    String FILE_TOO_LARGE = "file.too.large";
    String FILE_SIZE_INVALID = "file.size.invalid";
    String FILE_NAME_EMPTY = "file.name.empty";
    String FILE_NAME_INVALID = "file.name.invalid";
    String FILE_ALREADY_EXISTS = "file.already.exists";
    String FILE_HASH_MISMATCH = "file.hash.mismatch";
    String FILE_EXTENSION_NOT_ALLOWED = "file.extension.not.allowed";
    String FILE_READ_FAILED = "file.read.failed";
    String FILE_WRITE_FAILED = "file.write.failed";
    String FILE_DELETE_FAILED = "file.delete.failed";
    String FILE_CONTENT_UNCHANGED = "file.content.unchanged";
    String FILE_METADATA_UPDATE_FAILED = "file.metadata.update.failed";
    String FILE_PREPARE_FAILED = "file.prepare.failed";

    // ==================== 上传 (200-299) ====================
    String UPLOAD_FAILED = "upload.failed";
    String UPLOAD_INTERRUPTED = "upload.interrupted";
    String UPLOAD_TIMEOUT = "upload.timeout";
    String UPLOAD_SESSION_NOT_FOUND = "upload.session.not.found";
    String UPLOAD_SESSION_EXPIRED = "upload.session.expired";
    String UPLOAD_SESSION_COMPLETED = "upload.session.completed";
    String UPLOAD_CHUNK_MISSING = "upload.chunk.missing";
    String UPLOAD_CHUNK_MD5_MISMATCH = "upload.chunk.md5.mismatch";
    String UPLOAD_CHUNK_ALREADY_EXISTS = "upload.chunk.already.exists";
    String UPLOAD_PREPARE_FAILED = "upload.prepare.failed";
    String UPLOAD_CANCEL_FAILED = "upload.cancel.failed";

    // ==================== 下载 (300-399) ====================
    String DOWNLOAD_FAILED = "download.failed";
    String DOWNLOAD_INTERRUPTED = "download.interrupted";
    String DOWNLOAD_TOKEN_INVALID = "download.token.invalid";
    String DOWNLOAD_TOKEN_EXPIRED = "download.token.expired";
    String DOWNLOAD_RANGE_INVALID = "download.range.invalid";
    String DOWNLOAD_STREAM_FAILED = "download.stream.failed";

    // ==================== 存储 (400-499) ====================
    String STORAGE_ERROR = "storage.error";
    String STORAGE_TYPE_NOT_SUPPORTED = "storage.type.not.supported";
    String STORAGE_NODE_NOT_FOUND = "storage.node.not.found";
    String STORAGE_NODE_UNAVAILABLE = "storage.node.unavailable";
    String STORAGE_CAPACITY_EXCEEDED = "storage.capacity.exceeded";
    String STORAGE_CONNECTION_FAILED = "storage.connection.failed";
    String STORAGE_UPLOAD_FAILED = "storage.upload.failed";
    String STORAGE_DOWNLOAD_FAILED = "storage.download.failed";
    String STORAGE_DELETE_FAILED = "storage.delete.failed";
    String STORAGE_MIGRATE_FAILED = "storage.migrate.failed";
    String STORAGE_ARCHIVE_FAILED = "storage.archive.failed";
    String STORAGE_UNARCHIVE_FAILED = "storage.unarchive.failed";
    String STORAGE_HEALTH_CHECK_FAILED = "storage.health.check.failed";

    // ==================== 版本 (500-599) ====================
    String VERSION_CREATE_DENIED = "version.create.denied";
    String VERSION_NOT_FOUND = "version.not.found";
    String VERSION_DELETE_DENIED = "version.delete.denied";
    String VERSION_TAG_EXIST = "version.tag.exist";
    String VERSION_TAG_NOT_FOUND = "version.tag.not.found";
    String VERSION_BRANCH_NOT_FOUND = "version.branch.not.found";
    String VERSION_BRANCH_ALREADY_EXISTS = "version.branch.already.exists";
    String VERSION_BRANCH_DELETE_DENIED = "version.branch.delete.denied";
    String VERSION_MERGE_CONFLICT = "version.merge.conflict";
    String VERSION_ROLLBACK_FAILED = "version.rollback.failed";
    String VERSION_CONTROL_DISABLED = "version.control.disabled";
    String VERSION_ALREADY_LATEST = "version.already.latest";
    String VERSION_NO_COMMON_ANCESTOR = "version.no.common.ancestor";

    // ==================== 权限 (600-699) ====================
    String PERMISSION_DENIED = "permission.denied";
    String OVERWRITE_DENIED = "overwrite.denied";
    String DELETE_DENIED = "delete.denied";
    String UNAUTHORIZED = "unauthorized";
    String FORBIDDEN = "forbidden";
    String TENANT_MISMATCH = "tenant.mismatch";

    // ==================== 策略 (700-799) ====================
    String STRATEGY_NOT_SUPPORTED = "strategy.not.supported";
    String STRATEGY_EXECUTION_FAILED = "strategy.execution.failed";
    String STRATEGY_NOT_FOUND = "strategy.not.found";
    String DUPLICATE_STRATEGY_NOT_SUPPORTED = "duplicate.strategy.not.supported";
}