package com.yaocode.sts.auth.starter.controller;

import com.yaocode.sts.auth.interfaces.api.UserGroupApi;
import com.yaocode.sts.auth.interfaces.model.params.CreateUserGroupParams;
import com.yaocode.sts.common.web.annotation.SubRequestMapping;
import com.yaocode.sts.common.web.model.ResultModel;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年10月25日 13:05
 */
@SubRequestMapping("/v1")
public class UserGroupController implements UserGroupApi {
    @Override
    public ResultModel<?> singleAdd(CreateUserGroupParams params) {
        return null;
    }
}
