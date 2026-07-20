package com.yaocode.sts.components.flow.core.engine.parser.rule;

import com.yaocode.sts.components.flow.core.engine.parser.ParseContext;
import com.yaocode.sts.components.flow.core.engine.parser.error.ParseError;
import com.yaocode.sts.components.flow.core.model.NodeDefinition;
import com.yaocode.sts.components.flow.core.model.ProcessDefinition;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户任务解析规则
 *
 * <p>支持 Camunda 扩展属性：
 * <ul>
 *   <li>assignee - 指定办理人</li>
 *   <li>candidateUsers - 候选人</li>
 *   <li>candidateGroups - 候选组</li>
 *   <li>dueDate - 到期时间</li>
 *   <li>priority - 优先级</li>
 *   <li>formKey - 表单Key</li>
 * </ul>
 *
 * @author Process Engine Team
 */
@Slf4j
public class UserTaskRule extends AbstractParseRule {

    @Override
    public Object begin(Element element, ParseContext context) {
        String nodeId = getAttribute(element, "id", null);
        if (nodeId == null || nodeId.isEmpty()) {
            nodeId = "userTask_" + System.currentTimeMillis();
            context.addError(ParseError.of("用户任务缺少 id 属性，使用自动生成ID: " + nodeId, element));
        }

        NodeDefinition nodeDefinition = NodeDefinition.builder()
                .nodeId(nodeId)
                .name(getAttribute(element, "name", nodeId))
                .type("userTask")
                .behaviorType("userTask")
                .build();

        // 解析 Camunda 扩展属性
        String assignee = getAttribute(element, "camunda:assignee", null);
        if (assignee != null && !assignee.isEmpty()) {
            nodeDefinition.addProperty("assignee", assignee);
        }

        String candidateUsers = getAttribute(element, "camunda:candidateUsers", null);
        if (candidateUsers != null && !candidateUsers.isEmpty()) {
            nodeDefinition.addProperty("candidateUsers", candidateUsers);
            // 解析为列表
            List<String> userList = new ArrayList<>();
            for (String user : candidateUsers.split(",")) {
                userList.add(user.trim());
            }
            nodeDefinition.addProperty("candidateUserList", userList);
        }

        String candidateGroups = getAttribute(element, "camunda:candidateGroups", null);
        if (candidateGroups != null && !candidateGroups.isEmpty()) {
            nodeDefinition.addProperty("candidateGroups", candidateGroups);
            List<String> groupList = new ArrayList<>();
            for (String group : candidateGroups.split(",")) {
                groupList.add(group.trim());
            }
            nodeDefinition.addProperty("candidateGroupList", groupList);
        }

        String dueDate = getAttribute(element, "camunda:dueDate", null);
        if (dueDate != null && !dueDate.isEmpty()) {
            nodeDefinition.addProperty("dueDate", dueDate);
        }

        String priority = getAttribute(element, "camunda:priority", null);
        if (priority != null && !priority.isEmpty()) {
            nodeDefinition.addProperty("priority", priority);
        }

        String formKey = getAttribute(element, "camunda:formKey", null);
        if (formKey != null && !formKey.isEmpty()) {
            nodeDefinition.addProperty("formKey", formKey);
        }

        String formRef = getAttribute(element, "camunda:formRef", null);
        if (formRef != null && !formRef.isEmpty()) {
            nodeDefinition.addProperty("formRef", formRef);
        }

        String taskListener = getAttribute(element, "camunda:taskListener", null);
        if (taskListener != null && !taskListener.isEmpty()) {
            nodeDefinition.addProperty("taskListener", taskListener);
        }

        context.addElementId(nodeId);

        log.debug("开始解析用户任务: id={}, name={}", nodeId, nodeDefinition.getName());
        return nodeDefinition;
    }

    @Override
    public void end(Element element, ParseContext context, Object parent, Object currentObject) {
        NodeDefinition node = (NodeDefinition) currentObject;

        // 解析子元素
        // 1. 解析 documentation
        Element doc = findChild(element, "documentation");
        if (doc != null) {
            node.setDocumentation(getTextContent(doc));
        }

        // 2. 解析 extensionElements
        Element extElements = findChild(element, "extensionElements");
        if (extElements != null) {
            parseExtensionElements(extElements, node);
        }

        // 3. 解析 inputOutput（如果存在）
        Element ioMapping = findChild(element, "inputOutput");
        if (ioMapping != null) {
            // 已由 InputOutputRule 处理
        }

        // 添加到父流程
        if (parent instanceof ProcessDefinition process) {
            if (process.getNodes() == null) {
                process.setNodes(new java.util.ArrayList<>());
            }
            process.getNodes().add(node);
        }

        log.debug("用户任务解析完成: id={}", node.getNodeId());
    }

    private void parseExtensionElements(Element extElements, NodeDefinition node) {
        // 解析 Camunda 扩展元素
        Element properties = findChild(extElements, "properties");
        if (properties != null) {
            List<Element> propertyList = findChildren(properties, "property");
            for (Element prop : propertyList) {
                String name = prop.getAttribute("name");
                String value = prop.getAttribute("value");
                if (!name.isEmpty()) {
                    node.addProperty("prop_" + name, value);
                }
            }
        }
    }

    @Override
    public void setProperty(Element element, ParseContext context,
                            String attributeName, String attributeValue) {
        NodeDefinition node = (NodeDefinition) context.getCurrentObject();

        // 忽略已处理的标准属性
        if ("id".equals(attributeName) || "name".equals(attributeName) ||
                "camunda:assignee".equals(attributeName) ||
                "camunda:candidateUsers".equals(attributeName) ||
                "camunda:candidateGroups".equals(attributeName) ||
                "camunda:dueDate".equals(attributeName) ||
                "camunda:priority".equals(attributeName) ||
                "camunda:formKey".equals(attributeName) ||
                "camunda:formRef".equals(attributeName)) {
            return;
        }

        node.addProperty(attributeName, attributeValue);
    }

    @Override
    public String getRuleName() {
        return "UserTaskRule";
    }

    @Override
    public int getPriority() {
        return 30;
    }
}
