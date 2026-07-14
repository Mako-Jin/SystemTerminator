package com.yaocode.sts.components.file.core.enums;

import lombok.Getter;

@Getter
public enum FileErrorCodeEnums {

    // 文件上传错误 (2000-2999)
    FILE_UPLOAD_ERROR("012001", "文件上传失败"),
    FILE_TOO_LARGE("012002", "文件大小超限"),
    FILE_TYPE_NOT_ALLOWED("012003", "文件类型不允许"),
    FILE_EMPTY("012004", "文件为空"),
    FILE_NAME_INVALID("012005", "文件名非法"),

    // 存储错误 (3000-3999)
    STORAGE_ERROR("013001", "存储服务异常"),
    STORAGE_NOT_FOUND("013002", "存储节点不存在"),
    STORAGE_UNAVAILABLE("013003", "存储服务不可用"),
    STORAGE_CAPACITY_EXCEEDED("013004", "存储容量已满"),

    // 分片上传错误 (4000-4999)
    CHUNK_ERROR("014001", "分片上传失败"),
    CHUNK_MD5_MISMATCH("014002", "分片MD5校验失败"),
    CHUNK_ALREADY_UPLOADED("014003", "分片已上传"),
    CHUNK_NOT_FOUND("014004", "分片不存在"),

    // 权限错误 (5000-5999)
    UNAUTHORIZED("015001", "未授权"),
    FORBIDDEN("015002", "无权限访问"),

    // 系统错误 (9000-9999)
    SYSTEM_ERROR("019001", "系统异常"),
    TIMEOUT("019002", "请求超时");

    private final String code;
    private final String message;

    FileErrorCodeEnums(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
