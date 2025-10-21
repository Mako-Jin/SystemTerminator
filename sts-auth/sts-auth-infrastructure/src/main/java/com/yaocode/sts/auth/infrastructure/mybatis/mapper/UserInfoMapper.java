package com.yaocode.sts.auth.infrastructure.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaocode.sts.auth.infrastructure.po.UserInfoPo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户信息db mapper
 * @version 1.0
 * @author: Jin-LiangBo
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfoPo> {
}
