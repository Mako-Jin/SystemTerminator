package com.yaocode.sts.auth.starter.controller;

import com.yaocode.sts.auth.application.dto.UserGroupDto;
import com.yaocode.sts.auth.application.service.UserGroupApplicationService;
import com.yaocode.sts.auth.interfaces.api.UserGroupApi;
import com.yaocode.sts.auth.interfaces.assembler.UserGroupAssembler;
import com.yaocode.sts.auth.interfaces.model.params.CreateUserGroupParams;
import com.yaocode.sts.common.web.annotation.SubRequestMapping;
import com.yaocode.sts.common.web.model.ResultModel;
import com.yaocode.sts.common.web.result.ResultUtils;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年10月25日 13:05
 */
@RestController
@SubRequestMapping("/v1")
public class UserGroupController implements UserGroupApi {

    @Resource
    private UserGroupAssembler userGroupAssembler;

    @Resource
    private UserGroupApplicationService userGroupApplicationService;

    @Override
    public ResultModel<?> singleAdd(CreateUserGroupParams params) {
        UserGroupDto userGroupDto = userGroupAssembler.toDto(params);
        return ResultUtils.ok(userGroupApplicationService.singleAdd(userGroupDto));
    }
}
