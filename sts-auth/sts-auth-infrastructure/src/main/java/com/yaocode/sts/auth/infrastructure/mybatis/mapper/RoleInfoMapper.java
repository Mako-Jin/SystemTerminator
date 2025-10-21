package com.yaocode.sts.auth.infrastructure.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaocode.sts.auth.infrastructure.po.RoleInfoPo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色Mapper
 * @author: Jin-LiangBo
 * @date: 2025年10月14日 19:46
 */
@Mapper
public interface RoleInfoMapper extends BaseMapper<RoleInfoPo> {
}
