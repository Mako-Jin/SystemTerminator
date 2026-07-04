package com.yaocode.sts.common.web.constants;

import com.yaocode.sts.common.basic.constants.SymbolConstants;


public interface WebConstants {

    // ==================== 正则表达式常量 ====================
    /**
     * SQL注入检测正则表达式
     */
    String REGEX_SQL_INJECTION = "(?i)\\b(?:select|update|and|or|grant|alter|delete|chr|mid|" +
            "insert|truncate|char|into|substr|ascii|declare|exec|" +
            "count|master|drop|execute)\\b|(\\*|;|\\+|'|%)";

    /**
     * XSS攻击检测正则表达式
     */
    String REGEX_XSS_ATTACK = "<[^>]*script[^>]*>.*?</script>|<[^>]*on\\w+\\s*=|javascript:|vbscript:";

    /**
     * IP地址正则表达式
     */
    String REGEX_IP_ADDRESS = "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

    // ==================== 默认值常量 ====================
    /**
     * 默认端口
     */
    int DEFAULT_PORT = 8080;

    /**
     * 默认超时时间（毫秒）
     */
    long DEFAULT_TIMEOUT = 30000L;

    /**
     * 默认API耗时阈值（毫秒）
     */
    long DEFAULT_API_ELAPSED_THRESHOLD = 1000L;

    // ==================== XSS检测正则表达式常量 ====================
    /**
     * XSS脚本标签检测
     */
    String REGEX_XSS_SCRIPT_TAG = "<script>(.*?)</script>";

    /**
     * XSS src属性检测
     */
    String REGEX_XSS_SRC_ATTR = "src[\\r\\n]*=[\\r\\n]*['\"](.*?)['\"]";

    /**
     * XSS独立脚本标签检测
     */
    String REGEX_XSS_SCRIPT_END_TAG = "</script>";
    String REGEX_XSS_SCRIPT_START_TAG = "<script(.*?)>";

    /**
     * XSS eval函数检测
     */
    String REGEX_XSS_EVAL = "eval\\((.*?)\\)";

    /**
     * XSS expression函数检测
     */
    String REGEX_XSS_EXPRESSION = "expression\\((.*?)\\)";

    /**
     * XSS javascript协议检测
     */
    String REGEX_XSS_JAVASCRIPT = "javascript:";

    /**
     * XSS vbscript协议检测
     */
    String REGEX_XSS_VBSCRIPT = "vbscript:";

    /**
     * XSS onload事件检测
     */
    String REGEX_XSS_ONLOAD = "onload(.*?)=";

    /**
     * XSS alert函数检测
     */
    String REGEX_XSS_ALERT = "alert(.*?)";

    /**
     * XSS HTML标签检测
     */
    String REGEX_XSS_HTML_TAG = "(<(script|iframe|embed|frame|frameset|object|img|applet|body|html|style|layer|link|ilayer|meta|bgsound))";

    String REGEX_XSS_TAG_OPEN = SymbolConstants.LESS_THAN;
    String REGEX_XSS_TAG_CLOSE = SymbolConstants.GREATER_THAN;

    String ANNOTATION_VALUE = "value";

    /**
     * 未知标识
     */
    String UNKNOWN = "unknown";

    // ==================== SubRequestMapping 相关消息 ====================
    /**
     * 多个@RequestMapping注解警告日志
     */
    String LOG_MULTIPLE_REQUEST_MAPPING = "Multiple @RequestMapping annotations found on %s, but only the first will be used: %s";

    /**
     * RequestMapping和HttpExchange同时存在错误
     */
    String ERR_REQUEST_MAPPING_AND_HTTP_EXCHANGE = "%s is annotated with @RequestMapping and @HttpExchange annotations, but only one is allowed: %s";

    /**
     * 多个@HttpExchange注解错误
     */
    String ERR_MULTIPLE_HTTP_EXCHANGE = "Multiple @HttpExchange annotations found on %s, but only one is allowed: %s";

    /**
     * 值提取器不能为空
     */
    String ERR_VALUE_EXTRACTOR_NOT_NULL = "Value extractor must not be null";

}
