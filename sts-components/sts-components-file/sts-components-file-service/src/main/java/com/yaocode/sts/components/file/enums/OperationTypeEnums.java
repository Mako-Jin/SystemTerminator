package com.yaocode.sts.components.file.enums;

import lombok.Getter;

/**
 * 操作类型枚举
 */
@Getter
public enum OperationTypeEnums {

    UPLOAD(1, "上传"),
    DOWNLOAD(2, "下载"),
    DELETE(3, "删除"),
    VIEW(4, "查看"),
    UPDATE(5, "更新"),
    COPY(6, "复制");

    private final Integer code;
    private final String desc;

    OperationTypeEnums(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
