package com.yaocode.sts.components.flow.core.engine.parser.rule;

import com.yaocode.sts.components.flow.core.engine.parser.ParseContext;
import com.yaocode.sts.components.flow.core.model.NodeDefinition;
import com.yaocode.sts.components.flow.core.model.Outgoing;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Element;

/**
 * outgoing 元素解析规则
 *
 * <p>对应 BPMN 2.0 中的 bpmn:outgoing 元素。
 *
 * <p>BPMN 语义：
 * <pre>
 * &lt;bpmn:startEvent id="StartEvent_1"&gt;
 *     &lt;bpmn:outgoing&gt;Flow_1yqx94j&lt;/bpmn:outgoing&gt;
 * &lt;/bpmn:startEvent&gt;
 * </pre>
 *
 * <p>解析后会在父节点（NodeDefinition）的 outgoing 列表中
 * 创建一个 Outgoing 对象。
 *
 * @author Process Engine Team
 */
@Slf4j
public class OutgoingRule extends AbstractParseRule {

    @Override
    public Object begin(Element element, ParseContext context) {
        String flowId = element.getTextContent().trim();
        if (flowId.isEmpty()) {
            log.trace("outgoing 元素内容为空，跳过");
            return null;
        }

        // 获取父节点（应该是 startEvent / userTask / gateway 等）
        Object parent = context.getCurrentParent();
        if (parent instanceof NodeDefinition node) {
            // ✅ 创建 Outgoing 对象并添加到父节点
            Outgoing outgoing = Outgoing.builder()
                    .sequenceFlowId(flowId)
                    .sourceNodeId(node.getNodeId())
                    .order(node.getOutgoings().size())
                    .build();

            node.addOutgoing(outgoing);

            log.trace("添加 outgoing 引用: {} -> {} (order={})",
                    node.getNodeId(), flowId, outgoing.getOrder());
        } else {
            log.warn("outgoing 的父节点不是 NodeDefinition，实际类型: {}",
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
        // outgoing 元素没有属性需要处理
    }

    @Override
    public String getRuleName() {
        return "OutgoingRule";
    }

    @Override
    public int getPriority() {
        return 40;
    }
}