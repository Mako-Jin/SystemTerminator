package com.yaocode.sts.auth.interfaces.api;

import com.yaocode.sts.auth.interfaces.model.params.CreateOrganizationParams;
import com.yaocode.sts.common.web.model.ResultModel;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
