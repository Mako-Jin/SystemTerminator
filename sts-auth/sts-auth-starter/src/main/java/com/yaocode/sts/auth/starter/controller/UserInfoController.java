package com.yaocode.sts.auth.starter.controller;

import com.yaocode.sts.auth.application.dto.UserInfoDto;
import com.yaocode.sts.auth.application.service.UserInfoApplicationService;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserId;
import com.yaocode.sts.auth.interfaces.api.UserInfoApi;
import com.yaocode.sts.auth.interfaces.assembler.UserInfoAssembler;
import com.yaocode.sts.auth.interfaces.model.params.CreateUserParams;
import com.yaocode.sts.auth.interfaces.model.vo.UserInfoVo;
import com.yaocode.sts.common.web.annotation.SubRequestMapping;
import com.yaocode.sts.common.web.model.ResultModel;
import com.yaocode.sts.common.web.result.ResultUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户信息控制器层
 * @author: Jin-LiangBo
 * @date: 2025年10月07日 21:25
 */
@Slf4j
@RestController
@SubRequestMapping("/v1")
public class UserInfoController implements UserInfoApi {

    @Resource
    private UserInfoApplicationService userInfoApplicationService;

    @Resource
    private UserInfoAssembler userInfoAssembler;

    @Override
    public ResultModel<UserInfoVo> getSingle(String userId) {
        UserInfoDto userDto = userInfoApplicationService.getUserById(userId);
        UserInfoVo userInfoVo = userInfoAssembler.toVo(userDto);
        return ResultUtils.ok(userInfoVo);
    }

    @Override
    public ResultModel<String> singleAdd(CreateUserParams createUserParams) {
        String userId = userInfoApplicationService.singleAdd(userInfoAssembler.toDto(createUserParams));
        return ResultUtils.ok(userId);
    }

    @Override
    public ResultModel<UserId> register(CreateUserParams createUserParams) {
        // UserId userId = userInfoApplicationService.register(userInfoAssembler.toDto(createUserParams));
        // return ResultUtils.ok(userId);
        return ResultUtils.ok(null);
    }
}
