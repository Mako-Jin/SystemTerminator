package com.yaocode.sts.components.flow.core.repository;

import java.util.Map;

/**
 * 变量仓储接口
 */
public interface VariableRepository {

    void save(String processInstanceId, String name, Object value);

    void saveAll(String processInstanceId, Map<String, Object> variables);

    Map<String, Object> findByProcessInstanceId(String processInstanceId);

    Object findByName(String processInstanceId, String name);

    void deleteByProcessInstanceId(String processInstanceId);
}
