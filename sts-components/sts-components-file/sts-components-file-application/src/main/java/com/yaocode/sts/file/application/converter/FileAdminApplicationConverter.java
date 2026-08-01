package com.yaocode.sts.file.application.converter;

import com.yaocode.sts.file.application.model.result.StorageNodeInfoResult;
import com.yaocode.sts.file.infrastructure.entity.StorageNodeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 文件管理转换器（MapStruct）
 *
 * @author yaocode
 * @since 1.0.0
 */
@Mapper(componentModel = "spring")
public interface FileAdminApplicationConverter {

    FileAdminApplicationConverter INSTANCE = Mappers.getMapper(FileAdminApplicationConverter.class);

    /**
     * StorageNodeEntity → StorageNodeInfoResult
     */
    @Mapping(target = "usageRate", expression = "java(calculateUsageRate(entity))")
    StorageNodeInfoResult toStorageNodeInfoResult(StorageNodeEntity entity);

    /**
     * 批量转换
     */
    List<StorageNodeInfoResult> toStorageNodeInfoResultList(List<StorageNodeEntity> entities);

    @Named("calculateUsageRate")
    default Double calculateUsageRate(StorageNodeEntity entity) {
        if (entity.getMaxCapacity() == null || entity.getMaxCapacity() <= 0) {
            return 0.0;
        }
        return (double) entity.getUsedCapacity() / entity.getMaxCapacity() * 100;
    }
}
