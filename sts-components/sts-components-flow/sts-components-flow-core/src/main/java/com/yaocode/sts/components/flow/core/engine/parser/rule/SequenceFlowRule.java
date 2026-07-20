package com.yaocode.sts.components.flow.core.engine.parser.rule;

import com.yaocode.sts.components.flow.core.engine.parser.ParseContext;
import com.yaocode.sts.components.flow.core.engine.parser.error.ParseError;
import com.yaocode.sts.components.flow.core.model.ProcessDefinition;
import com.yaocode.sts.components.flow.core.model.SequenceDefinition;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Element;

/**
 * 序列流解析规则
 *
 * <p>解析 BPMN 的 sequenceFlow 元素，支持条件表达式
 *
 * @author Process Engine Team
 */
@Slf4j
public class SequenceFlowRule extends AbstractParseRule {

    @Override
    public Object begin(Element element, ParseContext context) {
        String sequenceId = getAttribute(element, "id", null);
        if (sequenceId == null || sequenceId.isEmpty()) {
            sequenceId = "sequenceFlow_" + System.currentTimeMillis();
            context.addError(ParseError.of("序列流缺少 id 属性，使用自动生成ID: " + sequenceId, element));
        }

        String sourceRef = getAttribute(element, "sourceRef", null);
        String targetRef = getAttribute(element, "targetRef", null);

        if (sourceRef == null || sourceRef.isEmpty()) {
            context.addError(ParseError.of("序列流缺少 sourceRef 属性: " + sequenceId, element));
        }
        if (targetRef == null || targetRef.isEmpty()) {
            context.addError(ParseError.of("序列流缺少 targetRef 属性: " + sequenceId, element));
        }

        SequenceDefinition.SequenceDefinitionBuilder builder = SequenceDefinition.builder()
                .sequenceId(sequenceId)
                .name(getAttribute(element, "name", sequenceId))
                .sourceRef(sourceRef)
                .targetRef(targetRef);

        SequenceDefinition sequence = builder.build();

        context.addElementId(sequenceId);

        log.debug("开始解析序列流: id={}, source={}, target={}", sequenceId, sourceRef, targetRef);
        return sequence;
    }

    @Override
    public void end(Element element, ParseContext context, Object parent, Object currentObject) {
        SequenceDefinition sequence = (SequenceDefinition) currentObject;

        // 解析条件表达式
        parseConditionExpression(element, sequence);

        // 添加到父流程
        if (parent instanceof ProcessDefinition process) {
            if (process.getSequences() == null) {
                process.setSequences(new java.util.ArrayList<>());
            }
            process.getSequences().add(sequence);
        }

        log.debug("序列流解析完成: id={}", sequence.getSequenceId());
    }

    private void parseConditionExpression(Element element, SequenceDefinition sequence) {
        // 查找 conditionExpression 子元素
        Element conditionElement = findChild(element, "conditionExpression");
        if (conditionElement != null) {
            String condition = getTextContent(conditionElement);
            if (condition != null && !condition.isEmpty()) {
                sequence.setConditionExpression(condition);

                // 检查条件类型
                String type = conditionElement.getAttribute("type");
                if (!type.isEmpty()) {
                    sequence.addProperty("conditionType", type);
                }

                // 检查是否使用脚本语言
                String language = conditionElement.getAttribute("language");
                if (!language.isEmpty()) {
                    sequence.addProperty("conditionLanguage", language);
                }
            }
        }
    }

    @Override
    public void setProperty(Element element, ParseContext context, String attributeName, String attributeValue) {
        SequenceDefinition sequence = (SequenceDefinition) context.getCurrentObject();

        // 忽略已处理的标准属性
        if ("id".equals(attributeName) || "name".equals(attributeName) ||
                "sourceRef".equals(attributeName) || "targetRef".equals(attributeName)) {
            return;
        }

        sequence.addProperty(attributeName, attributeValue);
    }

    @Override
    public String getRuleName() {
        return "SequenceFlowRule";
    }

    @Override
    public int getPriority() {
        return 30;
    }
}
