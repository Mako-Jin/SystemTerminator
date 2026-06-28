package com.yaocode.sts.auth.domain.entity;

import com.yaocode.sts.auth.domain.enums.EmploymentStatusEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.EmploymentId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.OrganizationId;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;

/**
 * 用户工作经历实体（属于用户聚合）
 * 对应表：auth_tbl_user_employment
 */
@Getter
public class UserEmploymentEntity {

    private final EmploymentId employmentId;
    private final UserId userId;
    private TenantId tenantId;
    private String organization;          // 组织/公司名称
    private String division;              // 事业部
    private OrganizationId organizationId;  // 部门ID
    private String departmentName;        // 部门名称（冗余）
    private String employeeNumber;
    private String jobTitle;              // 职位
    private String jobLevel;              // 职级
    private String costCenter;
    private UserId managerId;
    private String managerName;           // 冗余
    private UserId assistantId;
    private String assistantName;         // 冗余
    private LocalDate entryDate;
    private LocalDate quitDate;
    private LocalDate probationEndDate;
    private LocalDate regularizationDate;
    private EmploymentStatusEnums employmentStatus;

    private UserEmploymentEntity(EmploymentId employmentId, UserId userId) {
        this.employmentId = employmentId;
        this.userId = userId;
    }

    // ========== 工厂方法 ==========

    public static UserEmploymentEntity create(
            UserId userId,
            TenantId tenantId,
            String organization,
            String division,
            OrganizationId organizationId,
            String departmentName,
            String employeeNumber,
            String jobTitle,
            String jobLevel,
            String costCenter,
            UserId managerId,
            String managerName,
            UserId assistantId,
            String assistantName,
            LocalDate entryDate,
            LocalDate probationEndDate,
            LocalDate regularizationDate
    ) {
        if (entryDate == null) {
            throw new IllegalArgumentException("入职日期不能为空");
        }
        UserEmploymentEntity entity = new UserEmploymentEntity(EmploymentId.nextId(), userId);
        entity.tenantId = tenantId;
        entity.organization = organization;
        entity.division = division;
        entity.organizationId = organizationId;
        entity.departmentName = departmentName;
        entity.employeeNumber = employeeNumber;
        entity.jobTitle = jobTitle;
        entity.jobLevel = jobLevel;
        entity.costCenter = costCenter;
        entity.managerId = managerId;
        entity.managerName = managerName;
        entity.assistantId = assistantId;
        entity.assistantName = assistantName;
        entity.entryDate = entryDate;
        entity.probationEndDate = probationEndDate;
        entity.regularizationDate = regularizationDate;
        entity.employmentStatus = EmploymentStatusEnums.PROBATION;
        return entity;
    }

    public static UserEmploymentEntity reconstruct(
            EmploymentId employmentId,
            UserId userId,
            TenantId tenantId,
            String organization,
            String division,
            OrganizationId organizationId,
            String departmentName,
            String employeeNumber,
            String jobTitle,
            String jobLevel,
            String costCenter,
            UserId managerId,
            String managerName,
            UserId assistantId,
            String assistantName,
            LocalDate entryDate,
            LocalDate quitDate,
            LocalDate probationEndDate,
            LocalDate regularizationDate,
            EmploymentStatusEnums employmentStatus
    ) {
        UserEmploymentEntity entity = new UserEmploymentEntity(employmentId, userId);
        entity.tenantId = tenantId;
        entity.organization = organization;
        entity.division = division;
        entity.organizationId = organizationId;
        entity.departmentName = departmentName;
        entity.employeeNumber = employeeNumber;
        entity.jobTitle = jobTitle;
        entity.jobLevel = jobLevel;
        entity.costCenter = costCenter;
        entity.managerId = managerId;
        entity.managerName = managerName;
        entity.assistantId = assistantId;
        entity.assistantName = assistantName;
        entity.entryDate = entryDate;
        entity.quitDate = quitDate;
        entity.probationEndDate = probationEndDate;
        entity.regularizationDate = regularizationDate;
        entity.employmentStatus = employmentStatus;
        return entity;
    }

    // ========== 业务行为 ==========

    public void regularize() {
        if (employmentStatus != EmploymentStatusEnums.PROBATION) {
            throw new IllegalStateException("只有试用期员工才能转正");
        }
        this.employmentStatus = EmploymentStatusEnums.ACTIVE;
        this.regularizationDate = LocalDate.now();
    }

    public void resign(LocalDate quitDate) {
        if (quitDate == null) {
            throw new IllegalArgumentException("离职日期不能为空");
        }
        if (quitDate.isBefore(entryDate)) {
            throw new IllegalArgumentException("离职日期不能早于入职日期");
        }
        this.quitDate = quitDate;
        this.employmentStatus = EmploymentStatusEnums.RESIGNED;
    }

    public void updatePosition(String jobTitle, String jobLevel) {
        this.jobTitle = jobTitle;
        this.jobLevel = jobLevel;
    }

    public void updateDepartment(OrganizationId organizationId, String departmentName) {
        this.organizationId = organizationId;
        this.departmentName = departmentName;
    }

    public void updateManager(UserId managerId, String managerName) {
        this.managerId = managerId;
        this.managerName = managerName;
    }

    public boolean isActive() {
        return employmentStatus != null && employmentStatus.isActive();
    }

    public boolean isProbation() {
        return employmentStatus == EmploymentStatusEnums.PROBATION;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEmploymentEntity that = (UserEmploymentEntity) o;
        return Objects.equals(employmentId, that.employmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employmentId);
    }
}
