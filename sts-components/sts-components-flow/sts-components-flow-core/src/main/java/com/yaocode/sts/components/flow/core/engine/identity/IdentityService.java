package com.yaocode.sts.components.flow.core.engine.identity;

import java.util.List;

/**
 * 身份服务接口
 * <p>
 * 管理用户、组、租户以及它们之间的关系，包括：
 * <ul>
 *   <li>用户的创建、更新、删除、查询</li>
 *   <li>组的创建、更新、删除、查询</li>
 *   <li>用户-组关系的管理（成员资格）</li>
 *   <li>租户的创建、更新、删除、查询</li>
 *   <li>用户-租户、组-租户关系的管理</li>
 *   <li>用户密码管理和密码策略验证</li>
 *   <li>用户认证信息管理</li>
 *   <li>用户头像管理</li>
 *   <li>用户扩展信息管理（键值对）</li>
 * </ul>
 *
 * @author Process Engine Team
 * @version 1.0.0
 */
public interface IdentityService {

    // ========================================================================
    // 一、只读状态检查
    // ========================================================================

    /**
     * 检查身份服务实现是否为只读模式
     * <p>
     * 只读模式下，以下操作将抛出 {@link UnsupportedOperationException}：
     * <ul>
     *   <li>创建/保存/删除用户</li>
     *   <li>创建/保存/删除组</li>
     *   <li>创建/保存/删除租户</li>
     *   <li>创建/删除成员资格</li>
     *   <li>创建/删除租户成员资格</li>
     * </ul>
     *
     * @return true 表示只读，false 表示可读写
     */
    boolean isReadOnly();

    // ========================================================================
    // 二、用户管理
    // ========================================================================

    /**
     * 创建新用户实例（瞬态对象）
     * <p>
     * 返回的用户对象是瞬态的，必须通过 {@link #saveUser(User)} 保存到数据库
     *
     * @param userId 用户ID（不能为空）
     * @return 新创建的用户实例
     * @throws UnsupportedOperationException 如果身份服务实现为只读模式
     */
    User newUser(String userId);

    /**
     * 保存用户
     * <p>
     * 如果用户已存在则更新，否则创建新用户
     *
     * @param user 用户对象（不能为空）
     * @throws UnsupportedOperationException 如果身份服务实现为只读模式
     */
    void saveUser(User user);

    /**
     * 删除用户
     * <p>
     * 如果用户不存在，操作被忽略
     *
     * @param userId 用户ID（不能为空）
     * @throws UnsupportedOperationException 如果身份服务实现为只读模式
     */
    void deleteUser(String userId);

    /**
     * 解锁用户账户
     * <p>
     * 当用户因安全策略被锁定时，使用此方法解锁
     *
     * @param userId 用户ID（不能为空）
     */
    void unlockUser(String userId);

    /**
     * 查询用户
     *
     * @param userId 用户ID
     * @return 用户对象，不存在时返回null
     */
    User getUser(String userId);

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户对象，不存在时返回null
     */
    User getUserByUsername(String username);

    /**
     * 查询所有用户
     *
     * @return 用户列表
     */
    List<User> getAllUsers();

    /**
     * 分页查询用户
     *
     * @param page 页码（从0开始）
     * @param size 每页大小
     * @return 用户列表
     */
    List<User> getUsers(int page, int size);

    /**
     * 创建用户查询构建器
     *
     * @return 用户查询构建器
     */
    UserQuery createUserQuery();

    /**
     * 创建原生SQL用户查询构建器
     *
     * @return 原生SQL用户查询构建器
     */
    NativeUserQuery createNativeUserQuery();

    // ========================================================================
    // 三、组管理
    // ========================================================================

    /**
     * 创建新组实例（瞬态对象）
     *
     * @param groupId 组ID（不能为空）
     * @return 新创建的组实例
     * @throws UnsupportedOperationException 如果身份服务实现为只读模式
     */
    Group newGroup(String groupId);

    /**
     * 保存组
     * <p>
     * 如果组已存在则更新，否则创建新组
     *
     * @param group 组对象（不能为空）
     * @throws UnsupportedOperationException 如果身份服务实现为只读模式
     */
    void saveGroup(Group group);

    /**
     * 删除组
     * <p>
     * 如果组不存在，操作被忽略
     *
     * @param groupId 组ID（不能为空）
     * @throws UnsupportedOperationException 如果身份服务实现为只读模式
     */
    void deleteGroup(String groupId);

    /**
     * 查询组
     *
     * @param groupId 组ID
     * @return 组对象，不存在时返回null
     */
    Group getGroup(String groupId);

    /**
     * 查询所有组
     *
     * @return 组列表
     */
    List<Group> getAllGroups();

    /**
     * 查询用户所属的所有组
     *
     * @param userId 用户ID
     * @return 组列表
     */
    List<Group> getGroupsByUser(String userId);

    /**
     * 创建组查询构建器
     *
     * @return 组查询构建器
     */
    GroupQuery createGroupQuery();

    // ========================================================================
    // 四、用户-组关系管理（成员资格）
    // ========================================================================

    /**
     * 创建用户和组的成员资格关系
     * <p>
     * 将用户添加到组中
     *
     * @param userId  用户ID（不能为空）
     * @param groupId 组ID（不能为空）
     * @throws RuntimeException 如果用户或组不存在，或用户已是组成员
     * @throws UnsupportedOperationException 如果身份服务实现为只读模式
     */
    void createMembership(String userId, String groupId);

    /**
     * 删除用户和组的成员资格关系
     * <p>
     * 从组中移除用户。如果用户或组不存在，或用户不是组成员，操作被忽略
     *
     * @param userId  用户ID（不能为空）
     * @param groupId 组ID（不能为空）
     * @throws UnsupportedOperationException 如果身份服务实现为只读模式
     */
    void deleteMembership(String userId, String groupId);

    /**
     * 将用户添加到组
     *
     * @param userId  用户ID（不能为空）
     * @param groupId 组ID（不能为空）
     * @throws UnsupportedOperationException 如果身份服务实现为只读模式
     */
    void addUserToGroup(String userId, String groupId);

    /**
     * 从组中移除用户
     *
     * @param userId  用户ID（不能为空）
     * @param groupId 组ID（不能为空）
     * @throws UnsupportedOperationException 如果身份服务实现为只读模式
     */
    void removeUserFromGroup(String userId, String groupId);

    /**
     * 查询组的所有成员用户
     *
     * @param groupId 组ID
     * @return 用户列表
     */
    List<User> getUsersByGroup(String groupId);

    // ========================================================================
    // 五、租户管理
    // ========================================================================

    /**
     * 创建新租户实例（瞬态对象）
     *
     * @param tenantId 租户ID（不能为空）
     * @return 新创建的租户实例
     * @throws UnsupportedOperationException 如果身份服务实现为只读模式
     */
    Tenant newTenant(String tenantId);

    /**
     * 保存租户
     * <p>
     * 如果租户已存在则更新，否则创建新租户
     *
     * @param tenant 租户对象（不能为空）
     * @throws UnsupportedOperationException 如果身份服务实现为只读模式
     */
    void saveTenant(Tenant tenant);

    /**
     * 删除租户
     * <p>
     * 如果租户不存在，操作被忽略
     *
     * @param tenantId 租户ID（不能为空）
     * @throws UnsupportedOperationException 如果身份服务实现为只读模式
     */
    void deleteTenant(String tenantId);

    /**
     * 查询租户
     *
     * @param tenantId 租户ID
     * @return 租户对象，不存在时返回null
     */
    Tenant getTenant(String tenantId);

    /**
     * 查询所有租户
     *
     * @return 租户列表
     */
    List<Tenant> getAllTenants();

    /**
     * 创建租户查询构建器
     *
     * @return 租户查询构建器
     */
    TenantQuery createTenantQuery();

    // ========================================================================
    // 六、用户-租户关系管理
    // ========================================================================

    /**
     * 创建用户和租户的成员资格关系
     *
     * @param tenantId 租户ID（不能为空）
     * @param userId   用户ID（不能为空）
     * @throws RuntimeException 如果租户或用户不存在，或用户已是租户成员
     * @throws UnsupportedOperationException 如果身份服务实现为只读模式
     */
    void createTenantUserMembership(String tenantId, String userId);

    /**
     * 删除用户和租户的成员资格关系
     * <p>
     * 如果租户、用户或成员关系不存在，操作被忽略
     *
     * @param tenantId 租户ID（不能为空）
     * @param userId   用户ID（不能为空）
     * @throws UnsupportedOperationException 如果身份服务实现为只读模式
     */
    void deleteTenantUserMembership(String tenantId, String userId);

    /**
     * 将用户添加到租户
     *
     * @param userId   用户ID（不能为空）
     * @param tenantId 租户ID（不能为空）
     * @throws UnsupportedOperationException 如果身份服务实现为只读模式
     */
    void addUserToTenant(String userId, String tenantId);

    /**
     * 从租户中移除用户
     *
     * @param userId   用户ID（不能为空）
     * @param tenantId 租户ID（不能为空）
     * @throws UnsupportedOperationException 如果身份服务实现为只读模式
     */
    void removeUserFromTenant(String userId, String tenantId);

    // ========================================================================
    // 七、组-租户关系管理
    // ========================================================================

    /**
     * 创建组和租户的成员资格关系
     *
     * @param tenantId 租户ID（不能为空）
     * @param groupId  组ID（不能为空）
     * @throws RuntimeException 如果租户或组不存在，或组已是租户成员
     * @throws UnsupportedOperationException 如果身份服务实现为只读模式
     */
    void createTenantGroupMembership(String tenantId, String groupId);

    /**
     * 删除组和租户的成员资格关系
     * <p>
     * 如果租户、组或成员关系不存在，操作被忽略
     *
     * @param tenantId 租户ID（不能为空）
     * @param groupId  组ID（不能为空）
     * @throws UnsupportedOperationException 如果身份服务实现为只读模式
     */
    void deleteTenantGroupMembership(String tenantId, String groupId);

    // ========================================================================
    // 八、密码管理
    // ========================================================================

    /**
     * 设置用户密码
     *
     * @param userId   用户ID（不能为空）
     * @param password 新密码（不能为空）
     */
    void setUserPassword(String userId, String password);

    /**
     * 验证用户密码
     *
     * @param userId   用户ID（不能为空）
     * @param password 待验证的密码（不能为空）
     * @return true 密码正确，false 密码错误
     */
    boolean validateUserPassword(String userId, String password);

    /**
     * 检查用户密码是否有效
     * <p>
     * 与 {@link #validateUserPassword(String, String)} 功能相同
     *
     * @param userId   用户ID
     * @param password 密码
     * @return true 密码正确，false 密码错误
     */
    boolean checkPassword(String userId, String password);

    /**
     * 检查密码是否符合密码策略
     *
     * @param password 待检查的密码
     * @return 密码策略检查结果（包含通过和违反的规则）
     */
    PasswordPolicyResult checkPasswordAgainstPolicy(String password);

    /**
     * 检查密码是否符合密码策略（包含用户信息）
     *
     * @param candidatePassword 待检查的密码
     * @param user              用户对象（用于个性化策略检查）
     * @return 密码策略检查结果
     */
    PasswordPolicyResult checkPasswordAgainstPolicy(String candidatePassword, User user);

    /**
     * 使用指定密码策略检查密码
     *
     * @param policy   密码策略
     * @param password 待检查的密码
     * @return 密码策略检查结果
     */
    PasswordPolicyResult checkPasswordAgainstPolicy(PasswordPolicy policy, String password);

    /**
     * 使用指定密码策略检查密码（包含用户信息）
     *
     * @param policy            密码策略
     * @param candidatePassword 待检查的密码
     * @param user              用户对象
     * @return 密码策略检查结果
     */
    PasswordPolicyResult checkPasswordAgainstPolicy(PasswordPolicy policy,
                                                    String candidatePassword,
                                                    User user);

    /**
     * 获取当前配置的密码策略
     *
     * @return 密码策略对象，未配置时返回null
     */
    PasswordPolicy getPasswordPolicy();

    // ========================================================================
    // 九、认证管理
    // ========================================================================

    /**
     * 设置当前线程的认证用户ID
     * <p>
     * 后续所有服务调用将使用该用户身份进行权限检查。
     * 交互完成后应调用 {@link #clearAuthentication()} 清除认证信息
     *
     * @param authenticatedUserId 当前用户ID
     */
    void setAuthenticatedUserId(String authenticatedUserId);

    /**
     * 设置当前线程的认证信息（用户ID和组）
     *
     * @param userId 用户ID
     * @param groups 用户所属组列表
     */
    void setAuthentication(String userId, List<String> groups);

    /**
     * 设置当前线程的认证信息（用户ID、组和租户）
     *
     * @param userId    用户ID
     * @param groups    用户所属组列表
     * @param tenantIds 用户所属租户列表
     */
    void setAuthentication(String userId, List<String> groups, List<String> tenantIds);

    /**
     * 设置当前线程的认证信息
     *
     * @param authentication 认证对象
     */
    void setAuthentication(Authentication authentication);

    /**
     * 获取当前线程的认证信息
     *
     * @return 当前认证对象
     */
    Authentication getCurrentAuthentication();

    /**
     * 清除当前线程的认证信息
     * <p>
     * 如果不存在认证信息，不抛出异常
     */
    void clearAuthentication();

    // ========================================================================
    // 十、用户头像管理
    // ========================================================================

    /**
     * 设置用户头像
     *
     * @param userId  用户ID
     * @param picture 头像对象（为null时删除头像）
     */
    void setUserPicture(String userId, Picture picture);

    /**
     * 获取用户头像
     *
     * @param userId 用户ID
     * @return 头像对象，不存在时返回null
     */
    Picture getUserPicture(String userId);

    /**
     * 删除用户头像
     * <p>
     * 如果用户不存在或不具有头像，操作被忽略
     *
     * @param userId 用户ID
     */
    void deleteUserPicture(String userId);

    // ========================================================================
    // 十一、用户扩展信息管理（键值对）
    // ========================================================================

    /**
     * 设置用户扩展信息
     * <p>
     * 用于存储与用户相关的键值对信息
     *
     * @param userId 用户ID
     * @param key    信息键
     * @param value  信息值
     */
    void setUserInfo(String userId, String key, String value);

    /**
     * 获取用户扩展信息
     *
     * @param userId 用户ID
     * @param key    信息键
     * @return 信息值，不存在时返回null
     */
    String getUserInfo(String userId, String key);

    /**
     * 获取用户的所有扩展信息键
     *
     * @param userId 用户ID
     * @return 键列表
     */
    List<String> getUserInfoKeys(String userId);

    /**
     * 删除用户扩展信息
     *
     * @param userId 用户ID
     * @param key    信息键
     */
    void deleteUserInfo(String userId, String key);
}