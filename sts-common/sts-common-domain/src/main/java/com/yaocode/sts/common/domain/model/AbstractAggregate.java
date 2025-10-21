package com.yaocode.sts.common.domain.model;

import com.yaocode.sts.common.domain.publisher.DomainEventPublisher;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 抽象聚合根
 * @author: Jin-LiangBo
 * @date: 2025年10月12日 11:04
 */
@Getter
@Setter
public abstract class AbstractAggregate<ID extends Identifier<?>> implements Aggregate<ID> {

    private ID id;
    private final List<DomainEvent> domainEvents = new ArrayList<>();

    protected AbstractAggregate(ID id) {
        this.id = Objects.requireNonNull(id);
    }

    @Override
    public Collection<DomainEvent> getDomainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }

    @Override
    public void clearDomainEvents() {
        domainEvents.clear();
    }

    protected void registerEvent(DomainEvent event) {
        domainEvents.add(event);
    }

    /**
     * 发布事件并清空
     * @param publisher 发布器
     */
    public void publishEvents(DomainEventPublisher publisher) {
        Collection<DomainEvent> events = new ArrayList<>(this.domainEvents);
        clearDomainEvents();
        publisher.publish(events);
    }

}
