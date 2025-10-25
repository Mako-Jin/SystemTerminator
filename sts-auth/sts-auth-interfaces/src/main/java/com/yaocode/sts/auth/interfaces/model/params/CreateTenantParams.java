package com.yaocode.sts.auth.interfaces.model.params;

import com.yaocode.sts.common.basic.enums.OppositeEnums;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

/**
 * 新增租户参数类
 * @author: Jin-LiangBo
 * @date: 2025年10月16日 22:41
 */
@Data
public class CreateTenantParams {
    /**
     * 租户名称
     */
    @NotBlank(message = "租户名称不能为空")
    private String tenantName;
    /**
     * 租户编码
     */
    @NotBlank(message = "租户编码不能为空")
    private String tenantCode;
    /**
     * 租户描述
     */
    private String tenantDesc;
    /**
     * 是否允许注册新用户
     */
    @Range(min = 0, max = 1, message = "allowRegister只支持0和1")
    private Integer allowRegister = OppositeEnums.NO.getCode();
    /**
     * 是否允许新增新用户
     */
    @Range(min = 0, max = 1, message = "allowAdd只支持0和1")
    private Integer allowAdd = OppositeEnums.YES.getCode();
    /**
     * 父id
     */
    private String parentId;

}
