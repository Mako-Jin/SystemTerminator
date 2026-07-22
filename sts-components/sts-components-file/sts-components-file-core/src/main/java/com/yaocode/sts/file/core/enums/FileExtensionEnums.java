package com.yaocode.sts.file.core.enums;

import lombok.Getter;

/**
 * 文件扩展名枚举
 * <p>
 * 对应数据库 file_extension 字段（tinyint）
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
@Getter
public enum FileExtensionEnums {

    // ========== 未知 ==========
    UNKNOWN(0, "未知", "unknown", "application/octet-stream", FileTypeEnums.OTHER),

    // ========== 图片 ==========
    JPG(1, "JPG图片", "jpg", "image/jpeg", FileTypeEnums.IMAGE),
    JPEG(2, "JPEG图片", "jpeg", "image/jpeg", FileTypeEnums.IMAGE),
    PNG(3, "PNG图片", "png", "image/png", FileTypeEnums.IMAGE),
    GIF(4, "GIF图片", "gif", "image/gif", FileTypeEnums.IMAGE),
    BMP(5, "BMP图片", "bmp", "image/bmp", FileTypeEnums.IMAGE),
    SVG(6, "SVG矢量图", "svg", "image/svg+xml", FileTypeEnums.IMAGE),
    WEBP(7, "WEBP图片", "webp", "image/webp", FileTypeEnums.IMAGE),
    ICO(8, "ICO图标", "ico", "image/x-icon", FileTypeEnums.IMAGE),
    TIFF(9, "TIFF图片", "tiff", "image/tiff", FileTypeEnums.IMAGE),

    // ========== 视频 ==========
    MP4(10, "MP4视频", "mp4", "video/mp4", FileTypeEnums.VIDEO),
    AVI(11, "AVI视频", "avi", "video/x-msvideo", FileTypeEnums.VIDEO),
    MOV(12, "MOV视频", "mov", "video/quicktime", FileTypeEnums.VIDEO),
    WMV(13, "WMV视频", "wmv", "video/x-ms-wmv", FileTypeEnums.VIDEO),
    FLV(14, "FLV视频", "flv", "video/x-flv", FileTypeEnums.VIDEO),
    MKV(15, "MKV视频", "mkv", "video/x-matroska", FileTypeEnums.VIDEO),
    WEBM(16, "WEBM视频", "webm", "video/webm", FileTypeEnums.VIDEO),
    MPEG(17, "MPEG视频", "mpeg", "video/mpeg", FileTypeEnums.VIDEO),

    // ========== 音频 ==========
    MP3(18, "MP3音频", "mp3", "audio/mpeg", FileTypeEnums.AUDIO),
    WAV(19, "WAV音频", "wav", "audio/wav", FileTypeEnums.AUDIO),
    FLAC(20, "FLAC音频", "flac", "audio/flac", FileTypeEnums.AUDIO),
    AAC(21, "AAC音频", "aac", "audio/aac", FileTypeEnums.AUDIO),
    OGG(22, "OGG音频", "ogg", "audio/ogg", FileTypeEnums.AUDIO),
    WMA(23, "WMA音频", "wma", "audio/x-ms-wma", FileTypeEnums.AUDIO),

    // ========== 文本 ==========
    TXT(24, "文本文件", "txt", "text/plain", FileTypeEnums.TEXT),
    LOG(25, "日志文件", "log", "text/plain", FileTypeEnums.TEXT),
    MD(26, "Markdown文件", "md", "text/markdown", FileTypeEnums.TEXT),

    // ========== 文档 ==========
    PDF(27, "PDF文档", "pdf", "application/pdf", FileTypeEnums.PDF),
    DOC(28, "Word文档", "doc", "application/msword", FileTypeEnums.WORD),
    DOCX(29, "Word文档", "docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", FileTypeEnums.WORD),
    XLS(30, "Excel表格", "xls", "application/vnd.ms-excel", FileTypeEnums.EXCEL),
    XLSX(31, "Excel表格", "xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", FileTypeEnums.EXCEL),
    CSV(32, "CSV文件", "csv", "text/csv", FileTypeEnums.EXCEL),
    PPT(33, "PPT演示文稿", "ppt", "application/vnd.ms-powerpoint", FileTypeEnums.PPT),
    PPTX(34, "PPT演示文稿", "pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation", FileTypeEnums.PPT),

    // ========== 压缩 ==========
    ZIP(35, "ZIP压缩文件", "zip", "application/zip", FileTypeEnums.COMPRESSED),
    RAR(36, "RAR压缩文件", "rar", "application/x-rar-compressed", FileTypeEnums.COMPRESSED),
    SEVEN_Z(37, "7Z压缩文件", "7z", "application/x-7z-compressed", FileTypeEnums.COMPRESSED),
    TAR(38, "TAR归档文件", "tar", "application/x-tar", FileTypeEnums.COMPRESSED),
    GZ(39, "GZ压缩文件", "gz", "application/gzip", FileTypeEnums.COMPRESSED),
    BZ2(40, "BZ2压缩文件", "bz2", "application/x-bzip2", FileTypeEnums.COMPRESSED),

    // ========== 代码 ==========
    JAVA(41, "Java文件", "java", "text/x-java", FileTypeEnums.CODE),
    PY(42, "Python文件", "py", "text/x-python", FileTypeEnums.CODE),
    JS(43, "JavaScript文件", "js", "application/javascript", FileTypeEnums.CODE),
    TS(44, "TypeScript文件", "ts", "application/typescript", FileTypeEnums.CODE),
    GO(45, "Go文件", "go", "text/x-go", FileTypeEnums.CODE),
    RS(46, "Rust文件", "rs", "text/x-rust", FileTypeEnums.CODE),
    C(47, "C文件", "c", "text/x-c", FileTypeEnums.CODE),
    CPP(48, "C++文件", "cpp", "text/x-c++", FileTypeEnums.CODE),

    // ========== 数据格式 ==========
    JSON(49, "JSON文件", "json", "application/json", FileTypeEnums.JSON),
    XML(50, "XML文件", "xml", "application/xml", FileTypeEnums.XML),

    // ========== 可执行文件 ==========
    EXE(51, "可执行文件", "exe", "application/x-msdownload", FileTypeEnums.EXECUTABLE),
    SH(52, "Shell脚本", "sh", "application/x-sh", FileTypeEnums.EXECUTABLE),
    BAT(53, "批处理文件", "bat", "application/x-bat", FileTypeEnums.EXECUTABLE),

    // ========== 字体 ==========
    TTF(54, "TTF字体", "ttf", "application/x-font-ttf", FileTypeEnums.FONT),
    OTF(55, "OTF字体", "otf", "application/x-font-otf", FileTypeEnums.FONT),
    WOFF(56, "WOFF字体", "woff", "application/font-woff", FileTypeEnums.FONT),
    WOFF2(57, "WOFF2字体", "woff2", "application/font-woff2", FileTypeEnums.FONT);

    private final int code;
    private final String name;
    private final String extension;
    private final String mimeType;
    private final FileTypeEnums fileType;

    FileExtensionEnums(int code, String name, String extension, String mimeType, FileTypeEnums fileType) {
        this.code = code;
        this.name = name;
        this.extension = extension;
        this.mimeType = mimeType;
        this.fileType = fileType;
    }

    /**
     * 根据扩展名字符串获取枚举
     */
    public static FileExtensionEnums fromExtension(String extension) {
        if (extension == null || extension.isEmpty()) {
            return UNKNOWN;
        }

        String ext = extension.toLowerCase().trim();
        for (FileExtensionEnums e : values()) {
            if (e.getExtension().equals(ext)) {
                return e;
            }
        }
        return UNKNOWN;
    }

    /**
     * 根据code获取枚举
     */
    public static FileExtensionEnums fromCode(Integer code) {
        if (code == null) {
            return UNKNOWN;
        }
        for (FileExtensionEnums e : values()) {
            if (e.getCode() == code) {
                return e;
            }
        }
        return UNKNOWN;
    }

    /**
     * 获取文件类型code
     */
    public int getFileTypeCode() {
        return fileType.getCode();
    }

    /**
     * 获取文件类型名称
     */
    public String getFileTypeName() {
        return fileType.getName();
    }

    /**
     * 判断是否为图片
     */
    public boolean isImage() {
        return fileType == FileTypeEnums.IMAGE;
    }

    /**
     * 判断是否为视频
     */
    public boolean isVideo() {
        return fileType == FileTypeEnums.VIDEO;
    }

    /**
     * 判断是否为音频
     */
    public boolean isAudio() {
        return fileType == FileTypeEnums.AUDIO;
    }

    /**
     * 判断是否为文档
     */
    public boolean isDocument() {
        return fileType == FileTypeEnums.PDF ||
                fileType == FileTypeEnums.WORD ||
                fileType == FileTypeEnums.EXCEL ||
                fileType == FileTypeEnums.PPT;
    }

    /**
     * 判断是否为媒体文件（图片、视频、音频）
     */
    public boolean isMedia() {
        return fileType == FileTypeEnums.IMAGE ||
                fileType == FileTypeEnums.VIDEO ||
                fileType == FileTypeEnums.AUDIO;
    }

    /**
     * 判断是否为压缩文件
     */
    public boolean isCompressed() {
        return fileType == FileTypeEnums.COMPRESSED;
    }

    /**
     * 判断是否为代码文件
     */
    public boolean isCode() {
        return fileType == FileTypeEnums.CODE;
    }
}