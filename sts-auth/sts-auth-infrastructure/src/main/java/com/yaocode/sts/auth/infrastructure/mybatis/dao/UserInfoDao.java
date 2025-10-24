package com.yaocode.sts.auth.infrastructure.mybatis.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yaocode.sts.auth.infrastructure.po.UserInfoPo;

import java.util.List;

/**
 * 用户信息dao
 * @author: Jin-LiangBo
 * @date: 2025年10月07日 21:59
 */
public interface UserInfoDao extends IService<UserInfoPo> {

    /**
     * 根据用户名查询用户
     * @param userIdList 用户id
     * @param username 用户名
     * @return UserInfoPo
     */
    UserInfoPo getByUsername(List<String> userIdList, String username);

}
