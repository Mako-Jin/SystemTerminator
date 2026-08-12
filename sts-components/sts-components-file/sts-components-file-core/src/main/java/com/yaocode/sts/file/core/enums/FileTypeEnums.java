package com.yaocode.sts.file.core.enums;

import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 文件类型枚举
 * <p>
 * 对应数据库 file_type 字段（tinyint）
 * </p>
 * <p>
 * 集中管理文件类型、扩展名、MIME类型映射关系
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
@Getter
public enum FileTypeEnums {

    OTHER(0, "其他", "application/octet-stream", Collections.emptyList()),
    IMAGE(1, "图片", "image/", List.of("jpg", "jpeg", "png", "gif", "webp", "bmp", "svg", "ico", "tiff", "tif")),
    VIDEO(2, "视频", "video/", List.of("mp4", "avi", "mkv", "mov", "wmv", "flv", "webm", "mpeg", "mpg", "m4v", "3gp")),
    AUDIO(3, "音频", "audio/", List.of("mp3", "wav", "flac", "aac", "ogg", "wma", "m4a")),
    TEXT(4, "文本", "text/", List.of("txt", "log", "md", "readme")),
    PDF(5, "PDF文档", "application/pdf", List.of("pdf")),
    WORD(6, "Word文档", "application/msword", List.of("doc", "docx")),
    EXCEL(7, "Excel表格", "application/vnd.ms-excel", List.of("xls", "xlsx", "csv")),
    PPT(8, "PPT演示文稿", "application/vnd.ms-powerpoint", List.of("ppt", "pptx")),
    COMPRESSED(9, "压缩文件", "application/zip", List.of("zip", "rar", "7z", "tar", "gz", "bz2", "xz")),
    CODE(10, "代码文件", "text/plain", List.of("java", "py", "js", "ts", "go", "rs", "c", "cpp", "h", "hpp", "cs", "php", "rb", "swift", "kt")),
    JSON(11, "JSON数据", "application/json", List.of("json")),
    XML(12, "XML数据", "application/xml", List.of("xml", "xsd", "xslt")),
    EXECUTABLE(13, "可执行文件", "application/x-msdownload", List.of("exe", "msi", "sh", "bat", "cmd")),
    DATABASE(14, "数据库文件", "application/x-sqlite3", List.of("db", "sqlite", "sqlite3")),
    FONT(15, "字体文件", "application/x-font-ttf", List.of("ttf", "otf", "woff", "woff2"));

    private final int code;
    private final String name;
    private final String mimePrefix;
    private final List<String> extensions;

    // 预编译映射缓存
    private static final Map<String, FileTypeEnums> EXTENSION_MAP;
    private static final Map<String, FileTypeEnums> MIME_PREFIX_MAP;

    static {
        // 扩展名映射
        EXTENSION_MAP = Collections.unmodifiableMap(
                java.util.stream.Stream.of(values())
                        .flatMap(type -> type.extensions.stream().map(ext -> Map.entry(ext, type)))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
        );

        // MIME前缀映射（精确匹配优先）
        MIME_PREFIX_MAP = Collections.unmodifiableMap(
                java.util.stream.Stream.of(values())
                        .filter(type -> !"application/octet-stream".equals(type.getMimePrefix()))
                        .collect(Collectors.toMap(FileTypeEnums::getMimePrefix, type -> type))
        );
    }

    FileTypeEnums(int code, String name, String mimePrefix, List<String> extensions) {
        this.code = code;
        this.name = name;
        this.mimePrefix = mimePrefix;
        this.extensions = extensions != null ? Collections.unmodifiableList(extensions) : Collections.emptyList();
    }

    // ==================== 查询方法 ====================

    /**
     * 根据MIME类型获取文件类型枚举
     */
    public static FileTypeEnums fromMimeType(String mimeType) {
        if (mimeType == null || mimeType.isEmpty()) {
            return OTHER;
        }

        String lowerMime = mimeType.toLowerCase();

        // 精确匹配MIME前缀
        FileTypeEnums exactMatch = MIME_PREFIX_MAP.get(lowerMime);
        if (exactMatch != null) {
            return exactMatch;
        }

        // 前缀匹配
        for (FileTypeEnums type : values()) {
            if (lowerMime.startsWith(type.getMimePrefix())
                    && !"application/octet-stream".equals(type.getMimePrefix())) {
                return type;
            }
        }

        // 特殊关键词匹配
        if (lowerMime.contains("pdf")) return PDF;
        if (lowerMime.contains("msword") || lowerMime.contains("document") || lowerMime.contains("wordprocessingml")) {
            return WORD;
        }
        if (lowerMime.contains("excel") || lowerMime.contains("sheet") || lowerMime.contains("spreadsheetml")) {
            return EXCEL;
        }
        if (lowerMime.contains("powerpoint") || lowerMime.contains("presentation")) {
            return PPT;
        }
        if (lowerMime.contains("zip") || lowerMime.contains("compressed") || lowerMime.contains("rar") || lowerMime.contains("7z")) {
            return COMPRESSED;
        }
        if (lowerMime.contains("json")) return JSON;
        if (lowerMime.contains("xml")) return XML;

        return OTHER;
    }

    /**
     * 根据文件扩展名获取文件类型枚举
     */
    public static FileTypeEnums fromExtension(String extension) {
        if (extension == null || extension.isEmpty()) {
            return OTHER;
        }
        String ext = extension.toLowerCase().trim();
        return EXTENSION_MAP.getOrDefault(ext, OTHER);
    }

    /**
     * 根据code获取枚举
     */
    public static FileTypeEnums fromCode(Integer code) {
        if (code == null) {
            return OTHER;
        }
        for (FileTypeEnums type : values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return OTHER;
    }

    // ==================== 判断方法 ====================

    public static boolean isImage(int code) {
        return code == IMAGE.getCode();
    }

    public static boolean isVideo(int code) {
        return code == VIDEO.getCode();
    }

    public static boolean isAudio(int code) {
        return code == AUDIO.getCode();
    }

    public static boolean isDocument(int code) {
        return code == PDF.getCode() || code == WORD.getCode()
                || code == EXCEL.getCode() || code == PPT.getCode();
    }

    public static boolean isCompressed(int code) {
        return code == COMPRESSED.getCode();
    }

    /**
     * 判断是否为媒体文件（图片、视频、音频）
     */
    public static boolean isMedia(int code) {
        return code == IMAGE.getCode() || code == VIDEO.getCode() || code == AUDIO.getCode();
    }

    /**
     * 判断扩展名是否属于当前类型
     */
    public boolean containsExtension(String extension) {
        if (extension == null) {
            return false;
        }
        return extensions.contains(extension.toLowerCase().trim());
    }

    // ==================== 获取集合方法 ====================

    /**
     * 获取所有图片扩展名
     */
    public static List<String> getImageExtensions() {
        return IMAGE.getExtensions();
    }

    /**
     * 获取所有视频扩展名
     */
    public static List<String> getVideoExtensions() {
        return VIDEO.getExtensions();
    }

    /**
     * 获取所有文档扩展名
     */
    public static List<String> getDocumentExtensions() {
        return List.of(PDF, WORD, EXCEL, PPT).stream()
                .flatMap(type -> type.getExtensions().stream())
                .collect(Collectors.toList());
    }

    /**
     * 获取所有媒体扩展名（图片、视频、音频）
     */
    public static List<String> getMediaExtensions() {
        return List.of(IMAGE, VIDEO, AUDIO).stream()
                .flatMap(type -> type.getExtensions().stream())
                .collect(Collectors.toList());
    }

    /**
     * 获取所有扩展名映射
     */
    public static Map<String, FileTypeEnums> getExtensionMap() {
        return EXTENSION_MAP;
    }
}