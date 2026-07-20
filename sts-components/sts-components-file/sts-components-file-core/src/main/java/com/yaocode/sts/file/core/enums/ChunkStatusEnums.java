package com.yaocode.sts.file.core.enums;

import lombok.Getter;

/**
 * 分片状态枚举
 */
@Getter
public enum ChunkStatusEnums {

    PENDING(0, "待上传"),
    UPLOADING(1, "上传中"),
    COMPLETED(2, "已完成"),
    FAILED(3, "失败"),
    CANCELLED(4, "已取消");

    private final Integer code;
    private final String desc;

    ChunkStatusEnums(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
