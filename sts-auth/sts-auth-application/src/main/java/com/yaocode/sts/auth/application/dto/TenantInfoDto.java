package com.yaocode.sts.auth.application.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 租户信息Dto
 * @author: Jin-LiangBo
 * @date: 2025年10月16日 23:09
 */
@Data
public class TenantInfoDto {

    /**
     * 租户id
     */
    @TableId
    private String tenantId;
    /**
     * 租户名称
     */
    private String tenantName;
    /**
     * 租户编码
     */
    private String tenantCode;
    /**
     * 租户描述
     */
    private String tenantDesc;
    /**
     * 租户状态
     */
    private Integer tenantStatus;
    /**
     * 是否允许注册新用户
     */
    private String allowRegister;
    /**
     * 创建者id
     */
    private String parentId;

    /**
     * 创建者id
     */
    private String createUserId;
    /**
     * 创建者名
     */
    private String createUserName;
    /**
     * 更新者id
     */
    private String updateUserId;
    /**
     * 更新者名
     */
    private String updateUserName;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
