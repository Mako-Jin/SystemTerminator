package com.yaocode.sts.auth.interfaces.model.params;

/**
 * 新增用户参数
 * @author: Jin-LiangBo
 * @date: 2025年10月12日 13:57
 */
public record CreateUserParams (
        String tenantId,
        String orgId,
        String username,
        String email,
        String phoneNum,
        String nickname
) {}
