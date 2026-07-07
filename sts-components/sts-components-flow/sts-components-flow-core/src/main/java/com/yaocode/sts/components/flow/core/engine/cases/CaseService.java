package com.yaocode.sts.components.flow.core.engine.cases;


import java.util.List;
import java.util.Map;

/**
 * 案例服务接口（CMMN）
 * 管理CMMN案例实例和案例计划
 *
 * @author Process Engine Team
 * @version 1.0.0
 */
public interface CaseService {

    // ========== 案例定义管理 ==========

    /**
     * 查询案例定义
     */
    CaseDefinition getCaseDefinition(String caseDefinitionId);

    /**
     * 根据Key查询最新版本
     */
    CaseDefinition getLatestCaseDefinitionByKey(String caseDefinitionKey);

    /**
     * 查询所有案例定义
     */
    List<CaseDefinition> getAllCaseDefinitions();

    /**
     * 挂起案例定义
     */
    void suspendCaseDefinition(String caseDefinitionId);

    /**
     * 激活案例定义
     */
    void activateCaseDefinition(String caseDefinitionId);

    // ========== 案例实例管理 ==========

    /**
     * 启动案例实例
     */
    CaseInstance startCaseInstance(String caseDefinitionKey);

    /**
     * 启动案例实例（带变量）
     */
    CaseInstance startCaseInstance(String caseDefinitionKey, Map<String, Object> variables);

    /**
     * 启动案例实例（指定业务Key）
     */
    CaseInstance startCaseInstance(String caseDefinitionKey, String businessKey,
                                   Map<String, Object> variables);

    /**
     * 查询案例实例
     */
    CaseInstance getCaseInstance(String caseInstanceId);

    /**
     * 查询所有活跃案例实例
     */
    List<CaseInstance> getActiveCaseInstances();

    /**
     * 挂起案例实例
     */
    void suspendCaseInstance(String caseInstanceId);

    /**
     * 激活案例实例
     */
    void activateCaseInstance(String caseInstanceId);

    /**
     * 终止案例实例
     */
    void terminateCaseInstance(String caseInstanceId);

    // ========== 案例计划管理 ==========

    /**
     * 获取案例计划
     */
    CasePlan getCasePlan(String caseInstanceId);

    /**
     * 执行案例计划任务
     */
    void executeCasePlanTask(String caseInstanceId, String planItemId);

    /**
     * 手动触发案例计划项
     */
    void triggerCasePlanItem(String caseInstanceId, String planItemId);

    /**
     * 完成案例计划阶段
     */
    void completeCaseStage(String caseInstanceId, String stageId);

    // ========== 案例变量 ==========

    /**
     * 设置案例变量
     */
    void setCaseVariable(String caseInstanceId, String variableName, Object value);

    /**
     * 获取案例变量
     */
    Object getCaseVariable(String caseInstanceId, String variableName);

    /**
     * 获取所有案例变量
     */
    Map<String, Object> getCaseVariables(String caseInstanceId);

    // ========== 案例查询 ==========

    /**
     * 根据业务Key查询案例实例
     */
    List<CaseInstance> getCaseInstancesByBusinessKey(String businessKey);

    /**
     * 查询用户参与的案例实例
     */
    List<CaseInstance> getCaseInstancesByUser(String userId);
}
