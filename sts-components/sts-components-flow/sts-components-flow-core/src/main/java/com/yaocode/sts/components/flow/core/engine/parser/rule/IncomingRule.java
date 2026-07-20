package com.yaocode.sts.components.flow.core.engine.parser.rule;

import com.yaocode.sts.components.flow.core.engine.parser.ParseContext;
import com.yaocode.sts.components.flow.core.model.Incoming;
import com.yaocode.sts.components.flow.core.model.NodeDefinition;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Element;

/**
 * incoming 元素解析规则
 *
 * <p>对应 BPMN 2.0 中的 bpmn:incoming 元素。
 *
 * <p>BPMN 语义：
 * <pre>
 * &lt;bpmn:endEvent id="EndEvent_1"&gt;
 *     &lt;bpmn:incoming&gt;Flow_1yqx94j&lt;/bpmn:incoming&gt;
 * &lt;/bpmn:endEvent&gt;
 * </pre>
 *
 * <p>解析后会在父节点（NodeDefinition）的 incoming 列表中
 * 创建一个 Incoming 对象。
 *
 * @author Process Engine Team
 */
@Slf4j
public class IncomingRule extends AbstractParseRule {

    @Override
    public Object begin(Element element, ParseContext context) {
        String flowId = element.getTextContent().trim();
        if (flowId.isEmpty()) {
            log.trace("incoming 元素内容为空，跳过");
            return null;
        }

        // 获取父节点（应该是 endEvent / userTask / gateway 等）
        Object parent = context.getCurrentParent();
        if (parent instanceof NodeDefinition node) {
            // ✅ 创建 Incoming 对象并添加到父节点
            Incoming incoming = Incoming.builder()
                    .sequenceFlowId(flowId)
                    .targetNodeId(node.getNodeId())
                    .build();

            node.addIncoming(incoming);

            log.trace("添加 incoming 引用: {} <- {}", node.getNodeId(), flowId);
        } else {
            log.warn("incoming 的父节点不是 NodeDefinition，实际类型: {}",
                    parent != null ? parent.getClass().getName() : "null");
        }

        // ✅ 不修改 currentObject，让父级保持
        return null;
    }

    @Override
    public void end(Element element, ParseContext context, Object parent, Object currentObject) {
        // 不需要额外处理
    }

    @Override
    public void setProperty(Element element, ParseContext context,
                            String attributeName, String attributeValue) {
        // incoming 元素没有属性需要处理
    }

    @Override
    public String getRuleName() {
        return "IncomingRule";
    }

    @Override
    public int getPriority() {
        return 40;
    }
}