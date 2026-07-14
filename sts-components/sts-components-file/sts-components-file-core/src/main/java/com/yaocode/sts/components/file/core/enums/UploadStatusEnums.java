package com.yaocode.sts.components.file.core.enums;

import lombok.Getter;

/**
 * 上传状态枚举
 */
@Getter
public enum UploadStatusEnums {

    UPLOADING(0, "上传中"),
    COMPLETED(1, "已完成"),
    FAILED(2, "失败"),
    PAUSED(3, "暂停"),
    CANCELLED(4, "已取消");

    private final Integer code;
    private final String desc;

    UploadStatusEnums(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
