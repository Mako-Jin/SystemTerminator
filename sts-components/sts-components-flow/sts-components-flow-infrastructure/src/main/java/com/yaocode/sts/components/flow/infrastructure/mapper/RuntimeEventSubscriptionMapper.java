package com.yaocode.sts.components.flow.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaocode.sts.components.flow.infrastructure.entity.RuntimeEventSubscriptionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 运行时事件订阅 Mapper
 * 对应表: flow_tbl_runtime_event_subscription
 */
@Mapper
public interface RuntimeEventSubscriptionMapper extends BaseMapper<RuntimeEventSubscriptionEntity> {

    /**
     * 根据事件名称和类型查询等待中的订阅
     * XML: RuntimeEventSubscriptionMapper.xml
     */
    List<RuntimeEventSubscriptionEntity> selectWaitingByEventNameAndType(
            @Param("eventName") String eventName,
            @Param("eventType") Integer eventType
    );

    /**
     * 根据关联键查询等待中的订阅
     * XML: RuntimeEventSubscriptionMapper.xml
     */
    List<RuntimeEventSubscriptionEntity> selectWaitingByCorrelationKey(@Param("correlationKey") String correlationKey);

    /**
     * 根据流程实例ID查询所有订阅
     * XML: RuntimeEventSubscriptionMapper.xml
     */
    List<RuntimeEventSubscriptionEntity> selectByProcessInstanceId(@Param("processInstanceId") String processInstanceId);

    /**
     * 触发事件（标记为已触发）
     * XML: RuntimeEventSubscriptionMapper.xml
     */
    int trigger(
            @Param("subscriptionId") String subscriptionId,
            @Param("triggeredTime") LocalDateTime triggeredTime,
            @Param("triggeredBy") String triggeredBy
    );

    /**
     * 过期清理
     * XML: RuntimeEventSubscriptionMapper.xml
     */
    int expire(@Param("currentTime") LocalDateTime currentTime);

    /**
     * 取消流程实例的所有订阅
     * XML: RuntimeEventSubscriptionMapper.xml
     */
    int cancelByProcessInstanceId(@Param("processInstanceId") String processInstanceId);

    /**
     * 批量插入运行时事件订阅
     * XML: RuntimeEventSubscriptionMapper.xml
     */
    int batchInsert(@Param("list") List<RuntimeEventSubscriptionEntity> subscriptions);
}
