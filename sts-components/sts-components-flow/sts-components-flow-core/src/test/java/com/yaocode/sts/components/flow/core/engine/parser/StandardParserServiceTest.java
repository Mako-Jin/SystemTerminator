package com.yaocode.sts.components.flow.core.engine.parser;

import com.yaocode.sts.components.flow.core.engine.parser.api.ProcessDefinitionParser;
import com.yaocode.sts.components.flow.core.engine.parser.impl.BpmnProcessParser;
import com.yaocode.sts.components.flow.core.engine.parser.impl.JsonProcessParser;
import com.yaocode.sts.components.flow.core.engine.parser.rule.RuleRegistry;
import com.yaocode.sts.components.flow.core.engine.parser.xml.DomXmlParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 解析服务集成测试
 */
@DisplayName("解析服务集成测试")
class StandardParserServiceTest {

    private StandardParserService parserService;

    @BeforeEach
    void setUp() {
        // 创建BPMN解析器
        BpmnProcessParser bpmnParser = new BpmnProcessParser();
        bpmnParser.setRuleRegistry(new RuleRegistry());
        bpmnParser.setXmlParser(new DomXmlParser());

        // 创建JSON解析器
        JsonProcessParser jsonParser = new JsonProcessParser();

        // 创建解析服务
        List<ProcessDefinitionParser> parsers = List.of(bpmnParser, jsonParser);
        parserService = new StandardParserService(parsers, ParserConfiguration.defaultConfig());
    }

    @Test
    @DisplayName("测试通过解析服务解析BPMN文件")
    void testParseBpmnThroughService() throws IOException {
        try (InputStream inputStream = getClass().getResourceAsStream("/bpmn/start2end.bpmn")) {
            assertNotNull(inputStream, "测试资源文件不存在");

            // 使用解析服务解析
            ParseResult result = parserService.parse(inputStream, "start2end.bpmn");

            // 验证结果
            assertTrue(result.isSuccess(), "解析应成功");
            assertNotNull(result.getProcessDefinition(), "流程定义不能为空");
            assertEquals("Process_0blwuk8", result.getProcessDefinition().getProcessId());
        }
    }

    @Test
    @DisplayName("测试获取支持的格式")
    void testGetSupportedFormats() {
        List<String> formats = parserService.getSupportedFormats();

        assertTrue(formats.contains(".bpmn"), "应支持.bpmn格式");
        assertTrue(formats.contains(".xml"), "应支持.xml格式");
        assertTrue(formats.contains(".json"), "应支持.json格式");
    }

    @Test
    @DisplayName("测试格式支持判断")
    void testSupports() {
        assertTrue(parserService.supports("test.bpmn"), "应支持.bpmn");
        assertTrue(parserService.supports("test.json"), "应支持.json");
        assertFalse(parserService.supports("test.txt"), "不应支持.txt");
    }

    @Test
    @DisplayName("测试获取解析器")
    void testGetParser() {
        ProcessDefinitionParser bpmnParser = parserService.getParser("test.bpmn");
        assertNotNull(bpmnParser, "BPMN解析器不应为空");
        assertEquals("BPMN 2.0 XML Parser", bpmnParser.getParserName());

        ProcessDefinitionParser jsonParser = parserService.getParser("test.json");
        assertNotNull(jsonParser, "JSON解析器不应为空");
        assertEquals("JSON Flow Parser", jsonParser.getParserName());
    }

    @Test
    @DisplayName("测试获取统计信息")
    void testGetStatistics() {
        assertNotNull(parserService.getStatistics(), "统计信息不应为空");
    }

    @Test
    @DisplayName("测试配置获取与更新")
    void testConfiguration() {
        ParserConfiguration config = parserService.getConfiguration();
        assertNotNull(config, "配置不应为空");

        // 更新配置
        ParserConfiguration newConfig = ParserConfiguration.builder()
                .strictMode(true)
                .skipValidation(false)
                .build();
        parserService.updateConfiguration(newConfig);

        assertEquals(newConfig.isStrictMode(), parserService.getConfiguration().isStrictMode());
    }
}