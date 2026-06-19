package com.yaocode.sts.components.flow.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaocode.sts.components.flow.infrastructure.entity.EventSubscriptionDefinitionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 事件订阅定义 Mapper
 * 对应表: flow_tbl_event_subscription_definition
 */
@Mapper
public interface EventSubscriptionDefinitionMapper extends BaseMapper<EventSubscriptionDefinitionEntity> {

    /**
     * 根据流程定义ID查询所有事件订阅
     * XML: EventSubscriptionDefinitionMapper.xml
     */
    List<EventSubscriptionDefinitionEntity> selectByProcessId(@Param("processId") String processId);

    /**
     * 根据事件名称和类型查询
     * XML: EventSubscriptionDefinitionMapper.xml
     */
    List<EventSubscriptionDefinitionEntity> selectByEventNameAndType(
            @Param("eventName") String eventName,
            @Param("eventType") Integer eventType
    );

    /**
     * 根据目标节点查询
     * XML: EventSubscriptionDefinitionMapper.xml
     */
    List<EventSubscriptionDefinitionEntity> selectByTargetNode(@Param("targetNodeKey") String targetNodeKey);

    /**
     * 批量插入事件订阅定义
     * XML: EventSubscriptionDefinitionMapper.xml
     */
    int batchInsert(@Param("list") List<EventSubscriptionDefinitionEntity> subscriptions);
}
