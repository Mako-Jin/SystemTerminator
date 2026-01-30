package com.yaocode.sts.auth.starter.controller;

import com.yaocode.sts.auth.application.dto.ResourceDto;
import com.yaocode.sts.auth.application.service.ResourceApplicationService;
import com.yaocode.sts.auth.interfaces.api.ResourceApi;
import com.yaocode.sts.auth.interfaces.assembler.ResourceAssembler;
import com.yaocode.sts.auth.interfaces.model.params.BatchCreateResourceParams;
import com.yaocode.sts.auth.interfaces.model.params.CreateResourceParams;
import com.yaocode.sts.common.web.annotation.SubRequestMapping;
import com.yaocode.sts.common.web.model.ResultModel;
import com.yaocode.sts.common.web.result.ResultUtils;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月13日 22:43
 */
@RestController
@SubRequestMapping("/v1")
public class ResourceController implements ResourceApi {

    @Resource
    private ResourceApplicationService resourceApplicationService;

    @Resource
    private ResourceAssembler resourceAssembler;

    @Override
    public ResultModel<?> singleAdd(CreateResourceParams params) {
        ResourceDto resourceDto = resourceAssembler.toDto(params);
        return ResultUtils.ok(resourceApplicationService.singleAdd(resourceDto));
    }

    @Override
    public ResultModel<?> batchAdd(List<BatchCreateResourceParams> params) {
        List<ResourceDto> resourceDtoList = resourceAssembler.toDtoList(params);
        return ResultUtils.ok(resourceApplicationService.batchAdd(resourceDtoList));
    }
}
