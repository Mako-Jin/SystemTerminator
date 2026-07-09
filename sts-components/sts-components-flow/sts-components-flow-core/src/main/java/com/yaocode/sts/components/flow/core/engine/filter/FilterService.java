package com.yaocode.sts.components.flow.core.engine.filter;

import java.util.List;

/**
 * 过滤器服务接口
 * <p>
 * 管理查询过滤器，支持保存和共享查询条件，包括：
 * <ul>
 *   <li>过滤器的创建、保存、更新、删除、查询</li>
 *   <li>按用户、类型、公共/私有查询过滤器</li>
 *   <li>过滤器查询的执行（列表、分页、单条、计数）</li>
 *   <li>过滤器查询的扩展（支持附加查询条件）</li>
 *   <li>过滤器的共享和权限管理</li>
 * </ul>
 * <p>
 * 使用示例：
 * <pre>
 * // 创建任务过滤器
 * Filter filter = filterService.newTaskFilter("我的待办任务");
 * filter.setQuery(TaskQuery.class, taskQuery.createQuery().taskAssignee("john"));
 * filter.setProperties(properties);
 * filterService.saveFilter(filter);
 *
 * // 执行过滤器查询
 * List&lt;Task&gt; tasks = filterService.list(filter.getId());
 * </pre>
 *
 * @author Process Engine Team
 * @version 1.0.0
 */
public interface FilterService {

    // ========================================================================
    // 一、过滤器创建
    // ========================================================================

    /**
     * 创建新的任务过滤器（瞬态对象）
     *
     * @return 新的任务过滤器对象
     */
    Filter newTaskFilter();

    /**
     * 创建新的任务过滤器（瞬态对象），指定名称
     *
     * @param filterName 过滤器名称
     * @return 新的任务过滤器对象
     */
    Filter newTaskFilter(String filterName);

    /**
     * 创建过滤器
     *
     * @param filter 过滤器对象
     * @return 创建后的过滤器
     */
    Filter createFilter(Filter filter);

    // ========================================================================
    // 二、过滤器管理（CRUD）
    // ========================================================================

    /**
     * 保存过滤器
     * <p>
     * 如果过滤器已存在则更新，否则创建新过滤器
     *
     * @param filter 过滤器对象
     * @return 保存后的过滤器
     */
    Filter saveFilter(Filter filter);

    /**
     * 更新过滤器
     *
     * @param filter 过滤器对象
     */
    void updateFilter(Filter filter);

    /**
     * 删除过滤器
     *
     * @param filterId 过滤器ID
     */
    void deleteFilter(String filterId);

    /**
     * 查询过滤器
     *
     * @param filterId 过滤器ID
     * @return 过滤器对象，不存在时返回null
     */
    Filter getFilter(String filterId);

    /**
     * 查询用户的所有过滤器
     *
     * @param userId 用户ID
     * @return 过滤器列表
     */
    List<Filter> getFiltersByUser(String userId);

    /**
     * 查询指定类型的过滤器
     *
     * @param filterType 过滤器类型
     * @return 过滤器列表
     */
    List<Filter> getFiltersByType(String filterType);

    /**
     * 查询公共过滤器（所有用户共享）
     *
     * @return 公共过滤器列表
     */
    List<Filter> getPublicFilters();

    // ========================================================================
    // 三、过滤器查询（构建器）
    // ========================================================================

    /**
     * 创建过滤器查询构建器
     *
     * @return 过滤器查询构建器
     */
    FilterQuery createFilterQuery();

    /**
     * 创建任务过滤器查询构建器
     *
     * @return 任务过滤器查询构建器
     */
    FilterQuery createTaskFilterQuery();

    // ========================================================================
    // 四、过滤器执行（基础查询）
    // ========================================================================

    /**
     * 执行过滤器查询，返回结果列表
     * <p>
     * 执行过滤器中保存的查询，返回所有结果
     *
     * @param filterId   过滤器ID
     * @param <T>        结果类型
     * @return 查询结果列表
     */
    <T> List<T> list(String filterId);

    /**
     * 执行过滤器查询，返回分页结果
     *
     * @param filterId    过滤器ID
     * @param firstResult 起始索引（从0开始）
     * @param maxResults  最大返回数量
     * @param <T>         结果类型
     * @return 分页查询结果列表
     */
    <T> List<T> listPage(String filterId, int firstResult, int maxResults);

    /**
     * 执行过滤器查询，返回单条结果
     *
     * @param filterId 过滤器ID
     * @param <T>      结果类型
     * @return 单条查询结果
     */
    <T> T singleResult(String filterId);

    /**
     * 执行过滤器查询，返回结果总数
     *
     * @param filterId 过滤器ID
     * @return 结果总数
     */
    Long count(String filterId);

    /**
     * 执行过滤器查询（返回结果列表）
     *
     * @param filterId   过滤器ID
     * @param resultType 结果类型
     * @param <T>        结果类型
     * @return 查询结果列表
     */
    <T> List<T> executeFilter(String filterId, Class<T> resultType);

    /**
     * 执行过滤器查询（返回分页结果）
     *
     * @param filterId    过滤器ID
     * @param page        页码（从0开始）
     * @param size        每页大小
     * @param resultType  结果类型
     * @param <T>         结果类型
     * @return 分页查询结果列表
     */
    <T> List<T> executeFilter(String filterId, int page, int size, Class<T> resultType);

    /**
     * 获取过滤器的结果总数
     *
     * @param filterId 过滤器ID
     * @return 结果总数
     */
    long countFilterResults(String filterId);

    /**
     * 验证过滤器的查询条件是否有效
     *
     * @param filter 过滤器对象
     * @return true 有效，false 无效
     */
    boolean validateFilter(Filter filter);

    // ========================================================================
    // 五、扩展查询执行
    // ========================================================================

    /**
     * 执行过滤器查询并附加扩展查询条件
     *
     * @param filterId       过滤器ID
     * @param extendingQuery 扩展查询条件
     * @param <T>            结果类型
     * @param <Q>            查询类型
     * @return 查询结果列表
     */
    <T, Q extends Query<?, T>> List<T> list(String filterId, Q extendingQuery);

    /**
     * 执行过滤器查询并附加扩展查询条件（分页）
     *
     * @param filterId       过滤器ID
     * @param extendingQuery 扩展查询条件
     * @param firstResult    起始索引
     * @param maxResults     最大返回数量
     * @param <T>            结果类型
     * @param <Q>            查询类型
     * @return 分页查询结果列表
     */
    <T, Q extends Query<?, T>> List<T> listPage(String filterId, Q extendingQuery, int firstResult, int maxResults);

    /**
     * 执行过滤器查询并附加扩展查询条件（单条结果）
     *
     * @param filterId       过滤器ID
     * @param extendingQuery 扩展查询条件
     * @param <T>            结果类型
     * @param <Q>            查询类型
     * @return 单条查询结果
     */
    <T, Q extends Query<?, T>> T singleResult(String filterId, Q extendingQuery);

    /**
     * 执行过滤器查询并附加扩展查询条件（计数）
     *
     * @param filterId       过滤器ID
     * @param extendingQuery 扩展查询条件
     * @return 结果总数
     */
    Long count(String filterId, Query<?, ?> extendingQuery);

    // ========================================================================
    // 六、过滤器共享管理
    // ========================================================================

    /**
     * 共享过滤器给其他用户
     *
     * @param filterId 过滤器ID
     * @param userIds  用户ID列表
     */
    void shareFilter(String filterId, List<String> userIds);

    /**
     * 取消共享过滤器
     *
     * @param filterId 过滤器ID
     * @param userIds  用户ID列表
     */
    void unshareFilter(String filterId, List<String> userIds);

    /**
     * 获取共享过滤器的用户列表
     *
     * @param filterId 过滤器ID
     * @return 共享用户ID列表
     */
    List<String> getSharedUsers(String filterId);

    /**
     * 将过滤器设为公共（所有用户可见）
     *
     * @param filterId 过滤器ID
     */
    void makePublic(String filterId);

    /**
     * 将过滤器设为私有（仅创建者可见）
     *
     * @param filterId 过滤器ID
     */
    void makePrivate(String filterId);
}