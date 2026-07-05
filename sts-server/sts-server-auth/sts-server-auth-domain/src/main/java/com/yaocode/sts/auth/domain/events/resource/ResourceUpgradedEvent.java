package com.yaocode.sts.auth.domain.events.resource;

import com.yaocode.sts.auth.domain.aggregate.ResourceInfoAggregate;
import com.yaocode.sts.auth.domain.enums.AggregateTypeEnums;
import com.yaocode.sts.auth.domain.enums.EventTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ResourceId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.Version;
import com.yaocode.sts.common.domain.event.AggregateDomainEvent;
import lombok.Getter;

/**
 * 资源升级事件
 */
@Getter
public class ResourceUpgradedEvent extends AggregateDomainEvent<ResourceId> {

    private final ResourceId resourceId;
    private final Version newVersion;

    public ResourceUpgradedEvent(ResourceInfoAggregate resource, Version newVersion) {
        super(resource, EventTypeEnums.RESOURCE_UPGRADED.getCode());
        this.resourceId = resource.getId();
        this.newVersion = newVersion;
    }

    public ResourceUpgradedEvent(ResourceId resourceId, Version newVersion) {
        super(resourceId.getValue(), AggregateTypeEnums.RESOURCE.getCode(), EventTypeEnums.RESOURCE_UPGRADED.getCode());
        this.resourceId = resourceId;
        this.newVersion = newVersion;
    }
}
