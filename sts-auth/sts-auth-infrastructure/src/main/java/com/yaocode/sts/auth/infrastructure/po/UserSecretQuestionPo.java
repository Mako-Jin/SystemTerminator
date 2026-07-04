package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yaocode.sts.common.infrastructure.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@TableName("auth_tbl_user_secret_question")
@EqualsAndHashCode(callSuper = true)
public class UserSecretQuestionPo extends BasePo {

    @TableId
    private String questionId;

    /**
     * 用户ID
     */
    private String userId;
    /**
     * 租户ID
     */
    private String tenantId;
    /**
     * 问题类型（可配置的问题列表）
     * 如：您的出生地是？、您的第一辆车品牌是？
     */
    private String questionType;

    /**
     * 自定义问题（如果问题类型是CUSTOM）
     */
    private String customQuestion;

    /**
     * 答案（AES加密存储）
     */
    private String encryptedAnswer;

    /**
     * 排序号（支持多个密保问题）
     */
    private Integer sortOrder;
}
