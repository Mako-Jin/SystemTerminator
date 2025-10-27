package com.yaocode.sts.auth.application.service.impl;

import com.yaocode.sts.auth.application.converter.UserInfoApplicationConverter;
import com.yaocode.sts.auth.application.dto.UserInfoDto;
import com.yaocode.sts.auth.application.dto.UserRegistrationDto;
import com.yaocode.sts.auth.application.enums.AuthErrorCodeEnums;
import com.yaocode.sts.auth.application.exception.AuthServerException;
import com.yaocode.sts.auth.application.service.UserInfoApplicationService;
import com.yaocode.sts.auth.domain.entity.UserInfoEntity;
import com.yaocode.sts.auth.domain.enums.UserAddTypeEnums;
import com.yaocode.sts.auth.domain.repository.RoleInfoRepository;
import com.yaocode.sts.auth.domain.repository.UserInfoRepository;
import com.yaocode.sts.auth.domain.service.OrganizationDomainService;
import com.yaocode.sts.auth.domain.service.RoleDomainService;
import com.yaocode.sts.auth.domain.service.TenantDomainService;
import com.yaocode.sts.auth.domain.service.UserGroupDomainService;
import com.yaocode.sts.auth.domain.service.UserInfoDomainService;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.OrganizationId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.RoleId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserGroupId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.Username;
import com.yaocode.sts.common.tools.id.IdFactory;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 用户信息业务应用层
 * @author: Jin-LiangBo
 * @date: 2025年10月07日 22:02
 */
@Service
public class UserInfoApplicationServiceImpl implements UserInfoApplicationService {

    @Resource
    private UserInfoDomainService userInfoDomainService;

    @Resource
    private TenantDomainService tenantDomainService;

    @Resource
    private RoleDomainService roleDomainService;

    @Resource
    private UserGroupDomainService userGroupDomainService;

    @Resource
    private OrganizationDomainService organizationDomainService;

    @Resource
    private UserInfoRepository userInfoRepository;

    @Resource
    private RoleInfoRepository roleInfoRepository;

    @Resource
    private UserInfoApplicationConverter userInfoApplicationConverter;

    @Override
    public UserInfoDto getUserById(String userId) {
        UserInfoEntity user = userInfoRepository.findById(UserId.of(userId))
                .orElseThrow(() -> new AuthServerException(AuthErrorCodeEnums.USER_NOT_FOUND));
        return userInfoApplicationConverter.toDto(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String singleAdd(UserInfoDto userInfoDto) {
        // 验证租户存不存在
        TenantId tenantId = TenantId.of(userInfoDto.getTenantId());
        if (!tenantDomainService.validateTenantId(tenantId)) {
            throw new IllegalArgumentException("auth.params.data.not.exists");
        }
        // 验证组织存不存在
        OrganizationId organizationId = null;
        if (Objects.nonNull(userInfoDto.getOrganizationId())) {
            organizationId = OrganizationId.of(userInfoDto.getOrganizationId());
            if (!organizationDomainService.validateOrganizationId(tenantId, organizationId)) {
                throw new IllegalArgumentException("auth.params.data.not.exists");
            }
        }
        // 验证用户组存不存在
        UserGroupId userGroupId = null;
        if (Objects.nonNull(userInfoDto.getUserGroupId())) {
            userGroupId = UserGroupId.of(userInfoDto.getUserGroupId());
            if (!userGroupDomainService.validateUserGroupId(userGroupId)) {
                throw new IllegalArgumentException("auth.params.data.not.exists");
            }
        }

        // 验证角色存不存在
        List<RoleId> roleIdList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(userInfoDto.getRoleIdList())) {
            roleIdList = userInfoDto.getRoleIdList().stream().map(RoleId::of).distinct().toList();
            if (!roleDomainService.validateRoleId(roleIdList)) {
                throw new IllegalArgumentException("auth.params.data.not.exists");
            }
        }

        // 验证租户内，用户名是否唯一
        Username username = Username.of(userInfoDto.getUsername());
        if (!userInfoDomainService.isUsernameUnique(username, tenantId)) {
            throw new IllegalArgumentException("当前用户名已经存在");
        }

        // DTO转为DO
        userInfoDto.setUserId(IdFactory.generate().toString());
        UserInfoEntity userInfoEntity = userInfoApplicationConverter.toEntity(userInfoDto);
        // 设置默认密码
        // String defaultPassword = passwordPolicy.generateDefaultPassword();
        // String encryptedPassword = passwordEncoder.encode(defaultPassword);
        // userInfoEntity.setPassword(encryptedPassword);
        // // 标记需要首次登录修改密码
        // userInfoEntity.setNeedPasswordChange(true);
        // 保存用户信息
        UserId userId = userInfoRepository.save(userInfoEntity);
        // 关联租户信息
        tenantDomainService.associatedTenantUser(tenantId, userId, UserAddTypeEnums.ADD);
        if (Objects.nonNull(organizationId)) {
            // 关联组织机构信息
            organizationDomainService.associatedOrganizationUser(tenantId, organizationId, userId);
        }
        if (Objects.nonNull(userGroupId)) {
            // 关联租户信息
            userGroupDomainService.associatedUserGroupUser(tenantId, userGroupId, userId);
        }
        // 默认权限
        Optional<RoleId> defaultRoleId = roleInfoRepository.getDefaultRole(tenantId);
        if (defaultRoleId.isPresent()) {
            roleIdList.add(defaultRoleId.get());
        }
        // TODO 用户组和角色关联之后，用户组id不为空的时候，可能还得添加上用户组带的角色
        // 分配权限
        if (!CollectionUtils.isEmpty(roleIdList)) {
            roleDomainService.associatedRole(tenantId, userId, roleIdList);
        }
        // 发布用户新建的领域事件
        // domainEventPublisher.publishEvent(new UserCreateEvent(saveAuthorizeDO));
        return userId.getValue();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String register(UserRegistrationDto userRegistrationDto) {
        // // 验证租户是否存在且允许注册
        // Tenant tenant = tenantService.validateTenantForRegistration(command.tenantId());
        //
        // // 创建用户
        // User user = User.builder()
        //         .id(UserId.nextId())
        //         .tenantId(command.tenantId())
        //         .username(command.username())
        //         .email(command.email())
        //         .password(passwordEncoder.encode(command.password()))
        //         .personalInfo(command.personalInfo())
        //         .status(UserStatus.ACTIVE)
        //         .build();
        //
        // // 保存用户
        // userRepository.save(user);
        //
        // // 发布用户创建事件
        // user.registerEvent(new UserCreatedEvent(
        //         user.getId(),
        //         user.getTenantId(),
        //         user.getUsername(),
        //         user.getEmail()
        // ));
        //
        // // 发布所有领域事件
        // user.publishEvents(eventPublisher);
        //
        // log.info("用户注册成功: userId={}, username={}", user.getId(), user.getUsername());
        //
        // return user.getId();
        return null;
    }

    // public void changeUserPassword(ChangePasswordCommand command) {
    //     User user = userRepository.findById(command.userId())
    //             .orElseThrow(() -> new UserNotFoundException("用户不存在"));
    //
    //     // 验证原密码
    //     if (!passwordEncoder.matches(command.oldPassword(), user.getPassword())) {
    //         throw new InvalidPasswordException("原密码错误");
    //     }
    //
    //     // 修改密码
    //     user.changePassword(passwordEncoder.encode(command.newPassword()));
    //
    //     // 发布密码修改事件
    //     user.registerEvent(new UserPasswordChangedEvent(
    //             user.getId(),
    //             command.isForcedChange()
    //     ));
    //
    //     userRepository.save(user);
    //     user.publishEvents(eventPublisher);
    //
    //     log.info("用户密码修改成功: userId={}", user.getId());
    // }

}
