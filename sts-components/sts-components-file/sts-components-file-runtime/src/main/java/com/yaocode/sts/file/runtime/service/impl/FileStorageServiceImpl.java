package com.yaocode.sts.file.runtime.service.impl;

import com.yaocode.sts.file.runtime.model.command.AddStorageNodeCommand;
import com.yaocode.sts.file.runtime.model.command.UpdateStorageNodeCommand;
import com.yaocode.sts.file.runtime.model.result.ConnectionTestResult;
import com.yaocode.sts.file.runtime.model.result.MigrateTaskStatusResult;
import com.yaocode.sts.file.runtime.model.result.StorageNodeInfoResult;
import com.yaocode.sts.file.runtime.model.result.StorageNodeStatsResult;
import com.yaocode.sts.file.runtime.service.FileStorageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    @Override
    public void deleteFile(String filePath, Integer storageType) throws Exception {

    }

    @Override
    public String archiveFile(String filePath, String storageType, String archiveType) {
        return "";
    }

    @Override
    public String unarchiveFile(String archiveLocation, String storageType) {
        return "";
    }

    @Override
    public String migrateFile(String filePath, Integer sourceStorageType, Integer targetStorageType, boolean keepSource) {
        return "";
    }

    @Override
    public void migrateFileAsync(String filePath, Integer sourceStorageType, Integer targetStorageType, String taskId, boolean keepSource) {

    }

    @Override
    public MigrateTaskStatusResult getMigrateTaskStatus(String taskId) {
        return null;
    }

    @Override
    public void cancelMigrateTask(String taskId) {

    }

    @Override
    public boolean testConnection(String storageType, String endpoint, String accessKey, String secretKey) {
        return false;
    }

    @Override
    public List<StorageNodeStatsResult> getStorageNodeStats(String tenantId) {
        return List.of();
    }

    @Override
    public List<StorageNodeInfoResult> getStorageNodes(Boolean enabledOnly, String storageType, String tenantId) {
        return List.of();
    }

    @Override
    public StorageNodeInfoResult addStorageNode(AddStorageNodeCommand command) {
        return null;
    }

    @Override
    public StorageNodeInfoResult updateStorageNode(UpdateStorageNodeCommand command) {
        return null;
    }

    @Override
    public void deleteStorageNode(Long nodeId, boolean force) {

    }

    @Override
    public ConnectionTestResult testStorageNode(Long nodeId) {
        return null;
    }

    @Override
    public StorageNodeInfoResult refreshStorageNode(Long nodeId) {
        return null;
    }
}
