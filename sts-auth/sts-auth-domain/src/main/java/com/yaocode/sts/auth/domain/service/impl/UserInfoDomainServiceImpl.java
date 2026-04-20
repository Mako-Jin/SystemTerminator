package com.yaocode.sts.auth.domain.service.impl;

import com.yaocode.sts.auth.domain.command.CreateUserCommand;
import com.yaocode.sts.auth.domain.entity.UserInfoEntity;
import com.yaocode.sts.auth.domain.repository.UserInfoRepository;
import com.yaocode.sts.auth.domain.service.OrganizationDomainService;
import com.yaocode.sts.auth.domain.service.RoleDomainService;
import com.yaocode.sts.auth.domain.service.TenantDomainService;
import com.yaocode.sts.auth.domain.service.UserGroupDomainService;
import com.yaocode.sts.auth.domain.service.UserInfoDomainService;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.OrganizationId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.RoleId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserGroupId;
import com.yaocode.sts.common.basic.enums.OppositeEnums;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import com.yaocode.sts.common.domain.valueobject.UserId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.Username;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 用户信息领域服务
 * @author: Jin-LiangBo
 * @date: 2025年10月07日 23:18
 */
@Service
public class UserInfoDomainServiceImpl implements UserInfoDomainService {

    @Resource
    private UserInfoRepository userInfoRepository;

    @Resource
    private TenantDomainService tenantDomainService;

    @Resource
    private OrganizationDomainService organizationDomainService;

    @Resource
    private UserGroupDomainService userGroupDomainService;

    @Resource
    private RoleDomainService roleDomainService;

    @Override
    public UserInfoEntity createUser(CreateUserCommand command) {
        // 1. 验证命令参数
        command.validate();
        if (!tenantDomainService.validateTenantId(command.getTenantIdList())) {
            throw new IllegalArgumentException("租户不存在: " + command.getTenantIdList().stream()
                    .map(TenantId::getValue)
                    .collect(Collectors.joining(",", "[", "]"))
            );
        }
        // 3. 验证组织是否存在（如果提供了组织ID）
        if (!CollectionUtils.isEmpty(command.getOrganizationIdList())) {
            for (TenantId tenantId : command.getTenantIdList()) {
                boolean exist = organizationDomainService.validateOrganizationId(
                        tenantId,
                        command.getOrganizationIdList()
                );
                if (!exist) {
                    throw new IllegalArgumentException("用户组不存在: " + tenantId.getValue() + command.getUserGroupIdList().stream()
                            .map(UserGroupId::getValue)
                            .collect(Collectors.joining(",", "[", "]"))
                    );
                }

            }
        }

        // 4. 验证用户组是否存在（如果提供了用户组ID）
        if (!CollectionUtils.isEmpty(command.getUserGroupIdList())) {
            for (TenantId tenantId : command.getTenantIdList()) {
                boolean exist = userGroupDomainService.validateUserGroupId(
                        tenantId,
                        command.getUserGroupIdList()
                );
                if (!exist) {
                    throw new IllegalArgumentException("组织不存在: " + tenantId.getValue() + command.getOrganizationIdList().stream()
                            .map(OrganizationId::getValue)
                            .collect(Collectors.joining(",", "[", "]"))
                    );
                }

            }
        }

        // 5. 验证角色是否存在（如果提供了角色ID列表）
        if (command.getRoleIdList() != null && !command.getRoleIdList().isEmpty()) {
            for (TenantId tenantId : command.getTenantIdList()) {
                boolean exist = roleDomainService.validateRoleId(
                        tenantId,
                        command.getRoleIdList()
                );
                if (!exist) {
                    throw new IllegalArgumentException("角色不存在: " + tenantId.getValue() + command.getRoleIdList().stream()
                            .map(RoleId::getValue)
                            .collect(Collectors.joining(",", "[", "]"))
                    );
                }

            }
        }

        // 6. 验证用户名在租户内唯一
        isUsernameUnique(command.getUsername(), command.getTenantIdList());

        // ✅ 2. 生成默认密码
        // String defaultPassword = passwordPolicy.generateDefaultPassword();
        // Password encryptedPassword = Password.encode(defaultPassword, passwordEncoder);

        // ✅ 3. 通过实体工厂方法创建（触发领域事件）
        UserInfoEntity user = UserInfoEntity.build(
                command.getUsername(),
                command.getTenantIdList(),
                command.getOrganizationIdList(),
                command.getRoleIdList(),
                command.getUserGroupIdList(),
                command.getEmail(),
                command.getPhoneNum(),
                OppositeEnums.YES.getCode()
        );

        // ✅ 4. 分配角色（触发角色分配事件）
        // 分配角色得查询租户，组织，用户组，关联的默认基础角色，或者权限
        // if (!command.getRoleIds().isEmpty()) {
        //     user.assignRoles(command.getRoleIds());
        // }

        return user;
    }


    @Override
    public boolean isUsernameUnique(Username username, TenantId tenantId) {
        return userInfoRepository.findByUsername(tenantId, username).isEmpty();
    }

    @Override
    public boolean isUsernameUnique(Username username, List<TenantId> tenantIdList) {
        return userInfoRepository.findByUsernameInTenantIdList(tenantIdList, username).isEmpty();
    }

    @Override
    public void validateUserStatus(UserInfoEntity user) {

    }

    @Override
    public boolean validateUser(TenantId tenantId, UserId userId) {
        Optional<UserInfoEntity> userInfoEntityOptional = userInfoRepository.findById(tenantId, userId);
        return userInfoEntityOptional.isPresent();
    }
}
