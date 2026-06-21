package com.yaocode.sts.common.domain.event;

import com.yaocode.sts.common.tools.id.IdFactory;

import java.time.LocalDateTime;

/**
 * 领域事件抽象实现层
 * @author: Jin-LiangBo
 * @date: 2025年10月12日 11:06
 */
public abstract class AbstractDomainEvent implements DomainEvent {

    private final String eventId;
    private final LocalDateTime occurredOn;

    /**
     * 事件类型（默认使用类名）
     */
    private final String eventType;

    protected AbstractDomainEvent() {
        this.eventId = IdFactory.generate();
        this.occurredOn = LocalDateTime.now();
        this.eventType = this.getClass().getSimpleName();
    }

    /**
     * 构造函数（自定义事件类型）
     * 适用于需要显式指定事件类型的场景
     */
    protected AbstractDomainEvent(String eventType) {
        this.eventId = IdFactory.generate();
        this.occurredOn = LocalDateTime.now();
        this.eventType = eventType;
    }

    @Override
    public String getEventId() {
        return eventId;
    }

    @Override
    public LocalDateTime getOccurredOn() {
        return occurredOn;
    }

    @Override
    public String getEventType() {
        return eventType;
    }

    /**
     * 获取聚合根ID（由子类实现）
     */
    @Override
    public abstract String getAggregateId();

    /**
     * 获取聚合根类型（由子类实现）
     */
    @Override
    public abstract String getAggregateType();
}
