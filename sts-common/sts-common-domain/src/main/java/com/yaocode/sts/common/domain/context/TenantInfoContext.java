package com.yaocode.sts.common.domain.context;

import com.yaocode.sts.common.basic.exception.DataNotFoundException;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;

import java.util.Objects;

/**
 * 租户信息上下文
 * @author: Jin-LiangBo
 * @date: 2026年04月14日 16:42
 */
public final class TenantInfoContext {

    private static final Logger logger = LoggerFactory.getLogger(TenantInfoContext.class);

    private static final NamedThreadLocal<TenantInfoContext> CURRENT_TENANT_INFO =
            new NamedThreadLocal<>("Current Tenant Info");

    private TenantId tenantId;

    public static TenantInfoContext getInstance() {
        if (Objects.isNull(CURRENT_TENANT_INFO.get())) {
            CURRENT_TENANT_INFO.set(new TenantInfoContext());
        }
        return CURRENT_TENANT_INFO.get();
    }

    // public static void preHandle(HttpServletRequest request) {
    //     getInstance();
    //     setUserId(request.getHeader(USER_ID_HEADER_KEY));
    //     setUserName(request.getHeader(USER_NAME_HEADER_KEY));
    //     setOrganizationList(request.getHeader(DEPT_LIST_HEADER_KEY));
    // }

    public static void reset() {
        CURRENT_TENANT_INFO.remove();
    }

    /**
     * 设置当前租户ID
     */
    public static void setTenantId(TenantId tenantId) {
        if (Objects.isNull(tenantId)) {
            throw new IllegalArgumentException("租户ID不能为空");
        }
        CURRENT_TENANT_INFO.get().tenantId = tenantId;
        logger.debug("设置当前租户: {}", tenantId);
    }

    /**
     * 获取租户ID
     */
    public static TenantId getTenantId() {
        TenantId tenantId = getInstance().tenantId;
        if (Objects.isNull(tenantId)) {
            throw new DataNotFoundException("未找到租户上下文，请检查请求头X-Tenant-Id");
        }
        return tenantId;
    }

}
