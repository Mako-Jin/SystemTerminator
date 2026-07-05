package com.yaocode.sts.auth.domain.command;

import com.yaocode.sts.auth.domain.valueobjects.identifiers.OrganizationId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.RoleId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserGroupId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.Email;
import com.yaocode.sts.auth.domain.valueobjects.primitives.PhoneNum;
import com.yaocode.sts.auth.domain.valueobjects.primitives.Username;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 创建用户指令
 * @author: Jin-LiangBo
 * @date: 2026年04月20日 14:20
 */
@Getter
@Builder
public class CreateUserCommand {

    private final Username username;
    private final List<TenantId> tenantIdList;
    private final Email email;
    private final PhoneNum phoneNum;

    private final List<OrganizationId> organizationIdList;
    private final List<UserGroupId> userGroupIdList;
    private final List<RoleId> roleIdList;

    public CreateUserCommand(
            Username username, List<TenantId> tenantIdList, Email email,
            PhoneNum phoneNum, List<OrganizationId> organizationIdList,
            List<UserGroupId> userGroupIdList, List<RoleId> roleIdList
    ) {
        this.username = username;
        this.tenantIdList = tenantIdList;
        this.email = email;
        this.phoneNum = phoneNum;
        this.organizationIdList = organizationIdList;
        this.userGroupIdList = userGroupIdList;
        this.roleIdList = roleIdList;
    }

    public void validate() {
        if (username == null) {
            throw new IllegalArgumentException("用户名不能为空");
        }
        if (CollectionUtils.isEmpty(tenantIdList)) {
            throw new IllegalArgumentException("租户ID不能为空");
        }
        if (email == null) {
            throw new IllegalArgumentException("邮箱不能为空");
        }
        if (phoneNum == null) {
            throw new IllegalArgumentException("手机号不能为空");
        }
    }

}
