package com.yaocode.sts.auth.starter.controller;

import com.yaocode.sts.auth.interfaces.api.TestApi;
import com.yaocode.sts.common.web.annotation.SubRequestMapping;
import jakarta.annotation.Resource;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 测试控制器
 * @author: Jin-LiangBo
 * @date: 2026年04月09日 19:42
 */
@RestController
@SubRequestMapping("/v1")
public class TestController implements TestApi {

    @Resource
    private MessageSource messageSource;

    @Override
    public Map<String, String> testI18nMessages(String code, String lang) {
        Locale locale = new Locale(lang);
        Map<String, String> result = new HashMap<>(2);
        String message = messageSource.getMessage(code, null, "Not Found", locale);
        result.put("code", code);
        result.put("message", message);
        return result;
    }
}
