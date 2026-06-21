package com.yaocode.sts.auth.domain.events.resource;

import com.yaocode.sts.auth.domain.aggregate.ResourceInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ResourceId;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import lombok.Getter;

/**
 * 资源取消废弃事件
 */
@Getter
public class ResourceUndeprecatedEvent extends AggregateDomainEvent<ResourceId> {

    private final ResourceId resourceId;

    public ResourceUndeprecatedEvent(ResourceInfoAggregate resource) {
        super(resource, EventTypeEnums.RESOURCE_UNDEPRECATED.getCode());
        this.resourceId = resource.getId();
    }

    public ResourceUndeprecatedEvent(ResourceId resourceId) {
        super(resourceId.getValue(), AggregateTypeEnums.RESOURCE.getCode(), EventTypeEnums.RESOURCE_UNDEPRECATED.getCode());
        this.resourceId = resourceId;
    }
}

