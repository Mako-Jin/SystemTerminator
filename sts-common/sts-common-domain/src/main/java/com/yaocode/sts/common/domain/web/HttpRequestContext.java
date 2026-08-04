package com.yaocode.sts.common.domain.web;

import java.util.Map;
import java.util.Optional;

/**
 * HTTP请求上下文接口 - 领域层定义抽象
 * 提供对 HTTP Header 和 Query Parameter 的统一访问
 * 解析优先级：Header > Parameter（由调用方决定）
 * 
 * @author: Jin-LiangBo
 * @date: 2026年04月14日 18:28
 */
public interface HttpRequestContext {

    /**
     * 根据key获取请求头
     * @param name 请求头key
     * @return Optional<String>
     */
    Optional<String> getHeader(String name);

    /**
     * 根据key获取请求参数（Query Parameter 或 Form Parameter）
     * @param name 参数key
     * @return Optional<String>
     */
    Optional<String> getParameter(String name);

    /**
     * 便捷方法：先从 Header 获取，Header 没有则从 Parameter 获取
     * 适用于同名字段可能通过 Header 或 Parameter 传递的场景
     * 
     * @param name 字段名
     * @return Optional<String>
     */
    default Optional<String> getHeaderOrParameter(String name) {
        Optional<String> value = getParameter(name);
        if (value.isPresent()) {
            return value;
        }
        return getHeader(name);
    }

    /**
     * 获取所有请求头
     * @return Map<String, String>
     */
    Map<String, String> getHeaders();

    /**
     * 获取所有请求参数
     * @return Map<String, String[]>
     */
    Map<String, String[]> getParameters();
}