package com.yaocode.sts.auth.infrastructure.persistence;

import com.yaocode.sts.auth.domain.entity.OrganizationInfoEntity;
import com.yaocode.sts.auth.domain.repository.OrganizationRepository;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.OrganizationId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserId;
import com.yaocode.sts.auth.infrastructure.mybatis.dao.OrganizationInfoDao;
import com.yaocode.sts.auth.infrastructure.mybatis.dao.RelOrganizationUserDao;
import com.yaocode.sts.auth.infrastructure.po.RelOrganizationUserPo;
import com.yaocode.sts.common.tools.id.IdFactory;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

/**
 * 组织结构仓库实现类
 * @author: Jin-LiangBo
 * @date: 2025年10月14日 20:00
 */
@Repository
public class OrganizationRepositoryImpl implements OrganizationRepository {

    @Resource
    private OrganizationInfoDao organizationInfoDao;

    @Resource
    private RelOrganizationUserDao relOrganizationUserDao;

    @Override
    public Optional<OrganizationInfoEntity> findById(OrganizationId organizationId) {
        return Optional.empty();
    }

    @Override
    public OrganizationId save(OrganizationInfoEntity aggregate) {
        return null;
    }

    @Override
    public void delete(OrganizationInfoEntity aggregate) {

    }

    @Override
    public void saveRelOrganizationUser(TenantId tenantId, OrganizationId organizationId, UserId userId) {
        RelOrganizationUserPo relOrganizationUserPo = relOrganizationUserDao.getByOrgIdAndUserId(
                tenantId.getValue(), organizationId.getValue(), userId.getValue()
        );
        if (Objects.nonNull(relOrganizationUserPo)) {
            // TODO 感觉这块应该记个什么日志
            return;
        }
        relOrganizationUserPo = new RelOrganizationUserPo();
        relOrganizationUserPo.setRelId(IdFactory.generate());
        relOrganizationUserPo.setTenantId(tenantId.getValue());
        relOrganizationUserPo.setOrganizationId(organizationId.getValue());
        relOrganizationUserPo.setUserId(userId.getValue());
        relOrganizationUserDao.save(relOrganizationUserPo);
    }
}
