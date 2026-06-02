package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

// 用户证件表（支持多个证件）
@Data
@TableName("auth_tbl_user_credential")
@EqualsAndHashCode(callSuper = true)
public class UserCredentialPo extends BasePo {
    @TableId
    private String id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 证件类型：IDCARD-身份证、PASSPORT-护照、STUDENT_CARD-学生证、MILITARY_CARD-军人证
     */
    private String credentialType;

    /**
     * 证件号码（加密存储）
     */
    private String credentialNumber;

    /**
     * 证件姓名（与证件上一致）
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
    private Integer primary;

    /**
     * 证件照片/扫描件路径
     */
    private String credentialImage;
}