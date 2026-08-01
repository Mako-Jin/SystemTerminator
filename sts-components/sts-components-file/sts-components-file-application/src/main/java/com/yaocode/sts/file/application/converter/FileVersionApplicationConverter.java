package com.yaocode.sts.file.application.converter;

import com.yaocode.sts.file.application.model.command.CreateVersionCommand;
import com.yaocode.sts.file.application.model.result.VersionInfoResult;
import com.yaocode.sts.file.infrastructure.entity.FileVersionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 文件版本转换器（MapStruct）
 * 替代 FileVersionRuntimeConverter 的手动转换
 *
 * @author yaocode
 * @since 1.0.0
 */
@Mapper(componentModel = "spring", imports = {LocalDateTime.class})
public interface FileVersionApplicationConverter {

    FileVersionApplicationConverter INSTANCE = Mappers.getMapper(FileVersionApplicationConverter.class);

    /**
     * FileVersionEntity → VersionInfoResult
     */
    @Mapping(target = "versionId", source = "versionId")
    @Mapping(target = "fileId", source = "fileId")
    @Mapping(target = "versionNumber", source = "versionNumber")
    @Mapping(target = "versionTag", source = "versionTag")
    @Mapping(target = "versionName", source = "versionName")
    @Mapping(target = "versionRemark", source = "versionRemark")
    @Mapping(target = "changeSummary", source = "changeSummary")
    @Mapping(target = "fileSize", source = "fileSize")
    @Mapping(target = "fileMd5", source = "fileMd5")
    @Mapping(target = "fileSha256", source = "fileSha256")
    @Mapping(target = "fileUrl", source = "storageUrl")
    @Mapping(target = "branchId", source = "branchId")
    @Mapping(target = "isCurrent", source = "isCurrent")
    @Mapping(target = "isLatest", source = "isLatest")
    @Mapping(target = "createdUserId", source = "createdUserId")
    @Mapping(target = "createdUserName", source = "createdUserName")
    @Mapping(target = "createdTime", source = "createdTime")
    @Mapping(target = "updatedTime", source = "updatedTime")
    VersionInfoResult toVersionInfoResult(FileVersionEntity entity);

    /**
     * 批量转换
     */
    List<VersionInfoResult> toVersionInfoResultList(List<FileVersionEntity> entities);

    /**
     * 构建 FileVersionEntity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "versionId", source = "versionId")
    @Mapping(target = "fileId", source = "command.fileId")
    @Mapping(target = "versionNumber", source = "versionNumber")
    @Mapping(target = "versionType", source = "command.versionType")
    @Mapping(target = "versionTag", source = "versionTag")
    @Mapping(target = "versionName", source = "command.versionName")
    @Mapping(target = "versionRemark", source = "command.versionRemark")
    @Mapping(target = "changeSummary", source = "command.changeSummary")
    @Mapping(target = "filePath", source = "filePath")
    @Mapping(target = "fileSize", source = "command.fileSize")
    @Mapping(target = "fileMd5", source = "command.fileMd5")
    @Mapping(target = "fileSha256", source = "command.fileSha256")
    @Mapping(target = "storageUrl", source = "fileUrl")
    @Mapping(target = "parentVersionId", source = "parentVersionId")
    @Mapping(target = "branchId", source = "branchId")
    @Mapping(target = "isCurrent", constant = "false")
    @Mapping(target = "isLatest", constant = "true")
    @Mapping(target = "isDeleted", constant = "false")
    @Mapping(target = "createdUserId", source = "command.userId")
    @Mapping(target = "createdUserName", source = "command.userName")
    @Mapping(target = "createdTime", expression = "java(LocalDateTime.now())")
    @Mapping(target = "updatedTime", expression = "java(LocalDateTime.now())")
    FileVersionEntity toFileVersionEntity(
            CreateVersionCommand command,
            String versionId,
            String filePath,
            String fileUrl,
            String versionTag,
            String parentVersionId,
            String branchId,
            Integer versionNumber
    );
}
