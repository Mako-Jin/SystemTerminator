package com.yaocode.sts.common.domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 领域事件
 * @author: Jin-LiangBo
 * @date: 2025年10月12日 11:06
 */
public interface DomainEvent extends Serializable {

    /**
     * 获取领域事件id
     * @return java.lang.String
     */
    String getEventId();

    /**
     * 事件发生时间
     * @return java.time.LocalDateTime
     */
    LocalDateTime getOccurredOn();

    /**
     * 事件类型
     * @return java.lang.String
     */
    String getEventType();


}
