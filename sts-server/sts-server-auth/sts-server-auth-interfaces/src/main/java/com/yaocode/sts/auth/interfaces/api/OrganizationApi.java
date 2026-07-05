package com.yaocode.sts.auth.interfaces.api;

import com.yaocode.sts.auth.interfaces.model.params.CreateOrganizationParams;
import com.yaocode.sts.common.web.model.ResultModel;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年10月25日 12:53
 */
@RequestMapping("/auth/org")
public interface OrganizationApi {

    /**
     * 新增组织
     * @param params 参数
     * @return ResultModel<?>
     */
    @PostMapping("/single/add")
    ResultModel<?> singleAdd(@RequestBody @Valid CreateOrganizationParams params);

    /**
     * 根据id查询组织id
     * @param organizationId 组织id
     * @return ResultModel<?>
     */
    @GetMapping("/getById/{organizationId}")
    ResultModel<?> getById(@RequestParam String organizationId);

}
