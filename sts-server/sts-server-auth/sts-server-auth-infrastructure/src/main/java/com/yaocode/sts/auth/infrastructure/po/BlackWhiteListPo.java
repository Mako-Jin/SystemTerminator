package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yaocode.sts.common.infrastructure.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 黑白名单配置表
 * 支持平台级和租户级两级配置
 *
 * @author: Jin-LiangBo
 * @date: 2026-06-25
 */
@Data
@TableName("auth_tbl_black_white_list")
@EqualsAndHashCode(callSuper = true)
public class BlackWhiteListPo extends BasePo {

    @TableId
    private String listId;

    /**
     * 租户ID
     * NULL 表示平台级配置
     * 非NULL 表示租户级配置
     */
    private String tenantId;

    /**
     * 名单类型：
     * IP - IP地址/段
     * DEVICE - 设备ID
     * CLIENT - 客户端ID
     * USER - 用户ID
     * COUNTRY - 国家/地区代码
     * USER_AGENT - User Agent正则
     */
    private String listType;

    /**
     * 名单值
     * IP支持CIDR格式：192.168.1.0/24
     * 支持通配符：*.example.com
     */
    private String listValue;

    /**
     * 名单动作：1-ALLOW（白名单）, 0-DENY（黑名单）
     */
    private Integer action;

    /**
     * 优先级（数字越大优先级越高）
     * 租户级配置默认高于平台级
     */
    private Integer priority;

    /**
     * 生效开始时间（NULL表示立即生效）
     */
    private LocalDateTime effectiveFrom;

    /**
     * 生效结束时间（NULL表示永久生效）
     */
    private LocalDateTime effectiveTo;

    /**
     * 是否启用：0-禁用, 1-启用
     */
    private Integer isEnabled;

    /**
     * 备注
     */
    private String remark;
}
