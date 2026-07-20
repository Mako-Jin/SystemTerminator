package com.yaocode.sts.components.file.runtime.service;

import com.yaocode.sts.components.file.runtime.model.command.AddStorageNodeCommand;
import com.yaocode.sts.components.file.runtime.model.command.UpdateStorageNodeCommand;
import com.yaocode.sts.components.file.runtime.model.result.*;

import java.util.List;

/**
 * 文件存储服务接口
 * <p>
 * 提供文件存储相关的底层操作，包括：
 * <ul>
 *   <li>文件上传/下载/删除</li>
 *   <li>文件归档/取消归档</li>
 *   <li>文件迁移（同步/异步）</li>
 *   <li>存储节点管理</li>
 *   <li>存储连接测试</li>
 * </ul>
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
public interface FileStorageService {

    // ==================== 1. 文件基础操作 ====================

    /**
     * 删除存储文件
     *
     * @param filePath 文件路径
     * @param storageType 存储类型
     * @throws Exception 删除失败时抛出异常
     */
    void deleteFile(String filePath, Integer storageType) throws Exception;

    /**
     * 归档文件（迁移到冷存储）
     *
     * @param filePath 文件路径
     * @param storageType 当前存储类型
     * @param archiveType 归档类型（GLACIER, DEEP_ARCHIVE, STANDARD_IA）
     * @return 归档后的存储位置
     */
    String archiveFile(String filePath, String storageType, String archiveType);

    /**
     * 取消归档（从冷存储恢复到热存储）
     *
     * @param archiveLocation 归档位置
     * @param storageType 目标存储类型
     * @return 恢复后的文件路径
     */
    String unarchiveFile(String archiveLocation, String storageType);

    /**
     * 同步迁移文件
     *
     * @param filePath 文件路径
     * @param sourceStorageType 源存储类型
     * @param targetStorageType 目标存储类型
     * @param keepSource 是否保留源文件
     * @return 新文件路径
     */
    String migrateFile(
            String filePath,
            Integer sourceStorageType,
            Integer targetStorageType,
            boolean keepSource
    );

    /**
     * 异步迁移文件
     *
     * @param filePath 文件路径
     * @param sourceStorageType 源存储类型
     * @param targetStorageType 目标存储类型
     * @param taskId 任务ID
     * @param keepSource 是否保留源文件
     */
    void migrateFileAsync(
            String filePath,
            Integer sourceStorageType,
            Integer targetStorageType,
            String taskId,
            boolean keepSource
    );

    /**
     * 获取迁移任务状态
     *
     * @param taskId 任务ID
     * @return 迁移任务状态
     */
    MigrateTaskStatusResult getMigrateTaskStatus(String taskId);

    /**
     * 取消迁移任务
     *
     * @param taskId 任务ID
     */
    void cancelMigrateTask(String taskId);

    // ==================== 2. 存储连接测试 ====================

    /**
     * 测试存储连接
     *
     * @param storageType 存储类型
     * @param endpoint 端点地址
     * @param accessKey 访问密钥
     * @param secretKey 秘密密钥
     * @return 是否连接成功
     */
    boolean testConnection(
            String storageType,
            String endpoint,
            String accessKey,
            String secretKey
    );

    // ==================== 3. 存储节点管理 ====================

    /**
     * 获取存储节点统计信息
     *
     * @param tenantId 租户ID
     * @return 存储节点统计列表
     */
    List<StorageNodeStatsResult> getStorageNodeStats(String tenantId);

    /**
     * 获取存储节点列表
     *
     * @param enabledOnly 是否只获取启用的节点
     * @param storageType 存储类型（可选）
     * @param tenantId 租户ID
     * @return 存储节点列表
     */
    List<StorageNodeInfoResult> getStorageNodes(
            Boolean enabledOnly,
            String storageType,
            String tenantId
    );

    /**
     * 添加存储节点
     *
     * @param command 添加命令
     * @return 创建结果
     */
    StorageNodeInfoResult addStorageNode(AddStorageNodeCommand command);

    /**
     * 更新存储节点
     *
     * @param command 更新命令
     * @return 更新结果
     */
    StorageNodeInfoResult updateStorageNode(UpdateStorageNodeCommand command);

    /**
     * 删除存储节点
     *
     * @param nodeId 节点ID
     * @param force 是否强制删除（忽略文件存在检查）
     */
    void deleteStorageNode(Long nodeId, boolean force);

    /**
     * 测试存储节点连接
     *
     * @param nodeId 节点ID
     * @return 测试结果
     */
    ConnectionTestResult testStorageNode(Long nodeId);

    /**
     * 刷新存储节点状态
     *
     * @param nodeId 节点ID
     * @return 最新状态
     */
    StorageNodeInfoResult refreshStorageNode(Long nodeId);
}