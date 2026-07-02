package com.yaocode.sts.common.basic.constants;

import java.io.File;
import java.nio.file.FileSystems;

/**
 * 文件路径相关常量
 * <p>提供通用的路径前缀和文件系统相关常量</p>
 * @author: Jin-LiangBo
 * @date: 2026年07月03日
 */
public interface PathConstants {

    /**
     * Classpath 路径前缀
     */
    String CLASSPATH_PREFIX = "classpath:";

    /**
     * 文件系统路径前缀
     */
    String FILE_PREFIX = "file:";

    /**
     * URL 路径前缀
     */
    String URL_PREFIX = "url:";

    /**
     * HTTP URL 前缀
     */
    String HTTP_PREFIX = "http:";

    /**
     * HTTPS URL 前缀
     */
    String HTTPS_PREFIX = "https:";

    /**
     * 当前目录
     */
    String CURRENT_DIR = ".";

    /**
     * 父目录
     */
    String PARENT_DIR = "..";

    /**
     * 路径分隔符（Unix）
     */
    String PATH_SEPARATOR_UNIX = "/";

    /**
     * 路径分隔符（Windows）
     */
    String PATH_SEPARATOR_WINDOWS = "\\";

    /**
     * 系统路径分隔符
     */
    String PATH_SEPARATOR = File.pathSeparator;

    /**
     * 文件分隔符
     */
    String FILE_SEPARATOR = FileSystems.getDefault().getSeparator();

}
