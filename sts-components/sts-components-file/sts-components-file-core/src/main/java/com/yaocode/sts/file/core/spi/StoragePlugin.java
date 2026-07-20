package com.yaocode.sts.file.core.spi;

import com.yaocode.sts.file.core.enums.StorageTypeEnums;

import java.io.InputStream;

/**
 * 存储插件SPI接口
 * <p>
 * 所有存储插件必须实现此接口，通过SPI机制加载
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
public interface StoragePlugin {

    /**
     * 获取存储类型
     */
    StorageTypeEnums getStorageType();

    /**
     * 获取插件名称
     */
    String getPluginName();

    /**
     * 上传文件
     *
     * @param inputStream 文件输入流
     * @param fileName    文件名
     * @param fileSize    文件大小
     * @return 文件存储路径
     */
    String upload(InputStream inputStream, String fileName, Long fileSize);

    /**
     * 获取文件URL
     *
     * @param filePath 文件存储路径
     * @return 访问URL
     */
    String getFileUrl(String filePath);

    /**
     * 下载文件
     *
     * @param filePath 文件存储路径
     * @return 文件输入流
     */
    InputStream download(String filePath);

    /**
     * 删除文件
     *
     * @param filePath 文件存储路径
     */
    void delete(String filePath);

    /**
     * 检查文件是否存在
     *
     * @param filePath 文件存储路径
     * @return 是否存在
     */
    boolean exists(String filePath);

    /**
     * 上传分片
     */
    String uploadChunk(InputStream inputStream, String uploadId, int chunkNumber, long chunkSize);

    /**
     * 合并分片
     */
    String mergeChunks(String uploadId, String fileId);

    /**
     * 清理分片
     */
    void cleanupChunks(String uploadId);
}
