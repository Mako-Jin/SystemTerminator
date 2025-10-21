package com.yaocode.sts.auth.infrastructure.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaocode.sts.auth.infrastructure.po.AuthInfoPo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 资源mapper
 * @author: Jin-LiangBo
 * @date: 2025年10月14日 19:45
 */
@Mapper
public interface AuthInfoMapper extends BaseMapper<AuthInfoPo> {
}
