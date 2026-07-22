package com.yaocode.sts.file.core.enums;

import lombok.Getter;

/**
 * 文件类型枚举
 * <p>
 * 对应数据库 file_type 字段（tinyint）
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
@Getter
public enum FileTypeEnums {

    OTHER(0, "其他", "application/octet-stream"),
    IMAGE(1, "图片", "image/"),
    VIDEO(2, "视频", "video/"),
    AUDIO(3, "音频", "audio/"),
    TEXT(4, "文本", "text/"),
    PDF(5, "PDF文档", "application/pdf"),
    WORD(6, "Word文档", "application/msword"),
    EXCEL(7, "Excel表格", "application/vnd.ms-excel"),
    PPT(8, "PPT演示文稿", "application/vnd.ms-powerpoint"),
    COMPRESSED(9, "压缩文件", "application/zip"),
    CODE(10, "代码文件", "text/plain"),
    JSON(11, "JSON数据", "application/json"),
    XML(12, "XML数据", "application/xml"),
    EXECUTABLE(13, "可执行文件", "application/x-msdownload"),
    DATABASE(14, "数据库文件", "application/x-sqlite3"),
    FONT(15, "字体文件", "application/x-font-ttf");

    private final int code;
    private final String name;
    private final String mimePrefix;

    FileTypeEnums(int code, String name, String mimePrefix) {
        this.code = code;
        this.name = name;
        this.mimePrefix = mimePrefix;
    }

    /**
     * 根据MIME类型获取文件类型枚举
     */
    public static FileTypeEnums fromMimeType(String mimeType) {
        if (mimeType == null || mimeType.isEmpty()) {
            return OTHER;
        }

        String lowerMime = mimeType.toLowerCase();

        // 精确匹配
        for (FileTypeEnums type : values()) {
            if (lowerMime.equals(type.getMimePrefix())) {
                return type;
            }
        }

        // 前缀匹配
        for (FileTypeEnums type : values()) {
            if (lowerMime.startsWith(type.getMimePrefix()) && !type.getMimePrefix().equals("application/octet-stream")) {
                return type;
            }
        }

        // 特殊匹配
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
        return switch (ext) {
            // 图片
            case "jpg", "jpeg", "png", "gif", "bmp", "svg", "webp", "ico", "tiff", "tif" -> IMAGE;
            // 视频
            case "mp4", "avi", "mov", "wmv", "flv", "mkv", "webm", "mpeg", "mpg", "m4v", "3gp" -> VIDEO;
            // 音频
            case "mp3", "wav", "flac", "aac", "ogg", "wma", "m4a" -> AUDIO;
            // 文本
            case "txt", "log", "md", "readme" -> TEXT;
            // PDF
            case "pdf" -> PDF;
            // Word
            case "doc", "docx" -> WORD;
            // Excel
            case "xls", "xlsx", "csv" -> EXCEL;
            // PPT
            case "ppt", "pptx" -> PPT;
            // 压缩
            case "zip", "rar", "7z", "tar", "gz", "bz2", "xz" -> COMPRESSED;
            // 代码
            case "java", "py", "js", "ts", "go", "rs", "c", "cpp", "h", "hpp", "cs", "php", "rb", "swift", "kt" -> CODE;
            // JSON/XML
            case "json" -> JSON;
            case "xml", "xsd", "xslt" -> XML;
            // 可执行文件
            case "exe", "msi", "sh", "bat", "cmd" -> EXECUTABLE;
            // 字体
            case "ttf", "otf", "woff", "woff2" -> FONT;
            default -> OTHER;
        };
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
        return code == PDF.getCode() || code == WORD.getCode() ||
                code == EXCEL.getCode() || code == PPT.getCode();
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
}
