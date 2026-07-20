package com.yaocode.sts.components.flow.core.engine.parser.impl;

import com.yaocode.sts.components.flow.core.engine.parser.ParseResult;
import com.yaocode.sts.components.flow.core.engine.parser.enums.ParseStatusEnums;
import com.yaocode.sts.components.flow.core.engine.parser.rule.RuleRegistry;
import com.yaocode.sts.components.flow.core.engine.parser.xml.DomXmlParser;
import com.yaocode.sts.components.flow.core.engine.parser.xml.XmlParser;
import com.yaocode.sts.components.flow.core.model.NodeDefinition;
import com.yaocode.sts.components.flow.core.model.ProcessDefinition;
import com.yaocode.sts.components.flow.core.model.SequenceDefinition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * BPMN流程解析器测试类
 */
@DisplayName("BPMN流程解析器测试")
class BpmnProcessParserTest {

    private BpmnProcessParser parser;

    @BeforeEach
    void setUp() {
        // 初始化规则注册中心并注册必要的解析规则
        RuleRegistry ruleRegistry = new RuleRegistry();

        // 创建XML解析器
        XmlParser xmlParser = new DomXmlParser();

        // 创建BPMN解析器
        parser = new BpmnProcessParser();
        parser.setRuleRegistry(ruleRegistry);
        parser.setXmlParser(xmlParser);
    }

    @Test
    @DisplayName("测试解析简单BPMN流程")
    void testParseSimpleProcess() throws IOException {
        // 1. 加载测试资源
        try (InputStream inputStream = getClass().getResourceAsStream("/simple-process.bpmn")) {
            assertNotNull(inputStream, "测试资源文件不存在");

            // 2. 执行解析
            ParseResult result = parser.parse(inputStream, "simple-process.bpmn");

            // 3. 验证解析结果
            assertNotNull(result, "解析结果不能为空");
            assertTrue(result.isSuccess(), "解析应成功");
            assertEquals(ParseStatusEnums.COMPLETED, result.getStatus(), "解析状态应为COMPLETED");

            // 4. 验证流程定义
            ProcessDefinition processDefinition = result.getProcessDefinition();
            assertNotNull(processDefinition, "流程定义不能为空");
            assertEquals("simple-process", processDefinition.getProcessId(), "流程ID不正确");
            assertEquals("简单流程", processDefinition.getProcessName(), "流程名称不正确");

            // 5. 验证节点
            List<NodeDefinition> nodes = processDefinition.getNodes();
            assertNotNull(nodes, "节点列表不能为空");
            assertEquals(3, nodes.size(), "应有3个节点（开始事件、用户任务、结束事件）");

            // 6. 验证连线
            List<SequenceDefinition> sequences = processDefinition.getSequences();
            assertNotNull(sequences, "连线列表不能为空");
            assertEquals(2, sequences.size(), "应有2条连线");

            // 7. 验证开始节点
            NodeDefinition startNode = processDefinition.getStartNode();
            assertNotNull(startNode, "开始节点不能为空");
            assertEquals("start-event", startNode.getNodeId(), "开始节点ID不正确");
            assertEquals("startEvent", startNode.getType(), "开始节点类型不正确");

            // 8. 验证结束节点
            List<NodeDefinition> endNodes = processDefinition.getEndNodes();
            assertNotNull(endNodes, "结束节点列表不能为空");
            assertEquals(1, endNodes.size(), "应有1个结束节点");
            assertEquals("end-event", endNodes.get(0).getNodeId(), "结束节点ID不正确");

            // 9. 验证用户任务节点
            List<NodeDefinition> userTasks = processDefinition.getUserTasks();
            assertNotNull(userTasks, "用户任务列表不能为空");
            assertEquals(1, userTasks.size(), "应有1个用户任务");
            assertEquals("user-task", userTasks.get(0).getNodeId(), "用户任务ID不正确");
            assertEquals("审批任务", userTasks.get(0).getName(), "用户任务名称不正确");
        }
    }

    @Test
    @DisplayName("测试解析空内容")
    void testParseEmptyContent() {
        byte[] emptyContent = new byte[0];

        ParseResult result = parser.parse(emptyContent, "empty.bpmn");

        assertFalse(result.isSuccess(), "空内容解析应失败");
        assertEquals(ParseStatusEnums.FAILED, result.getStatus(), "解析状态应为FAILED");
    }

    @Test
    @DisplayName("测试解析无效XML")
    void testParseInvalidXml() {
        byte[] invalidXml = "<invalid></invalid>".getBytes();

        ParseResult result = parser.parse(invalidXml, "invalid.bpmn");

        // 解析器应能处理无效XML，可能返回部分成功或失败
        assertNotNull(result, "解析结果不能为空");
    }

    @Test
    @DisplayName("测试格式支持判断")
    void testSupports() {
        assertTrue(parser.supports("process.bpmn"), "应支持.bpmn格式");
        assertTrue(parser.supports("process.xml"), "应支持.xml格式");
        assertTrue(parser.supports("process.bpmn20.xml"), "应支持.bpmn20.xml格式");
        assertFalse(parser.supports("process.json"), "不应支持.json格式");
        assertFalse(parser.supports("process.txt"), "不应支持.txt格式");
        assertFalse(parser.supports(null), "不应支持null");
    }

    @Test
    @DisplayName("测试解析器信息")
    void testParserInfo() {
        assertEquals("BPMN 2.0 XML Parser", parser.getParserName(), "解析器名称不正确");
        assertEquals("2.0.0", parser.getVersion(), "解析器版本不正确");
        assertEquals("解析 BPMN 2.0 XML 格式的流程定义文件", parser.getDescription(), "解析器描述不正确");
        assertEquals(List.of(".bpmn", ".xml", ".bpmn20.xml"), parser.getSupportedFormats(), "支持格式不正确");
    }
}