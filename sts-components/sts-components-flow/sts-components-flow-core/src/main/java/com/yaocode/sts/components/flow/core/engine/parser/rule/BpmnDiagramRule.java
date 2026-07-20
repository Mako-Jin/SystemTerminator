package com.yaocode.sts.components.flow.core.engine.parser.rule;

import com.yaocode.sts.components.flow.core.engine.parser.ParseContext;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Element;

/**
 * BPMN DI 图表解析规则（仅用于忽略，不处理任何内容）
 *
 * <p>BPMN DI 是图形信息，对流程执行没有影响，所以直接忽略。
 */
@Slf4j
public class BpmnDiagramRule extends AbstractParseRule {

    @Override
    public Object begin(Element element, ParseContext context) {
        // 忽略 BPMN DI，不创建任何对象
        log.trace("忽略 BPMN DI 元素: {}", element.getTagName());
        return null;
    }

    @Override
    public void end(Element element, ParseContext context, Object parent, Object currentObject) {
        // 什么都不做
    }

    @Override
    public void setProperty(Element element, ParseContext context,
                            String attributeName, String attributeValue) {
        // 什么都不做
    }

    @Override
    public String getRuleName() {
        return "BpmnDiagramRule";
    }

    @Override
    public int getPriority() {
        return 100;  // 低优先级
    }
}
