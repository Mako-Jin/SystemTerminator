package com.yaocode.sts.auth.interfaces.api;

import com.yaocode.sts.auth.interfaces.model.params.CreateUserParams;
import com.yaocode.sts.auth.model.vo.UserInfoVo;
import com.yaocode.sts.common.web.model.ResultModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: Jin-LiangBo
 * @Date: 2025年10月07日 21:20
 */
@RequestMapping("/auth/user")
public interface UserInfoApi {

    /**
     * 查询单个用户
     *
     * @param userId 用户id
     * @return UserInfo 用户信息
     */
    @GetMapping("/single/{userId}")
    ResultModel<UserInfoVo> getSingle(@PathVariable String userId);

    /**
     * 单个添加用户
     * @param createUserParams 用户信息
     * @return ResultModel<?>
     */
    @PostMapping("/single/add")
    ResultModel<?> singleAdd(@RequestBody CreateUserParams createUserParams);

    /**
     * 用户注册
     * @param createUserParams 用户信息
     * @return ResultModel<?>
     */
    @PostMapping("/register")
    ResultModel<?> register(@RequestBody CreateUserParams createUserParams);

}
