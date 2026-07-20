package com.yaocode.sts.components.flow.core.engine.parser.rule;

import com.yaocode.sts.components.flow.core.engine.parser.ParseContext;
import org.w3c.dom.Element;

/**
 * 解析规则接口
 *
 * <p>定义了解析 BPMN 元素的标准流程：
 * <ol>
 *   <li>{@link #begin} - 开始解析元素，创建对象</li>
 *   <li>{@link #setProperty} - 设置元素属性</li>
 *   <li>{@link #end} - 解析完成，处理父子关系</li>
 * </ol>
 *
 * @author Process Engine Team
 */
public interface ParseRule {

    /**
     * 开始解析元素
     * <p>在解析元素开始时调用，负责创建对应的业务对象
     *
     * @param element XML 元素
     * @param context 解析上下文
     * @return 创建的对象
     */
    Object begin(Element element, ParseContext context);

    /**
     * 解析子元素完成后调用
     * <p>在元素的所有子元素解析完成后调用，负责处理父子关系
     *
     * @param element XML 元素
     * @param context 解析上下文
     * @param parent  父对象
     */
    void end(Element element, ParseContext context, Object parent, Object currentObject);

    /**
     * 设置属性
     * <p>在解析元素属性时调用
     *
     * @param element        XML 元素
     * @param context        解析上下文
     * @param attributeName  属性名
     * @param attributeValue 属性值
     */
    void setProperty(Element element, ParseContext context, String attributeName, String attributeValue);

    /**
     * 获取规则名称（用于日志和调试）
     *
     * @return 规则名称
     */
    default String getRuleName() {
        return getClass().getSimpleName();
    }

    /**
     * 获取规则优先级（数值越小优先级越高）
     *
     * @return 优先级
     */
    default int getPriority() {
        return 100;
    }
}
