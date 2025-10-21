package com.yaocode.sts.auth.infrastructure.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaocode.sts.auth.infrastructure.po.UserGroupPo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户组mapper
 * @author: Jin-LiangBo
 * @date: 2025年10月14日 19:46
 */
@Mapper
public interface UserGroupMapper extends BaseMapper<UserGroupPo> {
}
