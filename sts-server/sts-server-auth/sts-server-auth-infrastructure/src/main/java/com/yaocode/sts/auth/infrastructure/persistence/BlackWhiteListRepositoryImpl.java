package com.yaocode.sts.auth.infrastructure.persistence;

import com.yaocode.sts.auth.domain.entity.BlackWhiteListEntity;
import com.yaocode.sts.auth.domain.repository.BlackWhiteListRepository;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.BlackWhiteListId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BlackWhiteListRepositoryImpl implements BlackWhiteListRepository {
    @Override
    public List<BlackWhiteListEntity> findByTenantIdOrNullAndEnabled(String tenantId) {
        return List.of();
    }

    @Override
    public List<BlackWhiteListEntity> findByTenantIdAndEnabled(String tenantId) {
        return List.of();
    }

    @Override
    public List<BlackWhiteListEntity> findByTenantIdIsNullAndEnabled() {
        return List.of();
    }

    @Override
    public Optional<BlackWhiteListEntity> findById(BlackWhiteListId blackWhiteListId) {
        return Optional.empty();
    }

    @Override
    public BlackWhiteListId save(BlackWhiteListEntity rule) {
        return null;
    }

    @Override
    public void delete(BlackWhiteListEntity aggregate) {

    }

    @Override
    public void deleteById(String listId) {

    }
}
