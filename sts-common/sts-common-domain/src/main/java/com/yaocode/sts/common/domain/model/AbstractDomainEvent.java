package com.yaocode.sts.common.domain.model;


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

    protected AbstractDomainEvent() {
        this.eventId = IdFactory.generate();
        this.occurredOn = LocalDateTime.now();
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
        return this.getClass().getSimpleName();
    }
}
