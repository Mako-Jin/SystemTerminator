package com.yaocode.sts.auth.infrastructure.mybatis.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaocode.sts.auth.infrastructure.mybatis.dao.ContactInfoDao;
import com.yaocode.sts.auth.infrastructure.mybatis.mapper.ContactInfoMapper;
import com.yaocode.sts.auth.infrastructure.po.ResourceContactPo;
import org.springframework.stereotype.Repository;

/**
 * 联系人信息Dao
 * @author: Jin-LiangBo
 * @date: 2026年01月30日 16:37
 */
@Repository
public class ContactInfoDaoImpl extends ServiceImpl<ContactInfoMapper, ResourceContactPo> implements ContactInfoDao {
}
