package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.yaocode.sts.common.infrastructure.po.BasePo;


@Data
@TableName("auth_tbl_user_emergency_contact")
@EqualsAndHashCode(callSuper = true)
public class UserEmergencyContactPo extends BasePo {

    @TableId
    private String emergencyContactId;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 租户ID
     */
    private String tenantId;
    /**
     * 紧急联系人姓名
     */
    private String contactName;
    /**
     * 关系（父母/配偶/朋友）
     */
    private Integer relationship;
    /**
     * 联系电话
     */
    private String phoneNum;
    /**
     * 备用邮箱
     */
    private String email;
    /**
     * 排序（支持多个紧急联系人）
     */
    private Integer sortOrder;
}
