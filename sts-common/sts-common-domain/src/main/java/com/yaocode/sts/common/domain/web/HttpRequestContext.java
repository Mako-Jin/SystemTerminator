package com.yaocode.sts.common.domain.web;

import java.util.Optional;

/**
 * HTTP请求上下文接口 - 领域层定义抽象
 * @author: Jin-LiangBo
 * @date: 2026年04月14日 18:28
 */
public interface HttpRequestContext {

    /**
     * 根据key获取请求头
     * @param name 请求头key
     * @return java.lang.String
     */
    Optional<String> getHeader(String name);

}
