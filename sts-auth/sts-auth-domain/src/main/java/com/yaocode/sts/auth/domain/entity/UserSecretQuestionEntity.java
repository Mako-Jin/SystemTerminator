package com.yaocode.sts.auth.domain.entity;

import com.yaocode.sts.auth.domain.valueobjects.identifiers.SecretQuestionId;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Getter;

import java.util.Objects;

/**
 * 用户密保问题实体（属于用户聚合）
 * 对应表：auth_tbl_user_secret_question
 */
@Getter
public class UserSecretQuestionEntity {

    private final SecretQuestionId secretQuestionId;
    private final UserId userId;
    private TenantId tenantId;
    private String questionType;      // 问题类型（如：BIRTH_PLACE, FIRST_CAR）
    private String customQuestion;    // 自定义问题
    private String encryptedAnswer;   // AES加密存储
    private Integer sortOrder;

    private UserSecretQuestionEntity(SecretQuestionId secretQuestionId, UserId userId) {
        this.secretQuestionId = secretQuestionId;
        this.userId = userId;
    }

    // ========== 工厂方法 ==========

    public static UserSecretQuestionEntity create(
            UserId userId,
            TenantId tenantId,
            String questionType,
            String customQuestion,
            String encryptedAnswer,
            Integer sortOrder
    ) {
        if (questionType == null && customQuestion == null) {
            throw new IllegalArgumentException("问题类型或自定义问题至少提供一个");
        }
        if (encryptedAnswer == null || encryptedAnswer.trim().isEmpty()) {
            throw new IllegalArgumentException("答案不能为空");
        }
        UserSecretQuestionEntity entity = new UserSecretQuestionEntity(
                SecretQuestionId.nextId(), userId
        );
        entity.tenantId = tenantId;
        entity.questionType = questionType;
        entity.customQuestion = customQuestion;
        entity.encryptedAnswer = encryptedAnswer;
        entity.sortOrder = sortOrder != null ? sortOrder : 0;
        return entity;
    }

    public static UserSecretQuestionEntity reconstruct(
            SecretQuestionId secretQuestionId,
            UserId userId,
            TenantId tenantId,
            String questionType,
            String customQuestion,
            String encryptedAnswer,
            Integer sortOrder
    ) {
        UserSecretQuestionEntity entity = new UserSecretQuestionEntity(secretQuestionId, userId);
        entity.tenantId = tenantId;
        entity.questionType = questionType;
        entity.customQuestion = customQuestion;
        entity.encryptedAnswer = encryptedAnswer;
        entity.sortOrder = sortOrder != null ? sortOrder : 0;
        return entity;
    }

    // ========== 业务行为 ==========

    public void updateAnswer(String encryptedAnswer) {
        if (encryptedAnswer == null || encryptedAnswer.trim().isEmpty()) {
            throw new IllegalArgumentException("答案不能为空");
        }
        this.encryptedAnswer = encryptedAnswer;
    }

    public String getDisplayQuestion() {
        return customQuestion != null ? customQuestion : questionType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserSecretQuestionEntity that = (UserSecretQuestionEntity) o;
        return Objects.equals(secretQuestionId, that.secretQuestionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(secretQuestionId);
    }
}