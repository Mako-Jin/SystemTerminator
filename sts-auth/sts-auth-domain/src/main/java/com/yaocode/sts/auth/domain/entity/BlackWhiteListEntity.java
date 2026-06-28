package com.yaocode.sts.auth.domain.entity;

import com.yaocode.sts.auth.domain.enums.BlackWhiteListTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.BlackWhiteListId;
import com.yaocode.sts.common.basic.enums.OppositeEnums;
import com.yaocode.sts.common.domain.model.AbstractAggregate;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BlackWhiteListEntity extends AbstractAggregate<BlackWhiteListId> {

    private TenantId tenantId;           // null 表示平台级
    private BlackWhiteListTypeEnums listType;      // IP, DEVICE, CLIENT, USER
    private String listValue;
    private OppositeEnums action;      // ALLOW, DENY
    private Integer priority;
    private LocalDateTime effectiveFrom;
    private LocalDateTime effectiveTo;
    private OppositeEnums isEnabled;
    private String remark;

    private BlackWhiteListEntity(BlackWhiteListId id) {
        super(id);
        this.isEnabled = OppositeEnums.YES;
        this.priority = 0;
    }

    public static BlackWhiteListEntity create(
            TenantId tenantId,
            BlackWhiteListTypeEnums listType,
            String listValue,
            OppositeEnums action,
            Integer priority,
            LocalDateTime effectiveFrom,
            LocalDateTime effectiveTo,
            String remark
    ) {
        BlackWhiteListEntity entity = new BlackWhiteListEntity(BlackWhiteListId.nextId());
        entity.tenantId = tenantId;
        entity.listType = listType;
        entity.listValue = listValue;
        entity.action = action;
        entity.priority = priority != null ? priority : 0;
        entity.effectiveFrom = effectiveFrom;
        entity.effectiveTo = effectiveTo;
        entity.remark = remark;
        return entity;
    }

    public boolean isEffective() {
        LocalDateTime now = LocalDateTime.now();
        if (isEnabled == OppositeEnums.NO) return false;
        if (effectiveFrom != null && now.isBefore(effectiveFrom)) return false;
        return effectiveTo == null || !now.isAfter(effectiveTo);
    }

    public boolean matches(String value) {
        if (listValue == null) return false;
        return listValue.equals(value) || value.matches(listValue.replace("*", ".*"));
    }

    public void enable() { this.isEnabled = OppositeEnums.YES; }
    public void disable() { this.isEnabled = OppositeEnums.NO; }

}
