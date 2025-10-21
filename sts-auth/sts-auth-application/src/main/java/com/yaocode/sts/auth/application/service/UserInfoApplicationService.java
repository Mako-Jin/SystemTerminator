package com.yaocode.sts.auth.application.service;

import com.yaocode.sts.auth.application.dto.UserInfoDto;
import com.yaocode.sts.auth.application.dto.UserRegistrationDto;

/**
 * 用户信息应用层接口
 * @author: Jin-LiangBo
 * @date: 2025年10月07日 22:01
 */
public interface UserInfoApplicationService {

    /**
     * 根据id查询用户信息
     * @param userId 用户id
     * @return UserInfoDTO
     */
    UserInfoDto getUserById(String userId);

    /**
     * 新增单个用户
     * @param userInfoDto 用户信息
     * @return UserId
     */
    String singleAdd(UserInfoDto userInfoDto);

    String register(UserRegistrationDto userRegistrationDto);

}
