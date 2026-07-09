package com.yaocode.sts.components.flow.core.engine.authorization;

import java.util.List;
import java.util.Set;

/**
 * 授权服务接口
 * <p>
 * 管理用户和组的权限授权，包括：
 * <ul>
 *   <li>授权的创建、更新、删除、查询</li>
 *   <li>用户和组的权限检查</li>
 *   <li>资源访问权限的过滤</li>
 *   <li>流程实例和任务的权限检查</li>
 *   <li>管理员和租户管理员权限检查</li>
 *   <li>授权查询构建器</li>
 * </ul>
 * <p>
 * 授权示例：
 * <pre>
 * // 创建用户授权
 * Authorization auth = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
 * auth.setUserId("john");
 * auth.setResource(Resources.PROCESS_DEFINITION);
 * auth.setResourceId("2313");
 * auth.addPermission(Permissions.READ);
 * authorizationService.saveAuthorization(auth);
 * </pre>
 *
 * @author Process Engine Team
 * @version 1.0.0
 */
public interface AuthorizationService {

    // ========================================================================
    // 一、授权管理（CRUD）
    // ========================================================================

    /**
     * 创建新的授权对象（瞬态）
     * <p>
     * 返回的授权对象不是持久的，必须通过 {@link #saveAuthorization(Authorization)} 保存
     *
     * @param authorizationType 授权类型：
     *                           {@link Authorization#AUTH_TYPE_GLOBAL} 全局授权，
     *                           {@link Authorization#AUTH_TYPE_GRANT} 授予授权，
     *                           {@link Authorization#AUTH_TYPE_REVOKE} 撤销授权
     * @return 非持久化的授权对象
     */
    Authorization createNewAuthorization(int authorizationType);

    /**
     * 创建授权
     *
     * @param authorization 授权对象
     * @return 保存后的授权对象
     */
    Authorization createAuthorization(Authorization authorization);

    /**
     * 保存授权
     * <p>
     * 用于持久化新的授权对象或更新已存在的授权对象
     *
     * @param authorization 授权对象
     * @return 保存后的授权对象
     */
    Authorization saveAuthorization(Authorization authorization);

    /**
     * 更新授权
     *
     * @param authorization 授权对象
     */
    void updateAuthorization(Authorization authorization);

    /**
     * 删除授权
     *
     * @param authorizationId 授权ID
     */
    void deleteAuthorization(String authorizationId);

    /**
     * 查询授权
     *
     * @param authorizationId 授权ID
     * @return 授权对象，不存在时返回null
     */
    Authorization getAuthorization(String authorizationId);

    /**
     * 查询用户的所有授权
     *
     * @param userId 用户ID
     * @return 授权列表
     */
    List<Authorization> getAuthorizationsByUser(String userId);

    /**
     * 查询用户在特定资源上的授权
     *
     * @param userId       用户ID
     * @param resourceType 资源类型
     * @param resourceId   资源ID
     * @return 授权列表
     */
    List<Authorization> getAuthorizationsByUserAndResource(String userId, String resourceType, String resourceId);

    /**
     * 查询组的所有授权
     *
     * @param groupId 组ID
     * @return 授权列表
     */
    List<Authorization> getAuthorizationsByGroup(String groupId);

    /**
     * 创建授权查询构建器
     *
     * @return 授权查询构建器
     */
    AuthorizationQuery createAuthorizationQuery();

    // ========================================================================
    // 二、授权构建器
    // ========================================================================

    /**
     * 创建授权构建器
     * <p>
     * 用于流式构建授权对象
     *
     * @return 授权构建器
     */
    AuthorizationBuilder createAuthorizationBuilder();

    // ========================================================================
    // 三、用户权限检查
    // ========================================================================

    /**
     * 检查用户是否有权限
     *
     * @param userId       用户ID
     * @param resourceType 资源类型
     * @param resourceId   资源ID
     * @param permission   权限名称
     * @return true 有权限，false 无权限
     */
    boolean hasPermission(
            String userId,
            String resourceType,
            String resourceId,
            String permission
    );

    /**
     * 检查用户是否有某个权限（通过组继承）
     *
     * @param userId       用户ID
     * @param resourceType 资源类型
     * @param resourceId   资源ID
     * @param permission   权限名称
     * @return true 有权限（包括从组继承的权限），false 无权限
     */
    boolean hasPermissionIncludingGroups(
            String userId,
            String resourceType,
            String resourceId,
            String permission
    );

    /**
     * 检查用户是否被授权访问指定资源
     *
     * @param userId     用户ID
     * @param groupIds   用户所属组ID列表
     * @param permission 权限对象
     * @param resource   资源对象
     * @return true 已授权，false 未授权
     */
    boolean isUserAuthorized(
            String userId,
            List<String> groupIds,
            Permission permission,
            Resource resource
    );

    /**
     * 检查用户是否被授权访问指定资源（指定资源ID）
     *
     * @param userId     用户ID
     * @param groupIds   用户所属组ID列表
     * @param permission 权限对象
     * @param resource   资源对象
     * @param resourceId 资源ID
     * @return true 已授权，false 未授权
     */
    boolean isUserAuthorized(
            String userId,
            List<String> groupIds,
            Permission permission,
            Resource resource,
            String resourceId
    );

    /**
     * 检查用户对资源的所有权限
     *
     * @param userId       用户ID
     * @param resourceType 资源类型
     * @param resourceId   资源ID
     * @return 权限名称集合
     */
    Set<String> getPermissions(String userId, String resourceType, String resourceId);

    /**
     * 检查用户对资源的权限（包括组继承）
     *
     * @param userId       用户ID
     * @param resourceType 资源类型
     * @param resourceId   资源ID
     * @return 权限名称集合（包括从组继承的权限）
     */
    Set<String> getPermissionsIncludingGroups(String userId, String resourceType, String resourceId);

    // ========================================================================
    // 四、管理员权限检查
    // ========================================================================

    /**
     * 检查用户是否有管理权限
     *
     * @param userId 用户ID
     * @return true 是管理员，false 不是管理员
     */
    boolean isAdmin(String userId);

    /**
     * 检查用户是否为租户管理员
     *
     * @param userId   用户ID
     * @param tenantId 租户ID
     * @return true 是租户管理员，false 不是租户管理员
     */
    boolean isTenantAdmin(String userId, String tenantId);

    // ========================================================================
    // 五、资源访问控制
    // ========================================================================

    /**
     * 过滤用户可访问的资源列表
     *
     * @param userId       用户ID
     * @param resourceType 资源类型
     * @param resourceIds  待过滤的资源ID列表
     * @param permission   所需权限名称
     * @return 用户有权限访问的资源ID列表
     */
    List<String> filterResources(
            String userId,
            String resourceType,
            List<String> resourceIds,
            String permission
    );

    /**
     * 检查用户是否有流程实例的访问权限
     *
     * @param userId             用户ID
     * @param processInstanceId  流程实例ID
     * @return true 有访问权限，false 无访问权限
     */
    boolean hasProcessInstancePermission(String userId, String processInstanceId);

    /**
     * 检查用户是否有任务的访问权限
     *
     * @param userId 用户ID
     * @param taskId 任务ID
     * @return true 有访问权限，false 无访问权限
     */
    boolean hasTaskPermission(String userId, String taskId);
}