package com.yaocode.sts.common.domain.model;


import java.util.Collection;

/**
 * 领域对象接口
 * @author: Jin-LiangBo
 * @date: 2025年10月12日 11:01
 */
public interface Aggregate<ID extends Identifier<?>> extends Entity<ID> {

    /**
     * 聚合根标记接口，通常包含领域事件相关方法
     * @return Collection<DomainEvent>
     */
    Collection<DomainEvent> getDomainEvents();

    /**
     * 清楚领域事件
     */
    void clearDomainEvents();

}
