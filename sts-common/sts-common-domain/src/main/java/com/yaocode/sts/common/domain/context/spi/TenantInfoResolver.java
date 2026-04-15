package com.yaocode.sts.common.domain.context.spi;

import com.yaocode.sts.common.domain.valueobject.TenantId;

/**
 * 租户信息解析器
 * @author: Jin-LiangBo
 * @date: 2026年04月14日 18:18
 */
public interface TenantInfoResolver extends HttpHeaderResolver<TenantId> {

}
