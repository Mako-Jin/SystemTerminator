package com.yaocode.sts.components.flow.core.engine.parser.listener;

import com.yaocode.sts.components.flow.core.engine.parser.ParseContext;
import com.yaocode.sts.components.flow.core.model.NodeDefinition;
import com.yaocode.sts.components.flow.core.model.ProcessDefinition;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * BPMN 解析监听器
 *
 * <p>专门用于 BPMN 解析的事件监听，
 * 提供 BPMN 特定的处理逻辑
 *
 * @author Process Engine Team
 */
@Data
@Slf4j
@EqualsAndHashCode(callSuper = true)
public class BpmnParseListener extends AbstractParseListener {

    /**
     * 收集到的所有流程定义
     */
    private final List<ProcessDefinition> processes = new ArrayList<>();

    /**
     * 当前正在解析的流程
     */
    private ProcessDefinition currentProcess;

    /**
     * 是否记录流程信息
     */
    private boolean logProcessInfo = true;

    public BpmnParseListener() {
        super("BpmnParseListener");
    }

    public BpmnParseListener(boolean logProcessInfo) {
        super("BpmnParseListener");
        this.logProcessInfo = logProcessInfo;
    }

    @Override
    public void parseStarted(ParseContext context) {
        processes.clear();
        currentProcess = null;
        log.debug("BPMN 解析监听器已初始化");
    }

    @Override
    public void parseCompleted(ParseContext context, Object result) {
        // 从上下文获取流程定义
        for (Object obj : context.getDefinitions().values()) {
            if (obj instanceof ProcessDefinition) {
                processes.add((ProcessDefinition) obj);
            }
        }

        if (logProcessInfo && !processes.isEmpty()) {
            for (ProcessDefinition process : processes) {
                log.info("流程定义: id={}, name={}, nodes={}, sequences={}",
                        process.getProcessId(),
                        process.getProcessName(),
                        process.getNodes() != null ? process.getNodes().size() : 0,
                        process.getSequences() != null ? process.getSequences().size() : 0);

                // 打印节点信息
                if (log.isDebugEnabled() && process.getNodes() != null) {
                    for (NodeDefinition node : process.getNodes()) {
                        log.debug("  节点: id={}, type={}, name={}",
                                node.getNodeId(), node.getType(), node.getName());
                    }
                }
            }
        }
    }

    @Override
    public void elementParsed(Element element, ParseContext context, Object parsedObject) {
        String tagName = element.getTagName();

        // 检测流程定义
        if ("process".equals(tagName) && parsedObject instanceof ProcessDefinition) {
            currentProcess = (ProcessDefinition) parsedObject;
            log.debug("检测到流程定义: id={}", currentProcess.getProcessId());
        }

        // 检测流程节点
        if (parsedObject instanceof NodeDefinition node) {
            if (log.isTraceEnabled()) {
                log.trace("节点解析: id={}, type={}, name={}",
                        node.getNodeId(), node.getType(), node.getName());
            }
        }
    }

    @Override
    public void elementParsing(Element element, ParseContext context) {
        String tagName = element.getTagName();

        // 检测开始事件
        if ("startEvent".equals(tagName)) {
            String id = element.getAttribute("id");
            log.debug("检测到开始事件: id={}", id);
        }

        // 检测结束事件
        if ("endEvent".equals(tagName)) {
            String id = element.getAttribute("id");
            log.debug("检测到结束事件: id={}", id);
        }

        // 检测网关
        if (tagName.endsWith("Gateway")) {
            String id = element.getAttribute("id");
            log.debug("检测到网关: id={}, type={}", id, tagName);
        }
    }

    /**
     * 获取所有流程定义
     */
    public List<ProcessDefinition> getProcesses() {
        return new ArrayList<>(processes);
    }

    /**
     * 获取流程数量
     */
    public int getProcessCount() {
        return processes.size();
    }

    @Override
    public String getListenerName() {
        return "BpmnParseListener";
    }
}