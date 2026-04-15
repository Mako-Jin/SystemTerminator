package com.yaocode.sts.common.domain.context.spi;

import com.yaocode.sts.common.domain.valueobject.UserId;

/**
 * 用户信息解析器
 * @author: Jin-LiangBo
 * @date: 2026年04月15日 16:04
 */
public interface UserInfoResolver extends HttpHeaderResolver<UserId> {
}
