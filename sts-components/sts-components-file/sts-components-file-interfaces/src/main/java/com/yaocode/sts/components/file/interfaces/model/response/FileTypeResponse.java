package com.yaocode.sts.components.file.interfaces.model.response;

import lombok.Data;

import java.util.List;

/**
 * 文件类型VO
 */
@Data
public class FileTypeResponse {

    /**
     * 类型编码
     */
    private String typeCode;
    /**
     * 类型名称
     */
    private String typeName;
    /**
     * 类型描述
     */
    private String description;
    /**
     * 扩展名列表
     */
    private List<String> extensions;
    /**
     * 图标
     */
    private String icon;
    /**
     * 颜色
     */
    private String color;
    /**
     * 是否启用
     */
    private Boolean isEnabled;

}