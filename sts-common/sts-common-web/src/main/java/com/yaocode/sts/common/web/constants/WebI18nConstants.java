package com.yaocode.sts.common.web.constants;

import com.yaocode.sts.common.basic.constants.BasicI18nKeyConstants;

/**
 * 国际化消息key
 * @author: Jin-LiangBo
 * @date: 2026年04月21日 15:35
 */
public interface WebI18nConstants extends BasicI18nKeyConstants {

    // ==================== 通用消息 ====================
    /**
     * 请求成功
     */
    String WEB_REQUEST_SUCCESS = "common.web.request.success";

    /**
     * 系统异常
     */
    String WEB_SYSTEM_ERROR = "common.web.system.error";

    /**
     * 参数校验异常
     */
    String WEB_PARAM_ERROR = "common.web.param.error";

    // ==================== 安全相关 ====================
    /**
     * SQL注入检测消息
     */
    String SQL_INJECTION_DEFAULT_MESSAGE = "common.web.sql.injection.default.message";

    /**
     * XSS攻击检测消息
     */
    String XSS_ATTACK_DEFAULT_MESSAGE = "common.web.xss.attack.default.message";

    // ==================== 请求结果码描述 ====================
    String RESULT_CODE_SUCCESS = "common.web.result.code.success";
    String RESULT_CODE_SYSTEM_ERROR = "common.web.result.code.system_error";
    String RESULT_CODE_PARAM_ERROR = "common.web.result.code.param_error";

    // ==================== Header描述 ====================
    String HEADER_CLIENT_ID = "common.web.header.client_id";
    String HEADER_CLIENT_TYPE = "common.web.header.client_type";
    String HEADER_CLIENT_VERSION = "common.web.header.client_version";
    String HEADER_DEVICE_ID = "common.web.header.device_id";
    String HEADER_DEVICE_TYPE = "common.web.header.device_type";
    String HEADER_TRACE_ID = "common.web.header.trace_id";
    String HEADER_REQUEST_ID = "common.web.header.request_id";
    String HEADER_AUTHORIZATION = "common.web.header.authorization";

}
