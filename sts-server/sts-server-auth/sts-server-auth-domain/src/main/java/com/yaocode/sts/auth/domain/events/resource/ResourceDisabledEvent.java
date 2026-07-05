package com.yaocode.sts.auth.domain.events.resource;

import com.yaocode.sts.auth.domain.aggregate.ResourceInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ResourceId;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import lombok.Getter;

/**
 * 资源禁用事件
 */
@Getter
public class ResourceDisabledEvent extends AggregateDomainEvent<ResourceId> {

    private final ResourceId resourceId;

    public ResourceDisabledEvent(ResourceInfoAggregate resource) {
        super(resource, EventTypeEnums.RESOURCE_DISABLED.getCode());
        this.resourceId = resource.getId();
    }

    public ResourceDisabledEvent(ResourceId resourceId) {
        super(resourceId.getValue(), AggregateTypeEnums.RESOURCE.getCode(), EventTypeEnums.RESOURCE_DISABLED.getCode());
        this.resourceId = resourceId;
    }
}
