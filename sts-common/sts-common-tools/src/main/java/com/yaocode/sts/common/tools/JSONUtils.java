package com.yaocode.sts.common.tools;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.yaocode.sts.common.tools.constants.ToolsI18nKeyConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * JSON 工具类，兼容 jackson 和 fastjson2
 * <p>
 * 默认使用 jackson 作为序列化工具，可通过 setJsonProvider 方法切换
 * </p>
 *
 * @author yaocode
 * @since 0.0.1
 */
public class JSONUtils {

    private static final Logger logger = LoggerFactory.getLogger(JSONUtils.class);

    /**
     * JSON 序列化提供者枚举
     */
    public enum JsonProvider {
        JACKSON, FASTJSON2
    }

    /**
     * 当前使用的 JSON 提供者
     */
    private static volatile JsonProvider currentProvider = JsonProvider.JACKSON;

    /**
     * Jackson ObjectMapper 实例
     */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(SerializationFeature.INDENT_OUTPUT, true)
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    /**
     * 私有化构造器
     */
    private JSONUtils() {
    }

    /**
     * 设置 JSON 提供者
     *
     * @param provider JSON 提供者
     */
    public static void setJsonProvider(JsonProvider provider) {
        currentProvider = provider;
        logger.info("JSON provider switched to: {}", provider);
    }

    /**
     * 获取当前 JSON 提供者
     *
     * @return 当前 JSON 提供者
     */
    public static JsonProvider getJsonProvider() {
        return currentProvider;
    }

    // ==================== 对象转 JSON 字符串 ====================

    /**
     * 将对象转换为 JSON 字符串
     *
     * @param obj 对象
     * @return JSON 字符串
     */
    public static String toJson(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            if (currentProvider == JsonProvider.JACKSON) {
                return OBJECT_MAPPER.writeValueAsString(obj);
            } else {
                return JSON.toJSONString(obj);
            }
        } catch (Exception e) {
            logger.error("Failed to convert object to JSON", e);
            throw new RuntimeException(ToolsI18nKeyConstants.ERR_JSON_CONVERT_TO_STRING, e);
        }
    }

    /**
     * 将对象转换为格式化的 JSON 字符串
     *
     * @param obj 对象
     * @return 格式化的 JSON 字符串
     */
    public static String toJsonPretty(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            if (currentProvider == JsonProvider.JACKSON) {
                return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
            } else {
                return JSON.toJSONString(obj);
            }
        } catch (Exception e) {
            logger.error("Failed to convert object to pretty JSON", e);
            throw new RuntimeException(ToolsI18nKeyConstants.ERR_JSON_CONVERT_TO_PRETTY, e);
        }
    }

    // ==================== JSON 字符串转对象 ====================

    /**
     * 将 JSON 字符串转换为指定类型的对象
     *
     * @param json  JSON 字符串
     * @param clazz 目标类型
     * @param <T>   泛型类型
     * @return 指定类型的对象
     */
    public static <T> T parseObject(String json, Class<T> clazz) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            if (currentProvider == JsonProvider.JACKSON) {
                return OBJECT_MAPPER.readValue(json, clazz);
            } else {
                return JSON.parseObject(json, clazz);
            }
        } catch (Exception e) {
            logger.error("Failed to parse JSON to object", e);
            throw new RuntimeException(ToolsI18nKeyConstants.ERR_JSON_PARSE_TO_OBJECT, e);
        }
    }

    /**
     * 将 JSON 字符串转换为指定类型的对象（支持复杂泛型）
     *
     * @param json          JSON 字符串
     * @param typeReference 类型引用
     * @param <T>           泛型类型
     * @return 指定类型的对象
     */
    public static <T> T parseObject(String json, TypeReference<T> typeReference) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            if (currentProvider == JsonProvider.JACKSON) {
                return OBJECT_MAPPER.readValue(json, typeReference);
            } else {
                return JSON.parseObject(json, typeReference.getType());
            }
        } catch (Exception e) {
            logger.error("Failed to parse JSON to object with TypeReference", e);
            throw new RuntimeException(ToolsI18nKeyConstants.ERR_JSON_PARSE_TO_OBJECT_TYPE_REF, e);
        }
    }

    // ==================== JSON 字符串转 List ====================

    /**
     * 将 JSON 字符串转换为 List
     *
     * @param json  JSON 字符串
     * @param clazz 元素类型
     * @param <T>   泛型类型
     * @return List 对象
     */
    public static <T> List<T> parseArray(String json, Class<T> clazz) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            if (currentProvider == JsonProvider.JACKSON) {
                return OBJECT_MAPPER.readValue(json, OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            } else {
                return JSON.parseArray(json, clazz);
            }
        } catch (Exception e) {
            logger.error("Failed to parse JSON to List", e);
            throw new RuntimeException(ToolsI18nKeyConstants.ERR_JSON_PARSE_TO_LIST, e);
        }
    }

    // ==================== JSON 字符串转 Map ====================

    /**
     * 将 JSON 字符串转换为 Map
     *
     * @param json JSON 字符串
     * @return Map 对象
     */
    public static Map<String, Object> parseMap(String json) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            if (currentProvider == JsonProvider.JACKSON) {
                return OBJECT_MAPPER.readValue(json, new TypeReference<Map<String, Object>>() {});
            } else {
                return JSON.parseObject(json, new TypeReference<Map<String, Object>>() {}.getType());
            }
        } catch (Exception e) {
            logger.error("Failed to parse JSON to Map", e);
            throw new RuntimeException(ToolsI18nKeyConstants.ERR_JSON_PARSE_TO_MAP, e);
        }
    }

    // ==================== 通用转换方法 ====================

    /**
     * 将对象转换为指定类型
     *
     * @param from 源对象
     * @param to   目标类型
     * @param <T>  泛型类型
     * @return 目标类型对象
     */
    public static <T> T convert(Object from, Class<T> to) {
        if (from == null) {
            return null;
        }
        // 如果已经是目标类型，直接返回
        if (to.isInstance(from)) {
            return to.cast(from);
        }
        // 通过 JSON 中转实现类型转换
        String json = toJson(from);
        return parseObject(json, to);
    }

    // ==================== 验证方法 ====================

    /**
     * 检查字符串是否为有效的 JSON
     *
     * @param str 字符串
     * @return 是否为有效 JSON
     */
    public static boolean isValidJson(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        try {
            if (currentProvider == JsonProvider.JACKSON) {
                OBJECT_MAPPER.readTree(str);
            } else {
                JSON.parse(str);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ==================== Fastjson2 特有方法 ====================

    /**
     * 将对象转换为 JSONObject（仅 fastjson2）
     *
     * @param obj 对象
     * @return JSONObject
     */
    public static JSONObject toJSONObject(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return (JSONObject) JSON.toJSON(obj);
        } catch (Exception e) {
            logger.error("Failed to convert object to JSONObject", e);
            throw new RuntimeException(ToolsI18nKeyConstants.ERR_JSON_CONVERT_TO_JSON_OBJECT, e);
        }
    }

    /**
     * 将对象转换为 JSONArray（仅 fastjson2）
     *
     * @param obj 对象
     * @return JSONArray
     */
    public static JSONArray toJSONArray(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return (JSONArray) JSON.toJSON(obj);
        } catch (Exception e) {
            logger.error("Failed to convert object to JSONArray", e);
            throw new RuntimeException(ToolsI18nKeyConstants.ERR_JSON_CONVERT_TO_JSON_ARRAY, e);
        }
    }

    /**
     * 从 JSON 字符串获取指定路径的值
     *
     * @param json  JSON 字符串
     * @param path  路径，如 "data.items[0].name"
     * @param clazz 返回值类型
     * @param <T>   泛型类型
     * @return 指定路径的值
     */
    public static <T> T getValueByPath(String json, String path, Class<T> clazz) {
        if (StringUtils.isBlank(json) || StringUtils.isBlank(path)) {
            return null;
        }
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            return jsonObject.getObject(path, clazz);
        } catch (Exception e) {
            logger.error("Failed to get value by path", e);
            throw new RuntimeException(ToolsI18nKeyConstants.ERR_JSON_GET_VALUE_BY_PATH, e);
        }
    }

    // ==================== Jackson 特有方法 ====================

    /**
     * 获取 Jackson ObjectMapper 实例
     *
     * @return ObjectMapper 实例
     */
    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }
}