package com.yaocode.sts.auth.infrastructure.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaocode.sts.auth.infrastructure.po.RelUserGroupMemberPo;
import org.apache.ibatis.annotations.Mapper;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年10月23日 21:23
 */
@Mapper
public interface RelUserGroupUserMapper extends BaseMapper<RelUserGroupMemberPo> {
}
