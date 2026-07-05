package com.yaocode.sts.auth.domain.events.resource;

import com.yaocode.sts.auth.domain.aggregate.ResourceInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ResourceId;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import lombok.Getter;

/**
 * 资源创建事件
 */
@Getter
public class ResourceCreatedEvent extends AggregateDomainEvent<ResourceId> {

    private final ResourceId resourceId;
    private final String resourceName;

    public ResourceCreatedEvent(ResourceInfoAggregate resource) {
        super(resource, EventTypeEnums.RESOURCE_CREATED.getCode());
        this.resourceId = resource.getId();
        this.resourceName = resource.getResourceName();
    }

    public ResourceCreatedEvent(ResourceId resourceId, String resourceName) {
        super(resourceId.getValue(), AggregateTypeEnums.RESOURCE.getCode(), EventTypeEnums.RESOURCE_CREATED.getCode());
        this.resourceId = resourceId;
        this.resourceName = resourceName;
    }
}
