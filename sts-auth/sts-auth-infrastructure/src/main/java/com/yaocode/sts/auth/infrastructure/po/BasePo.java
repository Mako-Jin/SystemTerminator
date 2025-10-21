package com.yaocode.sts.auth.infrastructure.po;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 基础字段
 * @author: Jin-LiangBo
 * @date: 2025年10月14日 19:30
 */
@Data
public class BasePo {

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
