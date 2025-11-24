package com.yaocode.sts.auth.starter.controller;

import com.yaocode.sts.auth.application.dto.OrganizationDto;
import com.yaocode.sts.auth.application.service.OrganizationApplicationService;
import com.yaocode.sts.auth.interfaces.api.OrganizationApi;
import com.yaocode.sts.auth.interfaces.assembler.OrganizationAssembler;
import com.yaocode.sts.auth.interfaces.model.params.CreateOrganizationParams;
import com.yaocode.sts.common.resources.annotation.ModuleResources;
import com.yaocode.sts.common.resources.annotation.ServiceResources;
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
@ModuleResources(
    code = "000000000002",
    name = "组织架构数据",
    desc = "组织架构资源数据控制器",
    path = "/auth/org/v1",
    belongTo = {
        @ServiceResources(
            code = "000000000",
            name = "用户中心服务",
            desc = "租户、组织、用户、资源、权限管理服务",
            path = "/auth"
        )
    }
)
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
