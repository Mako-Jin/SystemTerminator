package com.yaocode.sts.auth.starter.controller;

import com.yaocode.sts.auth.application.dto.OrganizationDto;
import com.yaocode.sts.auth.application.service.OrganizationApplicationService;
import com.yaocode.sts.auth.interfaces.api.OrganizationApi;
import com.yaocode.sts.auth.interfaces.assembler.OrganizationAssembler;
import com.yaocode.sts.auth.interfaces.model.params.CreateOrganizationParams;
import com.yaocode.sts.auth.interfaces.model.vo.OrganizationInfoVo;
import com.yaocode.sts.common.resources.annotation.ApiResources;
import com.yaocode.sts.common.resources.annotation.ModuleResources;
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
@ModuleResources(code = "000000000002", name = "组织架构数据", desc = "组织架构资源数据控制器")
public class OrganizationController implements OrganizationApi {

    @Resource
    private OrganizationApplicationService organizationApplicationService;

    @Resource
    private OrganizationAssembler organizationAssembler;

    @Override
    @ApiResources(code = "000000000002001", name = "新增", desc = "新增单个组织架构")
    public ResultModel<String> singleAdd(CreateOrganizationParams params) {
        OrganizationDto organizationDto = organizationAssembler.toDto(params);
        String organizationId = organizationApplicationService.singleAdd(organizationDto);
        return ResultUtils.ok(organizationId);
    }

    @Override
    public ResultModel<?> getById(String organizationId) {
        OrganizationDto organizationDto = organizationApplicationService.getById(organizationId);
        OrganizationInfoVo organizationInfoVo = organizationAssembler.toVo(organizationDto);
        return ResultUtils.ok(organizationInfoVo);
    }

}
