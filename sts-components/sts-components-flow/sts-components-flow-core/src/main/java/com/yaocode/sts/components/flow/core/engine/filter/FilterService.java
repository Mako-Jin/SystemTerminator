package com.yaocode.sts.components.flow.core.engine.filter;


import java.util.List;

/**
 * 过滤器服务接口
 * 管理查询过滤器，支持保存和共享查询条件
 *
 * @author Process Engine Team
 * @version 1.0.0
 */
public interface FilterService {

    // ========== 过滤器管理 ==========

    /**
     * 创建过滤器
     */
    Filter createFilter(Filter filter);

    /**
     * 更新过滤器
     */
    void updateFilter(Filter filter);

    /**
     * 删除过滤器
     */
    void deleteFilter(String filterId);

    /**
     * 查询过滤器
     */
    Filter getFilter(String filterId);

    /**
     * 查询用户的所有过滤器
     */
    List<Filter> getFiltersByUser(String userId);

    /**
     * 查询指定类型的过滤器
     */
    List<Filter> getFiltersByType(String filterType);

    /**
     * 查询公共过滤器（所有用户共享）
     */
    List<Filter> getPublicFilters();

    // ========== 过滤器执行 ==========

    /**
     * 执行过滤器查询（返回结果列表）
     */
    <T> List<T> executeFilter(String filterId, Class<T> resultType);

    /**
     * 执行过滤器查询（返回分页结果）
     */
    <T> List<T> executeFilter(String filterId, int page, int size, Class<T> resultType);

    /**
     * 获取过滤器的结果总数
     */
    long countFilterResults(String filterId);

    /**
     * 验证过滤器的查询条件是否有效
     */
    boolean validateFilter(Filter filter);

    // ========== 过滤器共享 ==========

    /**
     * 共享过滤器给其他用户
     */
    void shareFilter(String filterId, List<String> userIds);

    /**
     * 取消共享过滤器
     */
    void unshareFilter(String filterId, List<String> userIds);

    /**
     * 获取共享过滤器的用户列表
     */
    List<String> getSharedUsers(String filterId);

    /**
     * 将过滤器设为公共
     */
    void makePublic(String filterId);

    /**
     * 将过滤器设为私有
     */
    void makePrivate(String filterId);
}
