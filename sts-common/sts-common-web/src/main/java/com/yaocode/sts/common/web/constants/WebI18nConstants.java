package com.yaocode.sts.common.web.constants;

import com.yaocode.sts.common.basic.constants.BasicI18nKeyConstants;

/**
 * 国际化消息key
 * @author: Jin-LiangBo
 * @date: 2026年04月21日 15:35
 */
public interface WebI18nConstants extends BasicI18nKeyConstants {

    /**
     * 系统异常key
     */
    String WEB_REQUEST_SUCCESS = "common.web.request.success";
    /**
     * 系统异常key
     */
    String WEB_SYSTEM_ERROR = "common.web.system.error";
    /**
     * 参数校验异常key
     */
    String WEB_PARAM_ERROR = "common.web.param.error";
    /**
     * sql注入默认消息
     */
    String SQL_INJECTION_DEFAULT_MESSAGE = "common.web.sql.injection.default.message";

}
