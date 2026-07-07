package com.yaocode.sts.components.flow.core.engine.authorization;


import java.util.List;
import java.util.Set;

/**
 * 授权服务接口
 * 权限管理和授权检查
 *
 * @author Process Engine Team
 * @version 1.0.0
 */
public interface AuthorizationService {

    // ========== 授权管理 ==========

    /**
     * 创建授权
     */
    Authorization createAuthorization(Authorization authorization);

    /**
     * 更新授权
     */
    void updateAuthorization(Authorization authorization);

    /**
     * 删除授权
     */
    void deleteAuthorization(String authorizationId);

    /**
     * 查询授权
     */
    Authorization getAuthorization(String authorizationId);

    /**
     * 查询用户的所有授权
     */
    List<Authorization> getAuthorizationsByUser(String userId);

    /**
     * 查询用户在特定资源上的授权
     */
    List<Authorization> getAuthorizationsByUserAndResource(String userId,
                                                           String resourceType,
                                                           String resourceId);

    /**
     * 查询组的所有授权
     */
    List<Authorization> getAuthorizationsByGroup(String groupId);

    // ========== 权限检查 ==========

    /**
     * 检查用户是否有权限
     */
    boolean hasPermission(String userId, String resourceType,
                          String resourceId, String permission);

    /**
     * 检查用户是否有某个权限（通过组继承）
     */
    boolean hasPermissionIncludingGroups(String userId, String resourceType,
                                         String resourceId, String permission);

    /**
     * 检查用户对资源的所有权限
     */
    Set<String> getPermissions(String userId, String resourceType, String resourceId);

    /**
     * 检查用户对资源的权限（包括组继承）
     */
    Set<String> getPermissionsIncludingGroups(String userId, String resourceType,
                                              String resourceId);

    /**
     * 检查用户是否有管理权限
     */
    boolean isAdmin(String userId);

    /**
     * 检查用户是否为租户管理员
     */
    boolean isTenantAdmin(String userId, String tenantId);

    // ========== 资源访问控制 ==========

    /**
     * 过滤用户可访问的资源列表
     */
    List<String> filterResources(String userId, String resourceType,
                                 List<String> resourceIds, String permission);

    /**
     * 检查用户是否有流程实例的访问权限
     */
    boolean hasProcessInstancePermission(String userId, String processInstanceId);

    /**
     * 检查用户是否有任务的访问权限
     */
    boolean hasTaskPermission(String userId, String taskId);

    // ========== 授权构建器 ==========

    /**
     * 授权构建器
     */
    AuthorizationBuilder createAuthorizationBuilder();

}
