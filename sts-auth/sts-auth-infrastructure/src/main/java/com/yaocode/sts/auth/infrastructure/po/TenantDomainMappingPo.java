package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yaocode.sts.common.infrastructure.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 租户域名映射表
 * 支持租户自定义域名
 */
@Data
@TableName("auth_tbl_tenant_domain_mapping")
@EqualsAndHashCode(callSuper = true)
public class TenantDomainMappingPo extends BasePo {

    @TableId
    private String mappingId;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 域名（完整域名，支持泛域名）
     * 示例：yaocode.yourdomain.com, mycompany.com, *.yourdomain.com
     */
    private String domain;

    /**
     * 是否为该租户的主域名
     */
    private Integer isPrimary;

    /**
     * 域名类型：
     * SYSTEM - 系统分配（如 {tenantCode}.yourdomain.com）
     * CUSTOM - 自定义域名
     */
    private Integer domainType;

    /**
     * 是否启用
     */
    private Integer isEnabled;

    /**
     * 验证状态（自定义域名需要验证所有权）
     * 0-未验证, 1-已验证, 2-验证失败
     */
    private Integer verifiedStatus;

    /**
     * 验证Token（用于DNS验证）
     */
    private String verifyToken;

    /**
     * 备注
     */
    private String remark;
}
