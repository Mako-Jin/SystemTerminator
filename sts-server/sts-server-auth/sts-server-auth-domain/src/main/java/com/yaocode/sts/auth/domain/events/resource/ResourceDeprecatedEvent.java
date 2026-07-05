package com.yaocode.sts.auth.domain.events.resource;

import com.yaocode.sts.auth.domain.aggregate.ResourceInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ResourceId;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import lombok.Getter;

/**
 * 资源标记废弃事件
 */
@Getter
public class ResourceDeprecatedEvent extends AggregateDomainEvent<ResourceId> {

    private final ResourceId resourceId;

    public ResourceDeprecatedEvent(ResourceInfoAggregate resource) {
        super(resource, EventTypeEnums.RESOURCE_DEPRECATED.getCode());
        this.resourceId = resource.getId();
    }

    public ResourceDeprecatedEvent(ResourceId resourceId) {
        super(resourceId.getValue(), AggregateTypeEnums.RESOURCE.getCode(), EventTypeEnums.RESOURCE_DEPRECATED.getCode());
        this.resourceId = resourceId;
    }
}
