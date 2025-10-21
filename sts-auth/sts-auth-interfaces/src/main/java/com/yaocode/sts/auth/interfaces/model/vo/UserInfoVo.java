package com.yaocode.sts.auth.interfaces.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 视图层对象
 * @author: Jin-LiangBo
 * @date: 2025年10月07日 21:45
 */
@Data
public class UserInfoVo {

    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
