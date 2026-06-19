package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.yaocode.sts.common.infrastructure.po.BasePo;

// 用户证件表（支持多个证件）
@Data
@TableName("auth_tbl_user_credential")
@EqualsAndHashCode(callSuper = true)
public class UserCredentialPo extends BasePo {
    @TableId
    private String credentialId;

    /**
     * 用户ID
     */
    private String userId;
    /**
     * 租户ID
     */
    private String tenantId;
    /**
     * 证件类型：IDCARD-身份证、PASSPORT-护照、STUDENT_CARD-学生证、MILITARY_CARD-军人证
     */
    private Integer credentialType;

    /**
     * 证件号码（AES加密存储）
     */
    private String encryptedCredentialNumber;

    /**
     * 证件姓名（与证件一致）
     */
    private String credentialName;

    /**
     * 签发日期
     */
    private String issueDate;

    /**
     * 有效期至
     */
    private String expireDate;

    /**
     * 是否主证件：0-否、1-是
     */
    private Integer isPrimary;

    /**
     * 证件照片/扫描件路径
     */
    private String credentialImage;
}