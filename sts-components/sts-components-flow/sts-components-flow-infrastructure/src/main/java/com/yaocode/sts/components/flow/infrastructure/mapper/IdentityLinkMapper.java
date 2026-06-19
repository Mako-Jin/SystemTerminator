package com.yaocode.sts.components.flow.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaocode.sts.components.flow.infrastructure.entity.IdentityLinkEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 身份关联 Mapper
 * 对应表: flow_tbl_identitylink
 */
@Mapper
public interface IdentityLinkMapper extends BaseMapper<IdentityLinkEntity> {

    /**
     * 根据任务ID查询身份关联
     * XML: IdentityLinkMapper.xml
     */
    List<IdentityLinkEntity> selectByTaskId(@Param("taskId") String taskId);

    /**
     * 根据流程实例ID查询身份关联
     * XML: IdentityLinkMapper.xml
     */
    List<IdentityLinkEntity> selectByProcessInstanceId(@Param("processInstanceId") String processInstanceId);

    /**
     * 根据用户ID查询任务关联
     * XML: IdentityLinkMapper.xml
     */
    List<IdentityLinkEntity> selectByUserId(@Param("userId") String userId);

    /**
     * 根据组ID查询任务关联
     * XML: IdentityLinkMapper.xml
     */
    List<IdentityLinkEntity> selectByGroupId(@Param("groupId") String groupId);

    /**
     * 批量插入身份关联
     * XML: IdentityLinkMapper.xml
     */
    int batchInsert(@Param("list") List<IdentityLinkEntity> identityLinks);

    /**
     * 根据任务ID删除身份关联
     * XML: IdentityLinkMapper.xml
     */
    int deleteByTaskId(@Param("taskId") String taskId);

    /**
     * 根据流程实例ID删除身份关联
     * XML: IdentityLinkMapper.xml
     */
    int deleteByProcessInstanceId(@Param("processInstanceId") String processInstanceId);

    /**
     * 撤销身份关联
     * XML: IdentityLinkMapper.xml
     */
    int revoke(
            @Param("identityLinkId") String identityLinkId,
            @Param("revokeTime") java.time.LocalDateTime revokeTime
    );
}
