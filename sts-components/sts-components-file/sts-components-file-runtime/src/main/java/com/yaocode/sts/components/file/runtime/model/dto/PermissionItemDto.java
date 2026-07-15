package com.yaocode.sts.components.file.runtime.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 权限项DTO
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class PermissionItemDto {
    /** ID */
    private String id;

    /** 名称 */
    private String name;

    /** 类型（USER/ROLE/GROUP） */
    private String type;

    /** 权限操作列表（READ/WRITE/DELETE/SHARE） */
    private List<String> actions;
}