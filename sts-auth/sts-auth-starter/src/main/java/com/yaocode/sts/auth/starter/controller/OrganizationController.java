package com.yaocode.sts.auth.starter.controller;

import com.yaocode.sts.auth.application.dto.OrganizationDto;
import com.yaocode.sts.auth.application.service.OrganizationApplicationService;
import com.yaocode.sts.auth.interfaces.api.OrganizationApi;
import com.yaocode.sts.auth.interfaces.assembler.OrganizationAssembler;
import com.yaocode.sts.auth.interfaces.model.params.CreateOrganizationParams;
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
public class OrganizationController implements OrganizationApi {

    @Resource
    private OrganizationApplicationService organizationApplicationService;

    @Resource
    private OrganizationAssembler organizationAssembler;

    @Override
    public ResultModel<String> singleAdd(CreateOrganizationParams params) {
        OrganizationDto organizationDto = organizationAssembler.toDto(params);
        String organizationId = organizationApplicationService.singleAdd(organizationDto);
        return ResultUtils.ok(organizationId);
    }

}
