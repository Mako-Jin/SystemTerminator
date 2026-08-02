package com.yaocode.sts.flow.core.engine.deployment;

import com.yaocode.sts.flow.core.model.ProcessDefinition;
import lombok.Getter;

import java.util.Date;

/**
 * 部署结果对象
 *
 * @author yourname
 */
@Getter
public class Deployment {

    private final String deploymentId;
    private final ProcessDefinition processDefinition;
    private final String processKey;
    private final int version;
    private final String tenantId;
    private final String operator;
    private final Date deployTime;
    private final boolean isNewVersion;  // true=新版本，false=覆盖已有版本

    public Deployment(String deploymentId, ProcessDefinition processDefinition,
                      String processKey, int version, String tenantId,
                      String operator, Date deployTime, boolean isNewVersion) {
        this.deploymentId = deploymentId;
        this.processDefinition = processDefinition;
        this.processKey = processKey;
        this.version = version;
        this.tenantId = tenantId;
        this.operator = operator;
        this.deployTime = deployTime;
        this.isNewVersion = isNewVersion;
    }

    @Override
    public String toString() {
        return "Deployment{" +
                "deploymentId='" + deploymentId + '\'' +
                ", processKey='" + processKey + '\'' +
                ", version=" + version +
                ", isNewVersion=" + isNewVersion +
                '}';
    }
}
