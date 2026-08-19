package com.yaocode.sts.flow.core.engine.parser.rule;

import com.yaocode.sts.flow.core.engine.parser.ParseContext;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

/**
 * 规则注册中心
 *
 * <p>管理所有 BPMN 元素的解析规则，支持：
 * <ul>
 *   <li>按标签名注册规则</li>
 *   <li>按命名空间注册规则</li>
 *   <li>规则查找（优先命名空间规则，回退到默认规则）</li>
 *   <li>运行时动态注册/注销规则</li>
 *   <li>规则统计信息</li>
 * </ul>
 *
 * <p>使用示例：
 * <pre>
 * RuleRegistry registry = new RuleRegistry();
 *
 * // 注册默认规则
 * registry.registerRule("process", new ProcessRule());
 * registry.registerRule("userTask", new UserTaskRule());
 *
 * // 注册带命名空间的规则
 * registry.registerRule("<a href="http://camunda.org/schema/1.0/bpmn">...</a>", "inputOutput", new InputOutputRule());
 *
 * // 获取规则
 * ParseRule rule = registry.getRule(context, "userTask");
 * </pre>
 *
 * TODO 支持规则组合和优先级
 *
 * @author Process Engine Team
 */
@Slf4j
public class RuleRegistry {

    /**
     * 默认规则映射：标签名 -> 解析规则
     */
    private final Map<String, ParseRule> defaultRules = new ConcurrentHashMap<>();

    // TODO 改为支持多个规则匹配同一标签（按优先级排序）
    //  private final Map<String, List<PrioritizedRule>> defaultRules = new ConcurrentHashMap<>();

    /**
     * 命名空间规则映射：命名空间 -> (标签名 -> 解析规则)
     */
    private final Map<String, Map<String, ParseRule>> namespaceRules = new ConcurrentHashMap<>();

    /**
     * 规则别名映射
     */
    private final Map<String, String> aliasMapping = new ConcurrentHashMap<>();

    /**
     * 统计信息
     * -- GETTER --
     *  获取统计信息
     *
     * @return 统计信息

     */
    @Getter
    private final RegistryStatistics statistics = new RegistryStatistics();

    public RuleRegistry() {
        // ==================== BPMN 核心元素 ====================
        this.registerRule("definitions", new DefinitionsRule());
        // 流程定义
        this.registerRule("process", new ProcessRule());
        // 事件
        this.registerRule("startEvent", new StartEventRule());
        this.registerRule("endEvent", new EndEventRule());
        this.registerRule("intermediateCatchEvent", new IntermediateCatchEventRule());
        this.registerRule("intermediateThrowEvent", new IntermediateThrowEventRule());
        this.registerRule("boundaryEvent", new BoundaryEventRule());

        // 任务
        this.registerRule("userTask", new UserTaskRule());
        this.registerRule("serviceTask", new ServiceTaskRule());
        this.registerRule("businessRuleTask", new ServiceTaskRule());  // 复用
        this.registerRule("scriptTask", new ServiceTaskRule());        // 复用
        this.registerRule("manualTask", new UserTaskRule());           // 复用
        this.registerRule("sendTask", new ServiceTaskRule());          // 复用
        this.registerRule("receiveTask", new ServiceTaskRule());       // 复用

        // 网关
        this.registerRule("exclusiveGateway", new GatewayRule());
        this.registerRule("parallelGateway", new GatewayRule());
        this.registerRule("inclusiveGateway", new GatewayRule());
        this.registerRule("eventBasedGateway", new GatewayRule());

        // 子流程和调用
        this.registerRule("subProcess", new SubProcessRule());
        this.registerRule("callActivity", new CallActivityRule());

        // 序列流
        this.registerRule("sequenceFlow", new SequenceFlowRule());

        // ==================== Camunda 扩展 ====================
        this.registerRule("http://camunda.org/schema/1.0/bpmn", "inputOutput", new InputOutputRule());
        this.registerRule("http://camunda.org/schema/1.0/bpmn", "properties", new PropertiesRule());

        // BPMN DI（忽略）
        this.registerRule("BPMNDiagram", new BpmnDiagramRule());
        this.registerRule("BPMNPlane", new BpmnDiagramRule());
        this.registerRule("BPMNShape", new BpmnDiagramRule());
        this.registerRule("BPMNEdge", new BpmnDiagramRule());
        this.registerRule("Bounds", new BpmnDiagramRule());
        this.registerRule("waypoint", new BpmnDiagramRule());

        // ==================== 事件子元素 ====================
        this.registerRule("outgoing", new OutgoingRule());
        this.registerRule("incoming", new IncomingRule());

        // ==================== 别名支持 ====================
        this.alias("gateway", "exclusiveGateway");
        this.alias("task", "serviceTask");
        this.alias("event", "intermediateCatchEvent");
    }

    // ==================== 注册方法 ====================

    /**
     * 注册默认规则
     *
     * @param tagName 标签名
     * @param rule    解析规则
     * @return 是否注册成功
     */
    public boolean registerRule(String tagName, ParseRule rule) {
        if (tagName == null || tagName.isEmpty()) {
            log.warn("标签名不能为空");
            return false;
        }
        if (rule == null) {
            log.warn("规则不能为空");
            return false;
        }

        ParseRule oldRule = defaultRules.put(tagName, rule);
        statistics.recordRegistration(tagName, oldRule != null);
        log.debug("注册规则: {} -> {}", tagName, rule.getClass().getSimpleName());
        return true;
    }

    /**
     * 注册带命名空间的规则
     *
     * @param namespace 命名空间
     * @param tagName   标签名
     * @param rule      解析规则
     * @return 是否注册成功
     */
    public boolean registerRule(String namespace, String tagName, ParseRule rule) {
        if (namespace == null || namespace.isEmpty()) {
            log.warn("命名空间不能为空");
            return false;
        }
        if (tagName == null || tagName.isEmpty()) {
            log.warn("标签名不能为空");
            return false;
        }
        if (rule == null) {
            log.warn("规则不能为空");
            return false;
        }

        Map<String, ParseRule> nsRules = namespaceRules.computeIfAbsent(namespace, k -> new ConcurrentHashMap<>());
        ParseRule oldRule = nsRules.put(tagName, rule);
        statistics.recordRegistration(namespace + ":" + tagName, oldRule != null);
        log.debug("注册命名空间规则: {}:{} -> {}", namespace, tagName, rule.getClass().getSimpleName());
        return true;
    }

    /**
     * 批量注册规则
     *
     * @param rules 规则映射
     */
    public void registerRules(Map<String, ParseRule> rules) {
        if (rules == null || rules.isEmpty()) {
            return;
        }
        for (Map.Entry<String, ParseRule> entry : rules.entrySet()) {
            registerRule(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 批量注册命名空间规则
     *
     * @param namespace 命名空间
     * @param rules     规则映射
     */
    public void registerRules(String namespace, Map<String, ParseRule> rules) {
        if (namespace == null || namespace.isEmpty() || rules == null || rules.isEmpty()) {
            return;
        }
        for (Map.Entry<String, ParseRule> entry : rules.entrySet()) {
            registerRule(namespace, entry.getKey(), entry.getValue());
        }
    }

    /**
     * 注册规则别名
     *
     * @param alias  别名
     * @param actual 实际标签名
     */
    public void registerAlias(String alias, String actual) {
        if (alias != null && !alias.isEmpty() && actual != null && !actual.isEmpty()) {
            aliasMapping.put(alias, actual);
            log.debug("注册规则别名: {} -> {}", alias, actual);
        }
    }

    // ==================== 注销方法 ====================

    /**
     * 注销默认规则
     *
     * @param tagName 标签名
     * @return 被移除的规则，如果不存在返回 null
     */
    public ParseRule unregisterRule(String tagName) {
        if (tagName == null || tagName.isEmpty()) {
            return null;
        }
        ParseRule removed = defaultRules.remove(tagName);
        if (removed != null) {
            statistics.recordUnregistration(tagName);
            log.debug("注销规则: {}", tagName);
        }
        return removed;
    }

    /**
     * 注销命名空间规则
     *
     * @param namespace 命名空间
     * @param tagName   标签名
     * @return 被移除的规则，如果不存在返回 null
     */
    public ParseRule unregisterRule(String namespace, String tagName) {
        if (namespace == null || namespace.isEmpty() || tagName == null || tagName.isEmpty()) {
            return null;
        }
        Map<String, ParseRule> nsRules = namespaceRules.get(namespace);
        if (nsRules == null) {
            return null;
        }
        ParseRule removed = nsRules.remove(tagName);
        if (removed != null) {
            statistics.recordUnregistration(namespace + ":" + tagName);
            log.debug("注销命名空间规则: {}:{}", namespace, tagName);
            // 如果命名空间下没有规则了，移除命名空间
            if (nsRules.isEmpty()) {
                namespaceRules.remove(namespace);
            }
        }
        return removed;
    }

    /**
     * 清空所有规则
     */
    public void clear() {
        defaultRules.clear();
        namespaceRules.clear();
        aliasMapping.clear();
        statistics.clear();
        log.info("规则注册中心已清空");
    }

    // ==================== 查找方法 ====================

    /**
     * 获取规则
     *
     * <p>查找顺序：
     * <ol>
     *   <li>优先查找带命名空间的规则</li>
     *   <li>如果没有找到，回退到默认规则</li>
     *   <li>如果还没有找到，尝试别名映射</li>
     * </ol>
     *
     * @param context 解析上下文（用于获取当前命名空间）
     * @param tagName 标签名
     * @return 解析规则，如果不存在返回 null
     */
    public ParseRule getRule(ParseContext context, String tagName) {
        if (tagName == null || tagName.isEmpty()) {
            return null;
        }

        // 1. 优先查找带命名空间的规则
        String namespace = context != null ? context.getCurrentNamespace() : null;
        if (namespace != null && !namespace.isEmpty()) {
            Map<String, ParseRule> nsRules = namespaceRules.get(namespace);
            if (nsRules != null) {
                ParseRule rule = nsRules.get(tagName);
                if (rule != null) {
                    statistics.recordHit(true);
                    return rule;
                }
            }
        }

        // 2. 查找默认规则
        ParseRule rule = defaultRules.get(tagName);
        if (rule != null) {
            statistics.recordHit(true);
            return rule;
        }

        // 3. 尝试别名映射
        String actualTag = aliasMapping.get(tagName);
        if (actualTag != null) {
            rule = defaultRules.get(actualTag);
            if (rule != null) {
                statistics.recordHit(true);
                log.debug("通过别名 {} -> {} 找到规则", tagName, actualTag);
                return rule;
            }
        }

        // 4. 未找到规则
        statistics.recordHit(false);
        log.trace("未找到规则: tagName={}, namespace={}", tagName, namespace);
        return null;
    }

    /**
     * 获取规则（简化版本，不使用上下文）
     *
     * @param tagName 标签名
     * @return 解析规则，如果不存在返回 null
     */
    public ParseRule getRule(String tagName) {
        return getRule(null, tagName);
    }

    /**
     * 检查是否存在指定标签的规则
     *
     * @param tagName 标签名
     * @return 是否存在
     */
    public boolean hasRule(String tagName) {
        return defaultRules.containsKey(tagName);
    }

    /**
     * 检查是否存在指定命名空间下的规则
     *
     * @param namespace 命名空间
     * @param tagName   标签名
     * @return 是否存在
     */
    public boolean hasRule(String namespace, String tagName) {
        if (namespace == null || namespace.isEmpty()) {
            return hasRule(tagName);
        }
        Map<String, ParseRule> nsRules = namespaceRules.get(namespace);
        return nsRules != null && nsRules.containsKey(tagName);
    }

    // ==================== 获取方法 ====================

    /**
     * 获取所有默认规则
     *
     * @return 规则映射（不可修改）
     */
    public Map<String, ParseRule> getDefaultRules() {
        return new HashMap<>(defaultRules);
    }

    /**
     * 获取所有命名空间规则
     *
     * @return 命名空间规则映射（不可修改）
     */
    public Map<String, Map<String, ParseRule>> getNamespaceRules() {
        Map<String, Map<String, ParseRule>> copy = new HashMap<>();
        for (Map.Entry<String, Map<String, ParseRule>> entry : namespaceRules.entrySet()) {
            copy.put(entry.getKey(), new HashMap<>(entry.getValue()));
        }
        return copy;
    }

    /**
     * 获取指定命名空间下的所有规则
     *
     * @param namespace 命名空间
     * @return 规则映射，如果不存在返回空映射
     */
    public Map<String, ParseRule> getNamespaceRules(String namespace) {
        if (namespace == null || namespace.isEmpty()) {
            return new HashMap<>();
        }
        Map<String, ParseRule> nsRules = namespaceRules.get(namespace);
        return nsRules != null ? new HashMap<>(nsRules) : new HashMap<>();
    }

    /**
     * 获取所有注册的标签名
     *
     * @return 标签名集合
     */
    public Set<String> getRegisteredTags() {
        return defaultRules.keySet();
    }

    /**
     * 获取所有注册的命名空间
     *
     * @return 命名空间集合
     */
    public Set<String> getRegisteredNamespaces() {
        return namespaceRules.keySet();
    }

    /**
     * 获取规则总数
     *
     * @return 规则总数
     */
    public int getRuleCount() {
        int count = defaultRules.size();
        for (Map<String, ParseRule> nsRules : namespaceRules.values()) {
            count += nsRules.size();
        }
        return count;
    }

    // ==================== 统计方法 ====================

    /**
     * 重置统计信息
     */
    public void resetStatistics() {
        statistics.reset();
    }

    /**
     * 打印统计信息
     */
    public void printStatistics() {
        statistics.print();
    }

    // ==================== 内部统计类 ====================

    /**
     * 规则注册中心统计信息
     */
    @Getter
    public static class RegistryStatistics {
        private final LongAdder totalRegistrations = new LongAdder();
        private final LongAdder totalUnregistrations = new LongAdder();
        private final LongAdder totalHits = new LongAdder();
        private final LongAdder totalMisses = new LongAdder();
        private final LongAdder totalUpdates = new LongAdder();

        void recordRegistration(String name, boolean isUpdate) {
            totalRegistrations.increment();
            if (isUpdate) {
                totalUpdates.increment();
            }
        }

        void recordUnregistration(String name) {
            totalUnregistrations.increment();
        }

        void recordHit(boolean hit) {
            if (hit) {
                totalHits.increment();
            } else {
                totalMisses.increment();
            }
        }

        void reset() {
            totalRegistrations.reset();
            totalUnregistrations.reset();
            totalHits.reset();
            totalMisses.reset();
            totalUpdates.reset();
        }

        synchronized void clear() {
            reset();
        }

        /**
         * 获取命中率
         */
        public double getHitRate() {
            long hits = totalHits.sum();
            long misses = totalMisses.sum();
            long total = hits + misses;
            return total > 0 ? (double) hits / total : 0.0;
        }

        /**
         * 获取总查询次数
         */
        public long getTotalLookups() {
            return totalHits.sum() + totalMisses.sum();
        }

        /**
         * 获取总注册数
         */
        public long getTotalRegistrations() {
            return totalRegistrations.sum();
        }

        /**
         * 获取总注销数
         */
        public long getTotalUnregistrations() {
            return totalUnregistrations.sum();
        }

        /**
         * 获取总更新数
         */
        public long getTotalUpdates() {
            return totalUpdates.sum();
        }

        /**
         * 打印统计信息
         */
        public void print() {
            log.info("=== RuleRegistry Statistics ===");
            log.info("Total Registrations: {}", totalRegistrations.sum());
            log.info("Total Updates: {}", totalUpdates.sum());
            log.info("Total Unregistrations: {}", totalUnregistrations.sum());
            log.info("Total Lookups: {}", getTotalLookups());
            log.info("Hits: {}", totalHits.sum());
            log.info("Misses: {}", totalMisses.sum());
            log.info("Hit Rate: {}", String.format("%.2f%%", getHitRate() * 100));
        }

        @Override
        public String toString() {
            return String.format("RegistryStatistics{registrations=%d, unregistrations=%d, hits=%d, misses=%d, hitRate=%.2f%%}",
                    totalRegistrations.sum(), totalUnregistrations.sum(),
                    totalHits.sum(), totalMisses.sum(), getHitRate() * 100);
        }
    }

    // ==================== 链式调用 ====================

    /**
     * 链式注册规则
     *
     * @param tagName 标签名
     * @param rule    解析规则
     * @return 当前实例（支持链式调用）
     */
    public RuleRegistry register(String tagName, ParseRule rule) {
        registerRule(tagName, rule);
        return this;
    }

    /**
     * 链式注册命名空间规则
     *
     * @param namespace 命名空间
     * @param tagName   标签名
     * @param rule      解析规则
     * @return 当前实例（支持链式调用）
     */
    public RuleRegistry register(String namespace, String tagName, ParseRule rule) {
        registerRule(namespace, tagName, rule);
        return this;
    }

    /**
     * 链式注册别名
     *
     * @param alias  别名
     * @param actual 实际标签名
     * @return 当前实例（支持链式调用）
     */
    public RuleRegistry alias(String alias, String actual) {
        registerAlias(alias, actual);
        return this;
    }

    // ==================== 验证方法 ====================

    /**
     * 验证规则是否有效
     *
     * @param tagName 标签名
     * @return 规则是否有效
     */
    public boolean isValidRule(String tagName) {
        return hasRule(tagName);
    }

    /**
     * 验证命名空间规则是否有效
     *
     * @param namespace 命名空间
     * @param tagName   标签名
     * @return 规则是否有效
     */
    public boolean isValidRule(String namespace, String tagName) {
        return hasRule(namespace, tagName);
    }
}