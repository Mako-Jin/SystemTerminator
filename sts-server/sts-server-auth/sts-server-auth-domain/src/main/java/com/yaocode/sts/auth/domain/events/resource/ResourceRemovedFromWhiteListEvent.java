package com.yaocode.sts.auth.domain.events.resource;

import com.yaocode.sts.auth.domain.aggregate.ResourceInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ResourceId;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import lombok.Getter;

/**
 * 资源移出白名单事件
 */
@Getter
public class ResourceRemovedFromWhiteListEvent extends AggregateDomainEvent<ResourceId> {

    private final ResourceId resourceId;

    public ResourceRemovedFromWhiteListEvent(ResourceInfoAggregate resource) {
        super(resource, EventTypeEnums.RESOURCE_REMOVED_FROM_WHITE_LIST.getCode());
        this.resourceId = resource.getId();
    }

    public ResourceRemovedFromWhiteListEvent(ResourceId resourceId) {
        super(resourceId.getValue(), AggregateTypeEnums.RESOURCE.getCode(), EventTypeEnums.RESOURCE_REMOVED_FROM_WHITE_LIST.getCode());
        this.resourceId = resourceId;
    }
}
