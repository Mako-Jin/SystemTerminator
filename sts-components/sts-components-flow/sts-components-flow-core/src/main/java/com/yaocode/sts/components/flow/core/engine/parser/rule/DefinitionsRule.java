package com.yaocode.sts.components.flow.core.engine.parser.rule;

import com.yaocode.sts.components.flow.core.engine.parser.ParseContext;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Element;

/**
 * BPMN definitions 根元素解析规则
 *
 * <p>解析 BPMN 2.0 的根元素 definitions，负责：
 * <ul>
 *   <li>保存命名空间信息</li>
 *   <li>创建流程定义容器</li>
 *   <li>管理子元素（process、import 等）</li>
 * </ul>
 *
 * @author Process Engine Team
 */
@Slf4j
public class DefinitionsRule extends AbstractParseRule {

    @Override
    public Object begin(Element element, ParseContext context) {
        // 记录命名空间
        String targetNamespace = getAttribute(element, "targetNamespace", null);
        if (targetNamespace != null && !targetNamespace.isEmpty()) {
            context.setCurrentNamespace(targetNamespace);
            context.setAttribute("targetNamespace", targetNamespace);
        }

        // 记录其他属性
        String id = getAttribute(element, "id", null);
        if (id != null && !id.isEmpty()) {
            context.setAttribute("definitionsId", id);
        }

        String exporter = getAttribute(element, "exporter", null);
        if (exporter != null && !exporter.isEmpty()) {
            context.setAttribute("exporter", exporter);
        }

        String exporterVersion = getAttribute(element, "exporterVersion", null);
        if (exporterVersion != null && !exporterVersion.isEmpty()) {
            context.setAttribute("exporterVersion", exporterVersion);
        }

        log.debug("开始解析 BPMN definitions: targetNamespace={}, exporter={}",
                targetNamespace, exporter);

        // 返回一个占位对象，实际流程定义由 ProcessRule 创建
        return new DefinitionsHolder(targetNamespace, id);
    }

    @Override
    public void end(Element element, ParseContext context, Object parent, Object currentObject) {
        // definitions 是根元素，没有父对象
        log.debug("BPMN definitions 解析完成");
    }

    @Override
    public void setProperty(Element element, ParseContext context, String attributeName, String attributeValue) {
        // 扩展属性可以存储
        if (attributeName.startsWith("modeler:")) {
            context.setAttribute(attributeName, attributeValue);
        }
    }

    @Override
    public String getRuleName() {
        return "DefinitionsRule";
    }

    @Override
    public int getPriority() {
        return 5; // 最高优先级
    }

    /**
     * Definitions 占位对象
     */
    public record DefinitionsHolder(String targetNamespace, String id) {

    }
}
