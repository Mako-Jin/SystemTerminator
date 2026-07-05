package com.yaocode.sts.auth.domain.events.resource;

import com.yaocode.sts.auth.domain.aggregate.ResourceInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ResourceId;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import lombok.Getter;

/**
 * 资源加入白名单事件
 */
@Getter
public class ResourceAddedToWhiteListEvent extends AggregateDomainEvent<ResourceId> {

    private final ResourceId resourceId;

    public ResourceAddedToWhiteListEvent(ResourceInfoAggregate resource) {
        super(resource, EventTypeEnums.RESOURCE_ADDED_TO_WHITE_LIST.getCode());
        this.resourceId = resource.getId();
    }

    public ResourceAddedToWhiteListEvent(ResourceId resourceId) {
        super(resourceId.getValue(), AggregateTypeEnums.RESOURCE.getCode(), EventTypeEnums.RESOURCE_ADDED_TO_WHITE_LIST.getCode());
        this.resourceId = resourceId;
    }
}

