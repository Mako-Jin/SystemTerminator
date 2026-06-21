package com.yaocode.sts.auth.domain.entity;

import com.yaocode.sts.auth.domain.enums.BrandTargetTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.composites.Branding;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.BrandConfigId;
import com.yaocode.sts.common.basic.enums.OppositeEnums;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import lombok.Getter;

import java.util.Objects;

/**
 * 品牌配置实体（属于租户聚合）
 * 对应表：auth_tbl_brand_config
 */
@Getter
public class BrandConfigEntity {

    private final BrandConfigId brandConfigId;
    private final BrandTargetTypeEnums targetType;
    private final String targetId;          // 关联目标ID（租户ID/公司ID）
    private Branding branding;
    private OppositeEnums isEnabled;
    private Integer priority;

    private BrandConfigEntity(BrandConfigId brandConfigId, BrandTargetTypeEnums targetType, String targetId) {
        this.brandConfigId = brandConfigId;
        this.targetType = targetType;
        this.targetId = targetId;
        this.isEnabled = OppositeEnums.YES;
        this.priority = 0;
    }

    // ========== 工厂方法 ==========

    public static BrandConfigEntity create(
            BrandTargetTypeEnums targetType,
            String targetId,
            Branding branding,
            Integer priority
    ) {
        BrandConfigEntity entity = new BrandConfigEntity(BrandConfigId.nextId(), targetType, targetId);
        entity.branding = branding;
        entity.priority = priority != null ? priority : 0;
        return entity;
    }

    public static BrandConfigEntity createForTenant(TenantId tenantId, Branding branding) {
        return create(BrandTargetTypeEnums.TENANT, tenantId.getValue(), branding, 0);
    }

    public static BrandConfigEntity reconstruct(
            BrandConfigId brandConfigId,
            BrandTargetTypeEnums targetType,
            String targetId,
            Branding branding,
            OppositeEnums isEnabled,
            Integer priority
    ) {
        BrandConfigEntity entity = new BrandConfigEntity(brandConfigId, targetType, targetId);
        entity.branding = branding;
        entity.isEnabled = isEnabled != null ? isEnabled : OppositeEnums.YES;
        entity.priority = priority != null ? priority : 0;
        return entity;
    }

    // ========== 业务行为 ==========

    public void updateBranding(Branding branding) {
        this.branding = branding;
    }

    public void enable() {
        this.isEnabled = OppositeEnums.YES;
    }

    public void disable() {
        this.isEnabled = OppositeEnums.NO;
    }

    public void updatePriority(Integer priority) {
        this.priority = priority != null ? priority : 0;
    }

    public boolean isEnabled() {
        return isEnabled == OppositeEnums.YES;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BrandConfigEntity that = (BrandConfigEntity) o;
        return Objects.equals(brandConfigId, that.brandConfigId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brandConfigId);
    }
}
