package com.yaocode.sts.common.domain.publisher;


import com.yaocode.sts.common.domain.model.DomainEvent;

import java.util.Collection;

/**
 * 领域事件发布组件
 * @author: Jin-LiangBo
 * @date: 2025年10月12日 14:01
 */
public interface DomainEventPublisher {

    /**
     * 发布单个事件
     * @param event 领域事件
     */
    void publish(DomainEvent event);
    /**
     * 发布系列事件
     * @param events 领域事件
     */
    void publish(Collection<DomainEvent> events);

}
