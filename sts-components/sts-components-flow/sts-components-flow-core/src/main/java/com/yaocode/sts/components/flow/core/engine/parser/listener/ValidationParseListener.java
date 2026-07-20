package com.yaocode.sts.components.flow.core.engine.parser.listener;

import com.yaocode.sts.components.flow.core.engine.parser.ParseContext;
import com.yaocode.sts.components.flow.core.engine.parser.error.ParseError;
import com.yaocode.sts.components.flow.core.engine.parser.error.ParseWarning;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 验证监听器
 *
 * <p>在解析过程中收集和记录验证错误/警告，
 * 并提供验证报告生成功能
 *
 * @author Process Engine Team
 */
@Data
@Slf4j
@EqualsAndHashCode(callSuper = true)
public class ValidationParseListener extends AbstractParseListener {

    /**
     * 收集到的错误
     */
    private final List<ParseError> collectedErrors = new ArrayList<>();

    /**
     * 收集到的警告
     */
    private final List<ParseWarning> collectedWarnings = new ArrayList<>();

    /**
     * 是否在发现错误时立即记录
     */
    private boolean immediateLogging = true;

    /**
     * 是否生成详细报告
     */
    private boolean detailedReport = false;

    public ValidationParseListener() {
        super("ValidationParseListener");
    }

    public ValidationParseListener(boolean immediateLogging) {
        super("ValidationParseListener");
        this.immediateLogging = immediateLogging;
    }

    @Override
    public void parseStarted(ParseContext context) {
        collectedErrors.clear();
        collectedWarnings.clear();
        log.debug("验证监听器已初始化");
    }

    @Override
    public void parseCompleted(ParseContext context, Object result) {
        // 收集上下文中的错误和警告
        collectedErrors.addAll(context.getErrors());
        collectedWarnings.addAll(context.getWarnings());

        if (!collectedErrors.isEmpty() || !collectedWarnings.isEmpty()) {
            log.info("验证完成，错误: {}, 警告: {}",
                    collectedErrors.size(), collectedWarnings.size());

            if (detailedReport) {
                generateReport();
            }
        }
    }

    @Override
    public void parseFailed(ParseContext context, Throwable error) {
        collectedErrors.addAll(context.getErrors());
        collectedWarnings.addAll(context.getWarnings());

        log.error("验证失败: {}", error.getMessage());
        if (immediateLogging && !collectedErrors.isEmpty()) {
            collectedErrors.forEach(e ->
                    log.error("  - [{}] {}", e.getSeverity(), e.getMessage()));
        }
    }

    @Override
    public void validationCompleted(ParseContext context, Object result) {
        // 验证完成时的处理
        if (result != null) {
            log.debug("验证结果: {}", result);
        }
    }

    /**
     * 生成验证报告
     */
    public String generateReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n========== 验证报告 ==========\n");
        sb.append("总错误数: ").append(collectedErrors.size()).append("\n");
        sb.append("总警告数: ").append(collectedWarnings.size()).append("\n");

        if (!collectedErrors.isEmpty()) {
            sb.append("\n--- 错误列表 ---\n");
            for (int i = 0; i < collectedErrors.size(); i++) {
                ParseError error = collectedErrors.get(i);
                sb.append(String.format("%d. [%s] %s",
                        i + 1, error.getSeverity(), error.getMessage()));
                if (error.getElement() != null) {
                    sb.append(" (元素: ").append(error.getElement().getTagName()).append(")");
                }
                sb.append("\n");
            }
        }

        if (!collectedWarnings.isEmpty()) {
            sb.append("\n--- 警告列表 ---\n");
            for (int i = 0; i < collectedWarnings.size(); i++) {
                ParseWarning warning = collectedWarnings.get(i);
                sb.append(String.format("%d. %s", i + 1, warning.getMessage()));
                if (warning.getElement() != null) {
                    sb.append(" (元素: ").append(warning.getElement().getTagName()).append(")");
                }
                sb.append("\n");
            }
        }

        sb.append("===============================\n");
        log.info(sb.toString());
        return sb.toString();
    }

    /**
     * 是否有错误
     */
    public boolean hasErrors() {
        return !collectedErrors.isEmpty();
    }

    /**
     * 是否有警告
     */
    public boolean hasWarnings() {
        return !collectedWarnings.isEmpty();
    }

    /**
     * 获取收集到的错误
     */
    public List<ParseError> getCollectedErrors() {
        return new ArrayList<>(collectedErrors);
    }

    /**
     * 获取收集到的警告
     */
    public List<ParseWarning> getCollectedWarnings() {
        return new ArrayList<>(collectedWarnings);
    }

    /**
     * 重置收集器
     */
    public void reset() {
        collectedErrors.clear();
        collectedWarnings.clear();
    }

    @Override
    public String getListenerName() {
        return "ValidationParseListener";
    }
}
