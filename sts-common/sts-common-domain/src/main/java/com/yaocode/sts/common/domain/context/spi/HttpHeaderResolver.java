package com.yaocode.sts.common.domain.context.spi;

import com.yaocode.sts.common.domain.valueobject.Identifier;
import com.yaocode.sts.common.domain.web.HttpRequestContext;

import java.util.Optional;

/**
 * Http请求头信息解析接口定义
 * @author: Jin-LiangBo
 * @date: 2026年04月15日 11:48
 */
public interface HttpHeaderResolver<T extends Identifier<String>> {

    /**
     * 解析方法
     * @param context Http上下文
     * @return java.util.Optional<T>
     */
    Optional<T> resolve(HttpRequestContext context);

}
