package com.yaocode.sts.common.basic.constants;

/**
 * 基础常量
 * @author: Jin-LiangBo
 * @date: 2026年04月21日 16:09
 */
public interface IConstants {

    /**
     * 布尔值对应的数字常量 - 是/启用/允许/成功
     */
    Integer YES = 1;
    /**
     * 布尔值对应的数字常量 - 否/禁用/拒绝/失败
     */
    Integer NO = 0;

    /**
     * 成功状态码
     */
    Integer SUCCESS_CODE = 1;
    /**
     * 失败状态码
     */
    Integer FAILED_CODE = 0;

    /**
     * 启用状态码
     */
    Integer ENABLED_CODE = 1;
    /**
     * 禁用状态码
     */
    Integer DISABLED_CODE = 0;

    /**
     * 允许状态码
     */
    Integer ALLOW_CODE = 1;
    /**
     * 拒绝状态码
     */
    Integer DENY_CODE = 0;

}