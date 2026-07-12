package com.yaocode.sts.components.file.interfaces.model.response;

import lombok.Data;

import java.util.List;

/**
 * 权限项
 */
@Data
public class PermissionItemResponse {

    /**
     * 权限项ID
     */
    private String id;
    /**
     * 权限项名称
     */
    private String name;
    /**
     * 类型 USER, ROLE, GROUP
     */
    private String type;
    /**
     * 操作权限列表 READ, WRITE, DELETE, SHARE
     */
    private List<String> actions;

}