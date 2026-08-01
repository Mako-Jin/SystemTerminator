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
    String FILE_NOT_FOUND = "file.not.found";
    String FILE_EMPTY = "file.empty";
    String FILE_TOO_LARGE = "file.too.large";
    String FILE_SIZE_INVALID = "file.size.invalid";
    String FILE_NAME_EMPTY = "file.name.empty";
    String FILE_NAME_INVALID = "file.name.invalid";
    String FILE_HASH_MISMATCH = "file.hash.mismatch";
    String FILE_EXTENSION_NOT_ALLOWED = "file.extension.not.allowed";
    String FILE_ALREADY_EXISTS = "file.already.exists";
    String FILE_UPLOAD_FAILED = "file.upload.failed";

    // ==================== 上传 (200-299) ====================
    String UPLOAD_SUCCESS = "upload.success";
    String UPLOAD_FAILED = "upload.failed";
    String UPLOAD_INTERRUPTED = "upload.interrupted";
    String UPLOAD_CHUNK_MISSING = "upload.chunk.missing";
    String UPLOAD_CHUNK_MD5_MISMATCH = "upload.chunk.md5.mismatch";
    String UPLOAD_SESSION_NOT_FOUND = "upload.session.not.found";
    String UPLOAD_SESSION_EXPIRED = "upload.session.expired";
    String UPLOAD_SESSION_COMPLETED = "upload.session.completed";
    String UPLOAD_DUPLICATE_REUSE = "upload.duplicate.reuse";
    String UPLOAD_VERSION_CREATED = "upload.version.created";
    String UPLOAD_OVERWRITE_SUCCESS = "upload.overwrite.success";
    String UPLOAD_RESUME_SUCCESS = "upload.resume.success";
    String UPLOAD_CANCEL_SUCCESS = "upload.cancel.success";

    // ==================== 存储 (300-399) ====================
    String STORAGE_TYPE_NOT_SUPPORTED = "storage.type.not.supported";
    String STORAGE_NODE_NOT_FOUND = "storage.node.not.found";
    String STORAGE_NODE_UNAVAILABLE = "storage.node.unavailable";
    String STORAGE_CAPACITY_EXCEEDED = "storage.capacity.exceeded";

    // ==================== 版本 (400-499) ====================
    String VERSION_CREATE_DENIED = "version.create.denied";
    String VERSION_NOT_FOUND = "version.not.found";
    String VERSION_DELETE_DENIED = "version.delete.denied";
    String VERSION_TAG_EXIST = "version.tag.exist";
    String VERSION_TAG_NOT_FOUND = "version.tag.not.found";
    String VERSION_BRANCH_NOT_FOUND = "version.branch.not.found";
    String VERSION_MERGE_CONFLICT = "version.merge.conflict";

    // ==================== 权限 (500-599) ====================
    String OVERWRITE_DENIED = "overwrite.denied";
    String UNAUTHORIZED = "unauthorized";
    String FORBIDDEN = "forbidden";
    String ACCESS_DENIED = "access.denied";
}
