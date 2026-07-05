package com.yaocode.sts.components.flow.core.engine;


import com.yaocode.sts.components.flow.core.engine.repository.RepositoryService;
import com.yaocode.sts.components.flow.core.engine.runtime.RuntimeService;

/**
 * 流程引擎服务聚合接口
 * 提供所有核心服务的访问入口
 *
 * @author Process Engine Team
 * @version 1.0.0
 */
public interface ProcessEngine {

    /**
     * 获取运行时服务
     * 用于流程实例管理、启动、变量操作等
     */
    RuntimeService getRuntimeService();

    /**
     * 获取仓库服务
     * 用于流程定义部署、查询、版本管理
     */
    RepositoryService getRepositoryService();

    /**
     * 获取任务服务
     * 用于人工任务管理、认领、完成、指派
     */
    TaskService getTaskService();

    /**
     * 获取历史服务
     * 用于历史数据查询、报表统计
     */
    HistoryService getHistoryService();

    /**
     * 获取身份服务
     * 用于用户、组、租户管理
     */
    IdentityService getIdentityService();

    /**
     * 获取管理服务
     * 用于引擎管理、作业管理、指标监控
     */
    ManagementService getManagementService();

    /**
     * 获取授权服务
     * 用于权限管理、授权检查
     */
    AuthorizationService getAuthorizationService();

    /**
     * 获取表单服务
     * 用于表单定义与渲染
     */
    FormService getFormService();

    /**
     * 获取案例服务
     * 用于CMMN案例管理
     */
    CaseService getCaseService();

    /**
     * 获取过滤器服务
     * 用于查询过滤器管理
     */
    FilterService getFilterService();

    /**
     * 获取外部任务服务
     * 用于外部任务管理
     */
    ExternalTaskService getExternalTaskService();

    /**
     * 获取决策服务
     * 用于DMN决策服务
     */
    DecisionService getDecisionService();

}
