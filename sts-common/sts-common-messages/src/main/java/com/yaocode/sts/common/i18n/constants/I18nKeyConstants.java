package com.yaocode.sts.common.i18n.constants;

import com.yaocode.sts.common.basic.constants.BasicI18nKeyConstants;
import com.yaocode.sts.common.basic.constants.PathConstants;

/**
 * 国际化消息key
 * @author: Jin-LiangBo
 * @date: 2026年04月21日 15:13
 */
public interface I18nKeyConstants extends BasicI18nKeyConstants {

    // ==================== 配置前缀 ====================
    /**
     * 国际化配置前缀
     */
    String CONFIG_PREFIX = "yaocode.messages.i18n";

    /**
     * 是否启用配置项
     */
    String CONFIG_ENABLED = CONFIG_PREFIX + ".enabled";

    // ==================== 默认值 ====================
    /**
     * 默认降级消息文件基础名称
     */
    String DEFAULT_BASENAME = "messages";

    /**
     * 默认编码
     */
    String DEFAULT_ENCODING = "UTF-8";

    // ==================== 路径常量 ====================
    /**
     * i18n模块扫描路径模式
     */
    String I18N_MODULE_SCAN_PATTERN = PathConstants.CLASSPATH_ALL_PREFIX + "i18n/*/messages.properties";

    /**
     * i18n目录前缀
     */
    String I18N_DIR_PREFIX = "i18n/";

    // ==================== 数据库相关 ====================
    /**
     * 国际化资源表名
     */
    String TABLE_I18N_RESOURCE = "aux_i18n_resource";

}
