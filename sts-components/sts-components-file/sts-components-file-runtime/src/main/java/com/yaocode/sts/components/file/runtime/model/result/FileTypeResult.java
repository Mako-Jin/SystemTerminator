package com.yaocode.sts.components.file.runtime.model.result;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 文件类型结果
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class FileTypeResult {
    /** 类型代码 */
    private String typeCode;

    /** 类型名称 */
    private String typeName;

    /** 描述 */
    private String description;

    /** 扩展名列表 */
    private List<String> extensions;

    /** 图标 */
    private String icon;

    /** 颜色 */
    private String color;

    /** 是否启用 */
    private Boolean isEnabled;
}
