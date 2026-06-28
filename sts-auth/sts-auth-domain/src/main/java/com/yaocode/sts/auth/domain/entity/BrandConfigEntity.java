package com.yaocode.sts.auth.domain.entity;

import com.yaocode.sts.auth.domain.enums.BrandTargetTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.composites.Branding;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.BrandConfigId;
import com.yaocode.sts.common.basic.enums.OppositeEnums;
import com.yaocode.sts.common.domain.model.AbstractAggregate;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import lombok.Getter;


/**
 * 品牌配置实体（属于租户聚合）
 * 对应表：auth_tbl_brand_config
 */
@Getter
public class BrandConfigEntity extends AbstractAggregate<BrandConfigId> {

    private final String brandConfigName;
    private final BrandTargetTypeEnums targetType;
    private final String targetId;          // 关联目标ID（租户ID/公司ID）
    private Branding branding;
    private OppositeEnums isEnabled;
    private Integer priority;
    private final String logoUrl;
    private final String loginTitle;
    private final String welcomeMessage;
    private final String subtitle;
    private final String institution;
    private final String copyright;
    private final String primaryColor;
    private final String loginBackgroundUrl;

    private BrandConfigEntity(
            BrandConfigId brandConfigId, String brandConfigName, BrandTargetTypeEnums targetType,
            String targetId, String logoUrl, String loginTitle, String welcomeMessage, String subtitle,
            String institution, String copyright, String primaryColor, String loginBackgroundUrl
    ) {
        super(brandConfigId);
        this.brandConfigName = brandConfigName;
        this.targetType = targetType;
        this.targetId = targetId;
        this.logoUrl = logoUrl;
        this.loginTitle = loginTitle;
        this.welcomeMessage = welcomeMessage;
        this.subtitle = subtitle;
        this.institution = institution;
        this.copyright = copyright;
        this.primaryColor = primaryColor;
        this.loginBackgroundUrl = loginBackgroundUrl;
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
        BrandConfigEntity entity = new BrandConfigEntity(
                BrandConfigId.nextId(), branding.getBrandName(), targetType, targetId,
                branding.getLogoUrl(), branding.getLoginTitle(), branding.getWelcomeMessage(),
                branding.getSubtitle(), branding.getInstitution(), branding.getCopyright(),
                branding.getPrimaryColor(), branding.getLoginBackgroundUrl()
        );
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
        BrandConfigEntity entity = new BrandConfigEntity(
                brandConfigId, branding.getBrandName(), targetType, targetId,
                branding.getLogoUrl(), branding.getLoginTitle(), branding.getWelcomeMessage(),
                branding.getSubtitle(), branding.getInstitution(), branding.getCopyright(),
                branding.getPrimaryColor(), branding.getLoginBackgroundUrl()
        );
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

}
