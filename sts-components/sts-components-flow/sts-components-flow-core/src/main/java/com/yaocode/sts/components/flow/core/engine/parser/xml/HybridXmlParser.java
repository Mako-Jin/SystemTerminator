package com.yaocode.sts.components.flow.core.engine.parser.xml;

import com.yaocode.sts.components.flow.core.engine.parser.ParseContext;
import com.yaocode.sts.components.flow.core.engine.parser.enums.ParseStrategyEnums;
import com.yaocode.sts.components.flow.core.engine.parser.rule.RuleRegistry;
import com.yaocode.sts.components.flow.core.exception.ParseException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 混合解析器实现
 *
 * <p>根据文件大小自动选择 DOM 或 SAX 解析策略：
 * <ul>
 *   <li>小文件（≤ 阈值）：使用 DOM 解析，支持随机访问</li>
 *   <li>大文件（> 阈值）：使用 SAX 解析，内存友好</li>
 * </ul>
 *
 * <p>参考 Tomcat 的自动选择逻辑
 *
 * @author Process Engine Team
 */
@Slf4j
public class HybridXmlParser extends AbstractXmlParser {

    /**
     * DOM 解析阈值（默认 1MB）
     * 小于此值使用 DOM，大于此值使用 SAX
     */
    private static final int DEFAULT_DOM_THRESHOLD = 1024 * 1024; // 1MB

    /**
     * 缓冲区大小（用于检测文件大小）
     */
    private static final int BUFFER_SIZE = 8192;

    /**
     * DOM 解析阈值（字节）
     * -- GETTER --
     *  获取 DOM 阈值（字节）

     */
    @Getter
    @Setter
    private int domThreshold = DEFAULT_DOM_THRESHOLD;

    /**
     * 规则注册中心（用于 SAX 解析）
     */
    @Setter
    private RuleRegistry ruleRegistry;

    /**
     * 解析上下文（用于 SAX 解析）
     */
    @Setter
    private ParseContext context;

    /**
     * 是否强制使用 DOM 解析
     */
    @Setter
    private boolean forceDom = false;

    /**
     * 是否强制使用 SAX 解析
     */
    @Setter
    private boolean forceSax = false;

    @Override
    public Document parse(InputStream inputStream, String systemId) throws ParseException {
        long startTime = System.currentTimeMillis();
        logParseStart(systemId);

        // 强制使用 DOM
        if (forceDom) {
            log.debug("强制使用 DOM 解析: systemId={}", systemId);
            return parseWithDom(inputStream, systemId);
        }

        // 强制使用 SAX
        if (forceSax) {
            log.debug("强制使用 SAX 解析: systemId={}", systemId);
            parseWithSax(inputStream, systemId);
            return null;
        }

        // 自动选择

        try (BufferedInputStream bis = new BufferedInputStream(inputStream, BUFFER_SIZE)) {
            try {
                // 标记位置以便重置
                bis.mark(domThreshold + 1);

                // 读取部分内容判断大小
                byte[] buffer = new byte[domThreshold + 1];
                int bytesRead = bis.read(buffer);

                // 重置流位置
                bis.reset();

                // 根据大小选择解析策略
                if (bytesRead <= domThreshold) {
                    // 小文件使用 DOM 解析
                    log.debug("文件较小 ({} bytes)，使用 DOM 解析: systemId={}", bytesRead, systemId);
                    return parseWithDom(bis, systemId);
                } else {
                    // 大文件使用 SAX 解析
                    log.debug("文件较大 ({} bytes)，使用 SAX 解析: systemId={}", bytesRead, systemId);
                    parseWithSax(bis, systemId);
                    return null;
                }

            } catch (IOException e) {
                logParseError(systemId, e);
                throw new ParseException("检测文件大小失败: " + systemId, e);
            }
        } catch (IOException e) {
            log.warn("关闭输入流失败", e);
        }
        return null;
    }

    /**
     * 使用 DOM 解析
     */
    private Document parseWithDom(InputStream inputStream, String systemId) throws ParseException {
        DomXmlParser domParser = new DomXmlParser();
        return domParser.parse(inputStream, systemId);
    }

    /**
     * 使用 SAX 解析
     */
    private void parseWithSax(InputStream inputStream, String systemId) throws ParseException {
        SaxXmlParser saxParser = new SaxXmlParser();

        // 传递配置
        saxParser.setRuleRegistry(ruleRegistry);
        saxParser.setContext(context);

        saxParser.parse(inputStream, systemId);
    }

    @Override
    public ParseStrategyEnums getStrategy() {
        return ParseStrategyEnums.HYBRID;
    }

    @Override
    public String getDescription() {
        return String.format("Hybrid XML Parser (domThreshold=%d, forceDom=%s, forceSax=%s)",
                domThreshold, forceDom, forceSax);
    }

    // ==================== 配置方法 ====================

    /**
     * 设置 DOM 阈值（MB）
     */
    public void setDomThresholdMb(int mb) {
        this.domThreshold = mb * 1024 * 1024;
        log.debug("设置 DOM 阈值为 {} MB", mb);
    }

    /**
     * 获取 DOM 阈值（MB）
     */
    public int getDomThresholdMb() {
        return domThreshold / (1024 * 1024);
    }

    /**
     * 自动模式（取消强制设置）
     */
    public void setAutoMode() {
        this.forceDom = false;
        this.forceSax = false;
        log.debug("切换到自动模式");
    }
}
