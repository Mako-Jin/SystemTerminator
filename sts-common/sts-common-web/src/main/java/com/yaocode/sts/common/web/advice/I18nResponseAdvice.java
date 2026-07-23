package com.yaocode.sts.common.web.advice;

import com.yaocode.sts.common.tools.JSONUtils;
import com.yaocode.sts.common.tools.messages.MessageUtils;
import com.yaocode.sts.common.web.model.ResultModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

/**
 * 响应国际化拦截器
 * 自动将 ResultModel 中的国际化key转换为实际消息
 * @author: Jin-LiangBo
 * @date: 2026年07月23日
 */
@Slf4j
@RestControllerAdvice
public class I18nResponseAdvice implements ResponseBodyAdvice<Object> {

    private final MessageUtils messageUtils;

    public I18nResponseAdvice(MessageUtils messageUtils) {
        this.messageUtils = messageUtils;
    }

    @Override
    public boolean supports(
            @NonNull MethodParameter returnType,
            @NonNull Class<? extends HttpMessageConverter<?>> converterType
    ) {
        return true;
    }

    @Nullable
    @Override
    public Object beforeBodyWrite(
            @Nullable Object body, @NonNull MethodParameter returnType, @NonNull MediaType selectedContentType,
            @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
            @NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response
    ) {
        // 处理 ResultModel
        if (body instanceof ResultModel<?> result) {
            return processResultModel(result);
        }

        // 处理 String 类型（可能是 JSON 字符串）
        if (body instanceof String) {
            try {
                ResultModel<?> result = JSONUtils.parseObject((String) body, ResultModel.class);
                if (Objects.isNull(result)) {
                    return body;
                }
                ResultModel<?> processed = processResultModel(result);
                return JSONUtils.toJson(processed);
            } catch (Exception e) {
                // 不是 ResultModel，直接返回
                return body;
            }
        }

        return body;
    }

    /**
     * 处理 ResultModel
     */
    private ResultModel<?> processResultModel(ResultModel<?> result) {
        String msg = result.getDesc();
        if (msg == null || msg.isEmpty()) {
            return result;
        }
        result.setDesc(messageUtils.getMessage(msg));
        return result;
    }
}
