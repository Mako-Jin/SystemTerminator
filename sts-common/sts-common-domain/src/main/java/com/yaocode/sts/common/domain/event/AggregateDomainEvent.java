package com.yaocode.sts.common.domain.event;

import com.yaocode.sts.common.domain.model.AbstractAggregate;
import com.yaocode.sts.common.domain.valueobject.Identifier;
import lombok.Getter;

/**
 * 聚合根领域事件基类
 * 自动从聚合根中提取聚合根ID和聚合根类型
 *
 * 使用场景：
 * 1. 大多数领域事件都应该继承此类，因为它自动提供了 aggregateId 和 aggregateType
 * 2. 适用于聚合根已经创建完成的场景
 *
 * @author: Jin-LiangBo
 * @date: 2025-10-12
 */
@Getter
public abstract class AggregateDomainEvent<ID extends Identifier<?>> extends AbstractDomainEvent {

    /**
     * 聚合根ID
     */
    private final String aggregateId;

    /**
     * 聚合根类型（使用聚合根的类名）
     */
    private final String aggregateType;

    /**
     * 构造函数 - 从聚合根自动提取信息（推荐使用）
     *
     * @param aggregate 聚合根实例
     */
    protected AggregateDomainEvent(AbstractAggregate<ID> aggregate) {
        super();
        this.aggregateId = aggregate.getId().getValue().toString();
        this.aggregateType = aggregate.getClass().getSimpleName();
    }

    /**
     * 构造函数 - 从聚合根自动提取信息 + 自定义事件类型
     *
     * @param aggregate 聚合根实例
     * @param eventType 自定义事件类型（如："USER_REGISTERED"）
     */
    protected AggregateDomainEvent(AbstractAggregate<ID> aggregate, String eventType) {
        super(eventType);
        this.aggregateId = aggregate.getId().getValue().toString();
        this.aggregateType = aggregate.getClass().getSimpleName();
    }

    /**
     * 构造函数 - 手动指定聚合根信息
     * 适用于聚合根还未完全构建、或无法直接传递聚合根实例的场景
     *
     * @param aggregateId 聚合根ID
     * @param aggregateType 聚合根类型
     */
    protected AggregateDomainEvent(String aggregateId, String aggregateType) {
        super();
        this.aggregateId = aggregateId;
        this.aggregateType = aggregateType;
    }

    /**
     * 构造函数 - 手动指定聚合根信息 + 自定义事件类型
     *
     * @param aggregateId 聚合根ID
     * @param aggregateType 聚合根类型
     * @param eventType 自定义事件类型
     */
    protected AggregateDomainEvent(String aggregateId, String aggregateType, String eventType) {
        super(eventType);
        this.aggregateId = aggregateId;
        this.aggregateType = aggregateType;
    }

    @Override
    public String getAggregateId() {
        return aggregateId;
    }

    @Override
    public String getAggregateType() {
        return aggregateType;
    }
}
