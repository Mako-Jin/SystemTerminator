package com.yaocode.sts.common.tools.constants;

import com.yaocode.sts.common.basic.constants.BasicI18nKeyConstants;

/**
 * 工具模块i18n键常量类
 * @author: Jin-LiangBo
 * @date: 2026年07月04日
 */
public interface ToolsI18nKeyConstants extends BasicI18nKeyConstants {

    // ==================== ID生成器 ====================
    String ID_GENERATOR_TYPE_UUID = "tools.id.generator.type.uuid";
    String ID_GENERATOR_TYPE_SNOWFLAKE = "tools.id.generator.type.snowflake";
    String ID_GENERATOR_NAME_UUID = "tools.id.generator.name.uuid";
    String ID_GENERATOR_NAME_SNOWFLAKE = "tools.id.generator.name.snowflake";
    String ERR_ID_GENERATOR_NOT_FOUND = "tools.id.generator.error.not_found";

    // ==================== 异常消息 ====================
    String ERR_EMPTY_VALUE = "tools.exception.empty_value";
    String ERR_OUT_OF_RANGE = "tools.exception.out_of_range";

    // ==================== JSON工具类异常 ====================
    String ERR_JSON_CONVERT_TO_STRING = "tools.json.error.convert_to_string";
    String ERR_JSON_CONVERT_TO_PRETTY = "tools.json.error.convert_to_pretty";
    String ERR_JSON_PARSE_TO_OBJECT = "tools.json.error.parse_to_object";
    String ERR_JSON_PARSE_TO_OBJECT_TYPE_REF = "tools.json.error.parse_to_object_typeref";
    String ERR_JSON_PARSE_TO_LIST = "tools.json.error.parse_to_list";
    String ERR_JSON_PARSE_TO_MAP = "tools.json.error.parse_to_map";
    String ERR_JSON_CONVERT_TO_JSON_OBJECT = "tools.json.error.convert_to_jsonobject";
    String ERR_JSON_CONVERT_TO_JSON_ARRAY = "tools.json.error.convert_to_jsonarray";
    String ERR_JSON_GET_VALUE_BY_PATH = "tools.json.error.get_value_by_path";

    // ==================== JSON提供者名称 ====================
    String JSON_PROVIDER_JACKSON = "tools.json.provider.jackson";
    String JSON_PROVIDER_FASTJSON2 = "tools.json.provider.fastjson2";
}
