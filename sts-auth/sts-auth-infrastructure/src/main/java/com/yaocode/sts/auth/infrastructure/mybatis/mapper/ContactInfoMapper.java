package com.yaocode.sts.auth.infrastructure.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaocode.sts.auth.infrastructure.po.ContactInfoPo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 联系人Mapper
 * @author: Jin-LiangBo
 * @date: 2026年01月30日 16:38
 */
@Mapper
public interface ContactInfoMapper extends BaseMapper<ContactInfoPo> {
}
