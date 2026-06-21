package com.yaocode.sts.auth.domain.entity;

import com.yaocode.sts.auth.domain.enums.DegreeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.EducationId;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;

/**
 * 用户教育经历实体（属于用户聚合）
 * 对应表：auth_tbl_user_education
 */
@Getter
public class UserEducationEntity {

    private final EducationId educationId;
    private final UserId userId;
    private TenantId tenantId;
    private String schoolName;
    private DegreeEnums degree;
    private String major;
    private LocalDate startDate;
    private LocalDate endDate;
    private String diplomaNo;
    private Integer sortOrder;

    private UserEducationEntity(EducationId educationId, UserId userId) {
        this.educationId = educationId;
        this.userId = userId;
    }

    // ========== 工厂方法 ==========

    public static UserEducationEntity create(
            UserId userId,
            TenantId tenantId,
            String schoolName,
            DegreeEnums degree,
            String major,
            LocalDate startDate,
            LocalDate endDate,
            String diplomaNo,
            Integer sortOrder
    ) {
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("入学日期不能晚于毕业日期");
        }
        UserEducationEntity entity = new UserEducationEntity(EducationId.nextId(), userId);
        entity.tenantId = tenantId;
        entity.schoolName = schoolName;
        entity.degree = degree;
        entity.major = major;
        entity.startDate = startDate;
        entity.endDate = endDate;
        entity.diplomaNo = diplomaNo;
        entity.sortOrder = sortOrder;
        return entity;
    }

    public static UserEducationEntity reconstruct(
            EducationId educationId,
            UserId userId,
            TenantId tenantId,
            String schoolName,
            DegreeEnums degree,
            String major,
            LocalDate startDate,
            LocalDate endDate,
            String diplomaNo,
            Integer sortOrder
    ) {
        UserEducationEntity entity = new UserEducationEntity(educationId, userId);
        entity.tenantId = tenantId;
        entity.schoolName = schoolName;
        entity.degree = degree;
        entity.major = major;
        entity.startDate = startDate;
        entity.endDate = endDate;
        entity.diplomaNo = diplomaNo;
        entity.sortOrder = sortOrder;
        return entity;
    }

    // ========== 业务行为 ==========

    public void updateEndDate(LocalDate endDate) {
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("毕业日期不能早于入学日期");
        }
        this.endDate = endDate;
    }

    public boolean isInProgress() {
        return endDate == null || endDate.isAfter(LocalDate.now());
    }

    public boolean isGraduated() {
        return endDate != null && endDate.isBefore(LocalDate.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEducationEntity that = (UserEducationEntity) o;
        return Objects.equals(educationId, that.educationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(educationId);
    }
}
