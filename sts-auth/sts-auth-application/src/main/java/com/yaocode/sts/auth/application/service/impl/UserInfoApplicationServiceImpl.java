package com.yaocode.sts.auth.application.service.impl;

import com.yaocode.sts.auth.application.converter.UserInfoApplicationConverter;
import com.yaocode.sts.auth.application.dto.UserInfoDto;
import com.yaocode.sts.auth.application.dto.UserRegistrationDto;
import com.yaocode.sts.auth.application.enums.AuthErrorCodeEnums;
import com.yaocode.sts.auth.application.exception.AuthServerException;
import com.yaocode.sts.auth.application.service.UserInfoApplicationService;
import com.yaocode.sts.auth.domain.entity.UserInfoEntity;
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
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        // 这四个验证能不能写成一个事件？
        // 验证租户存不存在
        TenantId tenantId = TenantId.of(userInfoDto.getTenantId());
        if (!tenantDomainService.validateTenantId(tenantId)) {
            throw new IllegalArgumentException("auth.params.data.not.exists");
        }
        // 验证组织存不存在
        OrganizationId organizationId = OrganizationId.of(userInfoDto.getOrganizationId());
        if (!organizationDomainService.validateOrganizationId(organizationId)) {
            throw new IllegalArgumentException("auth.params.data.not.exists");
        }
        // 验证用户组存不存在
        UserGroupId userGroupId = UserGroupId.of(userInfoDto.getUserGroupId());
        if (!userGroupDomainService.validateUserGroupId(userGroupId)) {
            throw new IllegalArgumentException("auth.params.data.not.exists");
        }
        // 验证角色存不存在
        List<RoleId> roleIdList = userInfoDto.getRoleIdList().stream().map(RoleId::of).distinct().toList();
        if (!roleDomainService.validateRoleId(roleIdList)) {
            throw new IllegalArgumentException("auth.params.data.not.exists");
        }
        // DTO转为DO
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
        // tenantDomainService.associatedTenant(tenantId, userId);
        // // 关联组织机构信息
        // organizationDomainService.associatedOrganization(tenantId, userId);
        // // 关联租户信息
        // userGroupDomainService.associatedUserGroup(tenantId, userId);
        // // 分配权限，是不是还得查一下租户或者组织机构下默认权限，进行分配
        // roleDomainService.associatedRole(tenantId, userId);
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
