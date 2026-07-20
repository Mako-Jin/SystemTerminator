package com.yaocode.sts.components.flow.core.engine.parser.xml;

import com.yaocode.sts.components.flow.core.engine.parser.enums.ParseStrategyEnums;
import com.yaocode.sts.components.flow.core.exception.ParseException;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * DOM 解析器实现
 *
 * <p>特点：
 * <ul>
 *   <li>将整个 XML 文档加载到内存中，构建 DOM 树</li>
 *   <li>支持随机访问和修改</li>
 *   <li>适合小文件（建议 < 10MB）</li>
 *   <li>参考 Tomcat 的 Digester 实现</li>
 * </ul>
 *
 * <p>使用场景：
 * <ul>
 *   <li>BPMN 文件解析（通常文件较小）</li>
 *   <li>需要多次遍历和修改 XML 结构</li>
 *   <li>需要 XPath 查询</li>
 * </ul>
 *
 * @author Process Engine Team
 */
@Slf4j
public class DomXmlParser extends AbstractXmlParser {

    /**
     * 是否保留空白字符
     */
    private boolean ignoreWhitespace = true;

    /**
     * 是否展开实体引用
     */
    private boolean expandEntityReferences = true;

    @Override
    public Document parse(InputStream inputStream, String systemId) throws ParseException {
        long startTime = System.currentTimeMillis();
        logParseStart(systemId);

        // ✅ 1. 检查 InputStream
        System.out.println("=== DomXmlParser.parse() 开始 ===");
        System.out.println("systemId: " + systemId);
        System.out.println("inputStream: " + inputStream);

        try {
            // ✅ 2. 检查 InputStream 是否可用
            if (inputStream == null) {
                System.out.println("❌ inputStream 为 null");
                throw new ParseException("InputStream 为 null");
            }

            // ✅ 3. 检查 InputStream 是否有数据
            try {
                int available = inputStream.available();
                System.out.println("available bytes: " + available);
                if (available == 0) {
                    System.out.println("⚠️ InputStream 可用字节为 0，可能已被读取");
                }
            } catch (IOException e) {
                System.out.println("获取 available 失败: " + e.getMessage());
            }

            // ✅ 4. 读取前 100 个字节看看内容
            if (inputStream.markSupported()) {
                inputStream.mark(1024);
                byte[] buffer = new byte[Math.min(100, inputStream.available())];
                int read = inputStream.read(buffer);
                if (read > 0) {
                    String preview = new String(buffer, 0, read, StandardCharsets.UTF_8);
                    System.out.println("XML 预览: " + preview.substring(0, Math.min(50, preview.length())) + "...");
                }
                inputStream.reset();  // 重置，让后续解析器重新读取
            }
            DocumentBuilderFactory factory = getDocumentBuilderFactory();

            // 禁用外部实体解析，防止XXE攻击
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            // 限制实体展开深度
            factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");


            // ✅ 打印工厂配置
            System.out.println("NamespaceAware: " + factory.isNamespaceAware());
            System.out.println("Validating: " + factory.isValidating());
            System.out.println("IgnoringComments: " + factory.isIgnoringComments());
            System.out.println("IgnoringElementContentWhitespace: " + factory.isIgnoringElementContentWhitespace());
            System.out.println("Coalescing: " + factory.isCoalescing());
            System.out.println("ExpandEntityReferences: " + factory.isExpandEntityReferences());

            DocumentBuilder builder = factory.newDocumentBuilder();
            System.out.println("DocumentBuilder: " + builder.getClass().getName());

            // 设置 EntityResolver
//            builder.setEntityResolver(getEntityResolver());

            // 设置错误处理器（参考 Tomcat 风格）
            builder.setErrorHandler(new ErrorHandler() {
                @Override
                public void warning(SAXParseException exception) {
                    log.warn("XML 解析警告: systemId={}, line={}, column={}, message={}",
                            systemId, exception.getLineNumber(),
                            exception.getColumnNumber(), exception.getMessage());
                }

                @Override
                public void error(SAXParseException exception) throws SAXParseException {
                    log.error("XML 解析错误: systemId={}, line={}, column={}, message={}",
                            systemId, exception.getLineNumber(),
                            exception.getColumnNumber(), exception.getMessage());
                    throw exception;
                }

                @Override
                public void fatalError(SAXParseException exception) throws SAXParseException {
                    log.error("XML 致命错误: systemId={}, line={}, column={}, message={}",
                            systemId, exception.getLineNumber(),
                            exception.getColumnNumber(), exception.getMessage());
                    throw exception;
                }
            });

            // 解析文档
            Document document = builder.parse(inputStream, systemId);

            // 设置文档的 URI
            if (systemId != null) {
                document.setDocumentURI(systemId);
            }

            logParseComplete(systemId, startTime);
            log.debug("DOM 解析完成: systemId={}, 文档类型={}",
                    systemId, document.getDocumentElement() != null ?
                            document.getDocumentElement().getTagName() : "null");

            return document;

        } catch (Exception e) {
            logParseError(systemId, e);
            throw new ParseException(
                    String.format("DOM 解析失败: systemId=%s, message=%s",
                            systemId, e.getMessage()),
                    e);
        }
    }

    @Override
    public ParseStrategyEnums getStrategy() {
        return ParseStrategyEnums.DOM;
    }

    @Override
    public String getDescription() {
        return String.format("DOM XML Parser (ignoreWhitespace=%s, expandEntityReferences=%s)",
                ignoreWhitespace, expandEntityReferences);
    }

    // ==================== 配置方法 ====================

    /**
     * 设置是否忽略空白字符
     */
    public void setIgnoreWhitespace(boolean ignoreWhitespace) {
        this.ignoreWhitespace = ignoreWhitespace;
        // 更新工厂配置
        try {
            DocumentBuilderFactory factory = getDocumentBuilderFactory();
            factory.setIgnoringElementContentWhitespace(ignoreWhitespace);
        } catch (Exception e) {
            log.warn("设置忽略空白字符失败", e);
        }
    }

    /**
     * 设置是否展开实体引用
     */
    public void setExpandEntityReferences(boolean expandEntityReferences) {
        this.expandEntityReferences = expandEntityReferences;
        try {
            DocumentBuilderFactory factory = getDocumentBuilderFactory();
            factory.setExpandEntityReferences(expandEntityReferences);
        } catch (Exception e) {
            log.warn("设置展开实体引用失败", e);
        }
    }
}
