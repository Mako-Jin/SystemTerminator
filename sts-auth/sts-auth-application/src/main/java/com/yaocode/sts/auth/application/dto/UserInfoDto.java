package com.yaocode.sts.auth.application.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户信息dto
 * @author: Jin-LiangBo
 * @date: 2025年10月07日 23:15
 */
@Data
public class UserInfoDto {

    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机
     */
    private String phoneNum;
    /**
     * 租户id
     */
    private String tenantId;
    /**
     * 组织id
     */
    private String organizationId;
    /**
     * 用户组id
     */
    private String userGroupId;
    /**
     * 角色id列表
     */
    private List<String> roleIdList;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
