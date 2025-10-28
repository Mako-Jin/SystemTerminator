package com.yaocode.sts.auth.starter.controller;

import com.yaocode.sts.auth.application.dto.RoleInfoDto;
import com.yaocode.sts.auth.application.service.RoleInfoApplicationService;
import com.yaocode.sts.auth.interfaces.api.RoleInfoApi;
import com.yaocode.sts.auth.interfaces.assembler.RoleInfoAssembler;
import com.yaocode.sts.auth.interfaces.model.params.CreateRoleParams;
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
public class RoleInfoController implements RoleInfoApi {

    @Resource
    private RoleInfoApplicationService roleInfoApplicationService;

    @Resource
    private RoleInfoAssembler roleInfoAssembler;

    @Override
    public ResultModel<String> singleAdd(CreateRoleParams params) {
        RoleInfoDto roleInfoDto = roleInfoAssembler.toDto(params);
        return ResultUtils.ok(roleInfoApplicationService.singleAdd(roleInfoDto));
    }

}
