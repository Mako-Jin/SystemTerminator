package com.yaocode.sts.auth.interfaces.api;

import com.yaocode.sts.auth.interfaces.model.params.CreateUserGroupParams;
import com.yaocode.sts.common.web.model.ResultModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年10月25日 12:53
 */
@RequestMapping("/auth/user-group")
public interface UserGroupApi {

    /**
     * 新增用户组
     * @param params 参数
     * @return ResultModel<?>
     */
    @PostMapping("/single/add")
    ResultModel<?> singleAdd(@RequestBody CreateUserGroupParams params);

}
