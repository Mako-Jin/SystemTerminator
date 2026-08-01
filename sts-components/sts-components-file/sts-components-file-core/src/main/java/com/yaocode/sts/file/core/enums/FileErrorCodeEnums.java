package com.yaocode.sts.file.core.enums;

import lombok.Getter;

/**
 * 文件服务错误码枚举
 * <p>
 * 错误码格式: 模块(2位) + 分类(2位) + 序号(2位)
 * </p>
 *
 * @author Jin-LiangBo
 * @date 2025年10月08日 17:53
 */
@Getter
public enum FileErrorCodeEnums {

    // ==================== 文件错误 (10-29) ====================
    // ==================== 文件错误 (10-29) ====================
    FILE_UPLOAD_ERROR("100000", "file.upload.error"),
    FILE_NOT_FOUND("100001", "file.not.found"),
    FILE_EMPTY("100002", "file.empty"),
    FILE_TOO_LARGE("100003", "file.too.large"),
    FILE_SIZE_INVALID("100004", "file.size.invalid"),
    FILE_NAME_EMPTY("100005", "file.name.empty"),
    FILE_NAME_INVALID("100006", "file.name.invalid"),
    FILE_ALREADY_EXISTS("100007", "file.already.exists"),
    FILE_HASH_MISMATCH("100008", "file.hash.mismatch"),
    FILE_EXTENSION_NOT_ALLOWED("100009", "file.extension.not.allowed"),
    FILE_READ_FAILED("100010", "file.read.failed"),
    FILE_WRITE_FAILED("100011", "file.write.failed"),
    FILE_DELETE_FAILED("100012", "file.delete.failed"),
    FILE_CONTENT_UNCHANGED("100013", "file.content.unchanged"),
    FILE_METADATA_UPDATE_FAILED("100014", "file.metadata.update.failed"),
    FILE_PREPARE_FAILED("100015", "file.prepare.failed"),

    // ==================== 上传错误 (30-49) ====================
    UPLOAD_FAILED("103001", "upload.failed"),
    UPLOAD_INTERRUPTED("103002", "upload.interrupted"),
    UPLOAD_TIMEOUT("103003", "upload.timeout"),
    UPLOAD_SESSION_NOT_FOUND("103004", "upload.session.not.found"),
    UPLOAD_SESSION_EXPIRED("103005", "upload.session.expired"),
    UPLOAD_SESSION_COMPLETED("103006", "upload.session.completed"),
    UPLOAD_CHUNK_MISSING("103007", "upload.chunk.missing"),
    UPLOAD_CHUNK_MD5_MISMATCH("103008", "upload.chunk.md5.mismatch"),
    UPLOAD_CHUNK_ALREADY_EXISTS("103009", "upload.chunk.already.exists"),
    UPLOAD_PREPARE_FAILED("103010", "upload.prepare.failed"),
    UPLOAD_CANCEL_FAILED("103011", "upload.cancel.failed"),

    // ==================== 下载错误 (50-69) ====================
    DOWNLOAD_FAILED("105001", "download.failed"),
    DOWNLOAD_INTERRUPTED("105002", "download.interrupted"),
    DOWNLOAD_TOKEN_INVALID("105003", "download.token.invalid"),
    DOWNLOAD_TOKEN_EXPIRED("105004", "download.token.expired"),
    DOWNLOAD_RANGE_INVALID("105005", "download.range.invalid"),
    DOWNLOAD_STREAM_FAILED("10506", "download.stream.failed"),

    // ==================== 存储错误 (70-89) ====================
    STORAGE_ERROR("107000", "storage.error"),
    STORAGE_TYPE_NOT_SUPPORTED("107001", "storage.type.not.supported"),
    STORAGE_NODE_NOT_FOUND("107002", "storage.node.not.found"),
    STORAGE_NODE_UNAVAILABLE("107003", "storage.node.unavailable"),
    STORAGE_CAPACITY_EXCEEDED("107004", "storage.capacity.exceeded"),
    STORAGE_CONNECTION_FAILED("107005", "storage.connection.failed"),
    STORAGE_UPLOAD_FAILED("107006", "storage.upload.failed"),
    STORAGE_DOWNLOAD_FAILED("107007", "storage.download.failed"),
    STORAGE_DELETE_FAILED("107008", "storage.delete.failed"),
    STORAGE_MIGRATE_FAILED("107009", "storage.migrate.failed"),
    STORAGE_ARCHIVE_FAILED("107010", "storage.archive.failed"),
    STORAGE_UNARCHIVE_FAILED("107011", "storage.unarchive.failed"),
    STORAGE_HEALTH_CHECK_FAILED("107012", "storage.health.check.failed"),

    // ==================== 版本错误 (90-109) ====================
    VERSION_CREATE_DENIED("109001", "version.create.denied"),
    VERSION_NOT_FOUND("109002", "version.not.found"),
    VERSION_DELETE_DENIED("109003", "version.delete.denied"),
    VERSION_TAG_EXIST("109004", "version.tag.exist"),
    VERSION_TAG_NOT_FOUND("109005", "version.tag.not.found"),
    VERSION_BRANCH_NOT_FOUND("109006", "version.branch.not.found"),
    VERSION_BRANCH_ALREADY_EXISTS("109007", "version.branch.already.exists"),
    VERSION_BRANCH_DELETE_DENIED("109008", "version.branch.delete.denied"),
    VERSION_MERGE_CONFLICT("109009", "version.merge.conflict"),
    VERSION_ROLLBACK_FAILED("109010", "version.rollback.failed"),
    VERSION_CONTROL_DISABLED("109011", "version.control.disabled"),
    VERSION_ALREADY_LATEST("109012", "version.already.latest"),
    VERSION_NO_COMMON_ANCESTOR("109013", "version.no.common.ancestor"),

    // ==================== 权限错误 (110-129) ====================
    PERMISSION_DENIED("110001", "permission.denied"),
    OVERWRITE_DENIED("110002", "overwrite.denied"),
    DELETE_DENIED("110003", "delete.denied"),
    UNAUTHORIZED("110004", "unauthorized"),
    FORBIDDEN("110005", "forbidden"),
    TENANT_MISMATCH("110006", "tenant.mismatch"),

    // ==================== 策略错误 (130-149) ====================
    STRATEGY_NOT_SUPPORTED("130001", "strategy.not.supported"),
    STRATEGY_EXECUTION_FAILED("130002", "strategy.execution.failed"),
    STRATEGY_NOT_FOUND("130003", "strategy.not.found"),
    DUPLICATE_STRATEGY_NOT_SUPPORTED("130004", "duplicate.strategy.not.supported"),
    ;

    private final String code;
    private final String msg;

    FileErrorCodeEnums(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 根据code获取枚举
     */
    public static FileErrorCodeEnums fromCode(String code) {
        if (code == null) {
            return null;
        }
        for (FileErrorCodeEnums e : values()) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        return null;
    }

    /**
     * 获取错误码（带模块前缀）
     */
    public String getFullCode() {
        return "FILE_" + this.code;
    }
}
