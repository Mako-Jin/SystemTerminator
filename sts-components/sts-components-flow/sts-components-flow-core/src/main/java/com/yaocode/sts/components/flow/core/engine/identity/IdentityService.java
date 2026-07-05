package com.yaocode.sts.components.flow.core.engine.identity;

import java.util.List;

/**
 * 身份服务接口
 * 管理用户、组、租户
 *
 * @author Process Engine Team
 * @version 1.0.0
 */
public interface IdentityService {

    // ========== 用户管理 ==========

    /**
     * 创建用户
     */
    User createUser(User user);

    /**
     * 更新用户
     */
    void updateUser(User user);

    /**
     * 删除用户
     */
    void deleteUser(String userId);

    /**
     * 查询用户
     */
    User getUser(String userId);

    /**
     * 根据用户名查询用户
     */
    User getUserByUsername(String username);

    /**
     * 查询所有用户
     */
    List<User> getAllUsers();

    /**
     * 分页查询用户
     */
    List<User> getUsers(int page, int size);

    /**
     * 设置用户密码
     */
    void setUserPassword(String userId, String password);

    /**
     * 验证用户密码
     */
    boolean validateUserPassword(String userId, String password);

    // ========== 组管理 ==========

    /**
     * 创建组
     */
    Group createGroup(Group group);

    /**
     * 更新组
     */
    void updateGroup(Group group);

    /**
     * 删除组
     */
    void deleteGroup(String groupId);

    /**
     * 查询组
     */
    Group getGroup(String groupId);

    /**
     * 查询所有组
     */
    List<Group> getAllGroups();

    /**
     * 查询用户所属组
     */
    List<Group> getGroupsByUser(String userId);

    // ========== 用户-组关系 ==========

    /**
     * 将用户添加到组
     */
    void addUserToGroup(String userId, String groupId);

    /**
     * 从组中移除用户
     */
    void removeUserFromGroup(String userId, String groupId);

    /**
     * 查询组的所有用户
     */
    List<User> getUsersByGroup(String groupId);

    // ========== 租户管理 ==========

    /**
     * 创建租户
     */
    Tenant createTenant(Tenant tenant);

    /**
     * 更新租户
     */
    void updateTenant(Tenant tenant);

    /**
     * 删除租户
     */
    void deleteTenant(String tenantId);

    /**
     * 查询租户
     */
    Tenant getTenant(String tenantId);

    /**
     * 查询所有租户
     */
    List<Tenant> getAllTenants();

    /**
     * 将用户添加到租户
     */
    void addUserToTenant(String userId, String tenantId);

    /**
     * 从租户中移除用户
     */
    void removeUserFromTenant(String userId, String tenantId);
}
