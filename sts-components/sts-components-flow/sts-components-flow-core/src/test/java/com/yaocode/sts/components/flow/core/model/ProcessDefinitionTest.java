package com.yaocode.sts.components.flow.core.model;

import com.yaocode.sts.components.flow.core.enums.NodeTypeEnums;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 流程定义模型测试
 */
@DisplayName("流程定义模型测试")
class ProcessDefinitionTest {

    @Test
    @DisplayName("测试流程定义构建")
    void testProcessDefinitionBuilder() {
        // 构建流程定义
        ProcessDefinition processDefinition = ProcessDefinition.builder()
                .processId("test-process")
                .processName("测试流程")
                .description("测试流程描述")
                .category("测试")
                .startable(true)
                .build();
        processDefinition.addNode(NodeDefinition.builder()
                        .nodeId("start")
                        .name("开始")
                        .type("startEvent")
                        .build());
        processDefinition.addNode(NodeDefinition.builder()
                        .nodeId("task")
                        .name("任务")
                        .type("userTask")
                        .build());
        processDefinition.addSequence(SequenceDefinition.builder()
                        .sequenceId("flow1")
                        .sourceRef("start")
                        .targetRef("task")
                        .build());


        // 验证构建结果
        assertEquals("test-process", processDefinition.getProcessId());
        assertEquals("测试流程", processDefinition.getProcessName());
        assertEquals("测试流程描述", processDefinition.getDescription());
        assertEquals("测试", processDefinition.getCategory());
        assertTrue(processDefinition.isStartable());

        // 验证节点
        assertNotNull(processDefinition.getNodes());
        assertEquals(2, processDefinition.getNodes().size());

        // 验证连线
        assertNotNull(processDefinition.getSequences());
        assertEquals(1, processDefinition.getSequences().size());
    }

    @Test
    @DisplayName("测试获取开始节点")
    void testGetStartNode() {
        ProcessDefinition processDefinition = ProcessDefinition.builder().build();
        processDefinition.addNode(NodeDefinition.builder()
                        .nodeId("start")
                        .type("startEvent")
                        .build());
        processDefinition.addNode(NodeDefinition.builder()
                        .nodeId("task")
                        .type("userTask")
                        .build());


        NodeDefinition startNode = processDefinition.getStartNode();
        assertNotNull(startNode);
        assertEquals("start", startNode.getNodeId());
        assertEquals("startEvent", startNode.getType());
    }

    @Test
    @DisplayName("测试获取结束节点")
    void testGetEndNodes() {
        ProcessDefinition processDefinition = ProcessDefinition.builder().build();
        processDefinition.addNode(NodeDefinition.builder()
                        .nodeId("end1")
                        .type("endEvent")
                        .build());
        processDefinition.addNode(NodeDefinition.builder()
                        .nodeId("end2")
                        .type("endEvent")
                        .build());
        processDefinition.addNode(NodeDefinition.builder()
                        .nodeId("task")
                        .type("userTask")
                        .build());


        List<NodeDefinition> endNodes = processDefinition.getEndNodes();
        assertNotNull(endNodes);
        assertEquals(2, endNodes.size());
        assertTrue(endNodes.stream().allMatch(n -> "endEvent".equals(n.getType())));
    }

    @Test
    @DisplayName("测试按类型获取节点")
    void testGetNodesByType() {
        ProcessDefinition processDefinition = ProcessDefinition.builder().build();
        processDefinition.addNode(NodeDefinition.builder().nodeId("user1").type("userTask").build());
        processDefinition.addNode(NodeDefinition.builder().nodeId("user2").type("userTask").build());
        processDefinition.addNode(NodeDefinition.builder().nodeId("service1").type("serviceTask").build());


        List<NodeDefinition> userTasks = processDefinition.getNodesByType(NodeTypeEnums.USER_TASK);
        assertEquals(2, userTasks.size());

        List<NodeDefinition> serviceTasks = processDefinition.getNodesByType(NodeTypeEnums.SERVICE_TASK);
        assertEquals(1, serviceTasks.size());

    }

    @Test
    @DisplayName("测试获取用户任务")
    void testGetUserTasks() {
        ProcessDefinition processDefinition = ProcessDefinition.builder().build();
        processDefinition.addNode(NodeDefinition.builder().nodeId("user1").type("userTask").build());
        processDefinition.addNode(NodeDefinition.builder().nodeId("user2").type("userTask").build());
        processDefinition.addNode(NodeDefinition.builder().nodeId("service1").type("serviceTask").build());


        List<NodeDefinition> userTasks = processDefinition.getUserTasks();
        assertEquals(2, userTasks.size());
    }

    @Test
    @DisplayName("测试获取服务任务")
    void testGetServiceTasks() {
        ProcessDefinition processDefinition = ProcessDefinition.builder().build();
        processDefinition.addNode(NodeDefinition.builder().nodeId("user1").type("userTask").build());
        processDefinition.addNode(NodeDefinition.builder().nodeId("service1").type("serviceTask").build());
        processDefinition.addNode(NodeDefinition.builder().nodeId("service2").type("serviceTask").build());


        List<NodeDefinition> serviceTasks = processDefinition.getServiceTasks();
        assertEquals(2, serviceTasks.size());
    }

    @Test
    @DisplayName("测试空流程定义")
    void testEmptyProcessDefinition() {
        ProcessDefinition processDefinition = ProcessDefinition.builder().build();

        assertNull(processDefinition.getProcessId());
        assertNull(processDefinition.getProcessName());
        assertNotNull(processDefinition.getNodes());
        assertTrue(processDefinition.getNodes().isEmpty());
        assertNotNull(processDefinition.getSequences());
        assertTrue(processDefinition.getSequences().isEmpty());
    }
}
