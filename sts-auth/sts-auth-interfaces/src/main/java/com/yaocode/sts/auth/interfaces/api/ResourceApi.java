package com.yaocode.sts.auth.interfaces.api;

import com.yaocode.sts.auth.interfaces.model.params.CreateResourceParams;
import com.yaocode.sts.common.web.model.ResultModel;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月13日 22:24
 */
@RequestMapping("/auth/resource")
public interface ResourceApi {

    /**
     * 新增权限资源
     * @param params 参数
     * @return ResultModel<?>
     */
    @PostMapping("/single/add")
    ResultModel<?> singleAdd(@RequestBody @Valid CreateResourceParams params);

}
