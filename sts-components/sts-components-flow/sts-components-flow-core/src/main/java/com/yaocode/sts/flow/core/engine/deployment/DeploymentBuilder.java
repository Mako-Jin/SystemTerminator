package com.yaocode.sts.flow.core.engine.deployment;

import com.yaocode.sts.flow.core.model.ProcessModel;

/**
 * 流程定义部署构建器
 * <p>
 * 用于构建和部署流程定义到引擎。
 *
 * @author yourname
 */
public interface DeploymentBuilder {

    /**
     * 设置流程定义的 JSON 数据（解析后的对象）
     */
    DeploymentBuilder model(ProcessModel model);

    /**
     * 设置流程定义的 XML 源（作为备份）
     */
    DeploymentBuilder sourceXml(String xml);

    /**
     * 设置原始 JSON（作为备份，可选）
     * 如果你的解析器输出 JSON 格式，用这个方法
     */
    DeploymentBuilder sourceJson(String json);

    /**
     * 设置流程 Key
     * 必填，用于标识流程
     */
    DeploymentBuilder processKey(String processKey);

    /**
     * 设置流程定义的版本号
     * @param version 如果为 null，则自动升版
     */
    DeploymentBuilder version(Integer version);

    /**
     * 设置租户 ID
     */
    DeploymentBuilder tenantId(String tenantId);

    /**
     * 设置是否覆盖已存在的版本
     */
    DeploymentBuilder overwriteIfExists(boolean overwrite);

    /**
     * 设置操作人（用于审计日志）
     */
    DeploymentBuilder operator(String operator);

    /**
     * 设置部署原因（用于审计日志）
     */
    DeploymentBuilder reason(String reason);

    /**
     * 执行部署
     */
    Deployment deploy();
}
