package com.yaocode.sts.components.flow.core.engine.parser.xml;

import com.yaocode.sts.components.flow.core.exception.ParseException;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * 抽象 XML 解析器基类
 *
 * <p>参考 Tomcat Digester 的设计思想，提供：
 * <ul>
 *   <li>安全的 XML 解析配置（防止 XXE 攻击）</li>
 *   <li>统一的 EntityResolver</li>
 *   <li>通用的工厂创建方法</li>
 * </ul>
 *
 * <p>安全配置：
 * <ul>
 *   <li>禁用 DTD 声明</li>
 *   <li>禁用外部实体</li>
 *   <li>禁用外部参数实体</li>
 *   <li>禁用外部 DTD 加载</li>
 * </ul>
 *
 * @author Process Engine Team
 */
@Slf4j
public abstract class AbstractXmlParser implements XmlParser {

    /**
     * 空的 EntityResolver，防止外部 DTD 加载
     * 参考 Tomcat 的实现，但改为抛出异常而不是返回空内容
     */
    protected static final EntityResolver NULL_ENTITY_RESOLVER = (publicId, systemId) -> {
        if (systemId != null || publicId != null) {
            throw new RuntimeException("外部实体引用被禁止: publicId=" + publicId + ", systemId=" + systemId);
        }
        return null;
    };

    /**
     * 默认的 DocumentBuilderFactory 配置
     */
    protected DocumentBuilderFactory defaultDocumentBuilderFactory;

    /**
     * 默认的 SAXParserFactory 配置
     */
    protected SAXParserFactory defaultSAXParserFactory;

    /**
     * 构造方法
     */
    public AbstractXmlParser() {
        try {
            this.defaultDocumentBuilderFactory = createSecureDocumentBuilderFactory();
            this.defaultSAXParserFactory = createSecureSAXParserFactory();
        } catch (Exception e) {
            log.warn("创建安全的 XML 解析工厂失败，将使用默认配置", e);
            this.defaultDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
            this.defaultSAXParserFactory = SAXParserFactory.newInstance();
        }
    }

    /**
     * 创建安全的 DocumentBuilderFactory
     *
     * <p>安全配置：
     * <ul>
     *   <li>禁用 DOCTYPE 声明</li>
     *   <li>禁用外部通用实体</li>
     *   <li>禁用外部参数实体</li>
     *   <li>禁用外部 DTD 加载</li>
     * </ul>
     *
     * @return 配置好的 DocumentBuilderFactory
     * @throws ParserConfigurationException 配置异常
     */
    protected DocumentBuilderFactory createSecureDocumentBuilderFactory() throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        // 基础配置
        factory.setNamespaceAware(true);
        factory.setValidating(false);

        // ====== 安全配置（防止 XXE 攻击） ======
        // 1. 禁用 DOCTYPE 声明
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);

        // 2. 禁用外部通用实体
        factory.setFeature("http://xml.org/sax/features/external-general-entities", false);

        // 3. 禁用外部参数实体
        factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);

        // 4. 禁用外部 DTD 加载
        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

        // 5. 禁用 Java 外部实体（JDK 特定）
        try {
            factory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
        } catch (Exception e) {
            log.debug("不支持 secure-processing 特性，忽略");
        }

        return factory;
    }

    /**
     * 创建安全的 SAXParserFactory
     *
     * @return 配置好的 SAXParserFactory
     * @throws ParserConfigurationException  配置异常
     * @throws SAXNotRecognizedException     不识别特性
     * @throws SAXNotSupportedException      不支持特性
     */
    protected SAXParserFactory createSecureSAXParserFactory()
            throws ParserConfigurationException, SAXNotRecognizedException, SAXNotSupportedException {
        SAXParserFactory factory = SAXParserFactory.newInstance();

        // 基础配置
        factory.setNamespaceAware(true);
        factory.setValidating(false);

        // ====== 安全配置（防止 XXE 攻击） ======
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);

        return factory;
    }

    /**
     * 获取 DocumentBuilderFactory（子类可覆盖）
     */
    protected DocumentBuilderFactory getDocumentBuilderFactory() {
        return defaultDocumentBuilderFactory;
    }

    /**
     * 获取 SAXParserFactory（子类可覆盖）
     */
    protected SAXParserFactory getSAXParserFactory() {
        return defaultSAXParserFactory;
    }

    /**
     * 创建新的 DocumentBuilderFactory（不共享）
     */
    protected DocumentBuilderFactory newDocumentBuilderFactory() throws ParserConfigurationException {
        return createSecureDocumentBuilderFactory();
    }

    /**
     * 创建新的 SAXParserFactory（不共享）
     */
    protected SAXParserFactory newSAXParserFactory()
            throws ParserConfigurationException, SAXNotRecognizedException, SAXNotSupportedException {
        return createSecureSAXParserFactory();
    }

    /**
     * 获取 EntityResolver（默认使用空解析器）
     */
    protected EntityResolver getEntityResolver() {
        return NULL_ENTITY_RESOLVER;
    }

    /**
     * 记录解析开始
     */
    protected void logParseStart(String systemId) {
        if (log.isDebugEnabled()) {
            log.debug("开始 XML 解析: systemId={}, parser={}", systemId, getParserName());
        }
    }

    /**
     * 记录解析完成
     */
    protected void logParseComplete(String systemId, long startTime) {
        if (log.isDebugEnabled()) {
            long elapsed = System.currentTimeMillis() - startTime;
            log.debug("XML 解析完成: systemId={}, elapsed={}ms", systemId, elapsed);
        }
    }

    /**
     * 记录解析错误
     */
    protected void logParseError(String systemId, Exception e) {
        log.error("XML 解析失败: systemId={}", systemId, e);
    }

    /**
     * 解析 XML 输入流（简化版本）
     *
     * @param inputStream XML 输入流
     * @return 解析后的 Document 对象
     * @throws ParseException 解析异常
     */
    @Override
    public Document parse(InputStream inputStream) throws ParseException {
        return parse(inputStream, null);
    }

    /**
     * 获取解析器名称
     *
     * @return 解析器名称
     */
    @Override
    public String getParserName() {
        return getClass().getSimpleName();
    }

    /**
     * 是否支持命名空间
     *
     * @return true 支持，false 不支持
     */
    @Override
    public boolean isNamespaceAware() {
        return true;
    }

    /**
     * 是否验证 DTD
     *
     * @return true 验证，false 不验证
     */
    @Override
    public boolean isValidating() {
        return false;
    }

    /**
     * 获取解析器描述
     *
     * @return 描述信息
     */
    @Override
    public String getDescription() {
        return String.format("%s (Strategy: %s, NamespaceAware: %s, Validating: %s)",
                getParserName(), getStrategy(), isNamespaceAware(), isValidating());
    }

    @Override
    public String toString() {
        return getDescription();
    }

}
