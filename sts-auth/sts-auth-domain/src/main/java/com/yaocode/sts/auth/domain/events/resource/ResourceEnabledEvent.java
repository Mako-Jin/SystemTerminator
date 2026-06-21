package com.yaocode.sts.auth.domain.events.resource;

import com.yaocode.sts.auth.domain.aggregate.ResourceInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ResourceId;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import lombok.Getter;

/**
 * 资源启用事件
 */
@Getter
public class ResourceEnabledEvent extends AggregateDomainEvent<ResourceId> {

    private final ResourceId resourceId;

    public ResourceEnabledEvent(ResourceInfoAggregate resource) {
        super(resource, EventTypeEnums.RESOURCE_ENABLED.getCode());
        this.resourceId = resource.getId();
    }

    public ResourceEnabledEvent(ResourceId resourceId) {
        super(resourceId.getValue(), AggregateTypeEnums.RESOURCE.getCode(), EventTypeEnums.RESOURCE_ENABLED.getCode());
        this.resourceId = resourceId;
    }
}
