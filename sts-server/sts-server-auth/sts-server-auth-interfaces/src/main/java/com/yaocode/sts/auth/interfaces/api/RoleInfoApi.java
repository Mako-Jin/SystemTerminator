package com.yaocode.sts.auth.interfaces.api;

import com.yaocode.sts.auth.interfaces.model.params.CreateRoleParams;
import com.yaocode.sts.common.web.model.ResultModel;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 角色信息控制器
 * @author: Jin-LiangBo
 * @date: 2025年10月18日 10:11
 */
@RequestMapping("/auth/role")
public interface RoleInfoApi {

    /**
     * 新增角色
     * @param params 参数
     * @return ResultModel<?>
     */
    @PostMapping("/single/add")
    ResultModel<?> singleAdd(@RequestBody @Valid CreateRoleParams params);

}
