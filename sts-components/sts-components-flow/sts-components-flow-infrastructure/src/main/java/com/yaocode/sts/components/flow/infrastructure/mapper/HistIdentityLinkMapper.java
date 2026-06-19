package com.yaocode.sts.components.flow.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaocode.sts.components.flow.infrastructure.entity.HistIdentityLinkEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 历史身份关联 Mapper
 * 对应表: flow_tbl_hist_identitylink
 */
@Mapper
public interface HistIdentityLinkMapper extends BaseMapper<HistIdentityLinkEntity> {

    /**
     * 根据流程实例ID查询历史身份关联
     * XML: HistIdentityLinkMapper.xml
     */
    List<HistIdentityLinkEntity> selectByProcessInstanceId(@Param("processInstanceId") String processInstanceId);

    /**
     * 根据任务ID查询历史身份关联
     * XML: HistIdentityLinkMapper.xml
     */
    List<HistIdentityLinkEntity> selectByTaskId(@Param("taskId") String taskId);

    /**
     * 根据用户ID查询历史身份关联
     * XML: HistIdentityLinkMapper.xml
     */
    List<HistIdentityLinkEntity> selectByUserId(@Param("userId") String userId);

    /**
     * 批量清理历史身份关联
     * XML: HistIdentityLinkMapper.xml
     */
    int batchCleanup(
            @Param("removalTime") java.time.LocalDateTime removalTime,
            @Param("limit") Integer limit
    );
}
