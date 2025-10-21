package com.yaocode.sts.auth.starter.controller;

import com.yaocode.sts.auth.application.dto.TenantInfoDto;
import com.yaocode.sts.auth.application.service.TenantInfoApplicationService;
import com.yaocode.sts.auth.interfaces.api.RoleInfoApi;
import com.yaocode.sts.auth.interfaces.api.TenantInfoApi;
import com.yaocode.sts.auth.interfaces.assembler.TenantInfoAssembler;
import com.yaocode.sts.auth.interfaces.model.params.CreateTenantParams;
import com.yaocode.sts.common.web.annotation.SubRequestMapping;
import com.yaocode.sts.common.web.model.ResultModel;
import com.yaocode.sts.common.web.result.ResultUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

/**
 * 租户接口实现
 * @author: Jin-LiangBo
 * @date: 2025年10月16日 22:42
 */
@Slf4j
@RestController
@SubRequestMapping("/v1")
public class TenantInfoController implements TenantInfoApi, RoleInfoApi {

    @Resource
    private TenantInfoApplicationService tenantInfoApplicationService;

    @Resource
    private TenantInfoAssembler tenantInfoAssembler;

    @Override
    public ResultModel<?> singleAdd(CreateTenantParams createTenantParams) {
        TenantInfoDto tenantInfoDto = tenantInfoAssembler.toDto(createTenantParams);
        String tenantId = tenantInfoApplicationService.singleAdd(tenantInfoDto);
        return ResultUtils.ok(tenantId);
    }

}
