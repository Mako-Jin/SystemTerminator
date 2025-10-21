package com.yaocode.sts.common.domain.publisher;

import com.yaocode.sts.common.domain.model.DomainEvent;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Collection;

/**
 * Spring boot 事件发送器
 * @author: Jin-LiangBo
 * @date: 2025年10月12日 14:01
 */
public class SpringDomainEventPublisher implements DomainEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public SpringDomainEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publish(DomainEvent event) {
        applicationEventPublisher.publishEvent(event);
    }

    @Override
    public void publish(Collection<DomainEvent> events) {
        events.forEach(this::publish);
    }

}
