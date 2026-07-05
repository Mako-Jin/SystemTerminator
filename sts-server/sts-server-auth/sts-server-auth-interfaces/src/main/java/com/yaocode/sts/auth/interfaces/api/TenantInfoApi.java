package com.yaocode.sts.auth.interfaces.api;

import com.yaocode.sts.auth.interfaces.model.params.CreateTenantParams;
import com.yaocode.sts.common.web.model.ResultModel;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 租户接口
 * @author: Jin-LiangBo
 * @date: 2025年10月16日 22:37
 */
@RequestMapping("/auth/tenant")
public interface TenantInfoApi {

    /**
     * 单个添加租户
     * @param createTenantParams 租户信息
     * @return ResultModel<?>
     */
    @PostMapping("/single/add")
    ResultModel<?> singleAdd(@Valid @RequestBody CreateTenantParams createTenantParams);

}
