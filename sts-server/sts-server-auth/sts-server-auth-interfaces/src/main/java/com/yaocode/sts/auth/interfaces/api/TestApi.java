package com.yaocode.sts.auth.interfaces.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * 测试接口
 * @author: Jin-LiangBo
 * @date: 2026年04月09日 19:42
 */
@RequestMapping("/auth/test")
public interface TestApi {

    /**
     * 测试国际化消息
     * @param code 消息key
     * @param lang 语言
     * @return 消息
     */
    @GetMapping("/i18n")
    Map<String, String> testI18nMessages(@RequestParam String code, @RequestParam String lang);

}
