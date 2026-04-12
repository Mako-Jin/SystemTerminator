package com.yaocode.sts.common.i18n.repository;

/**
 * 国际化消息仓库接口
 * @author: Jin-LiangBo
 * @date: 2026年04月07日 18:51
 */
public interface I18nMessageRepository {

    /**
     * 根据国际化编码查询国际化消息
     * @param code 语言编码
     * @param locale 语言区域
     * @return java.lang.String
     */
    String getMessage(String code, String locale);

}
