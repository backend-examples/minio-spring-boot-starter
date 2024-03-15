package com.minio.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public enum MimeTypeEnum {

    AAC(".acc","AAC音频","audio/aac", "media"),

    ABW(".abw","AbiWord文件","application/x-abiword", "file"),

    ARC(".arc","存档文件","application/x-freearc", "file"),

    AVI(".avi","音频视频交错格式","video/x-msvideo", "file"),

    AZW(".azw","亚马逊Kindle电子书格式","application/vnd.amazon.ebook", "file"),

    BIN(".bin","任何类型的二进制数据","application/octet-stream", "file"),

    BMP(".bmp","Windows OS / 2位图图形","image/bmp", "file"),

    BZ(".bz","BZip存档","application/x-bzip", "file"),

    BZ2(".bz2","BZip2存档","application/x-bzip2", "file"),

    CSH(".csh","C-Shell脚本","application/x-csh", "file"),

    CSS(".css","级联样式表（CSS）","text/css", "file"),

    CSV(".csv","逗号分隔值（CSV）","text/csv", "file"),

    DOC(".doc","微软Word文件","application/msword", "file"),

    DOCX(".docx","Microsoft Word（OpenXML）","application/vnd.openxmlformats-officedocument.wordprocessingml.document", "file"),

    EOT(".eot","MS Embedded OpenType字体","application/vnd.ms-fontobject", "file"),

    EPUB(".epub","电子出版物（EPUB）","application/epub+zip", "file"),

    GZ(".gz","GZip压缩档案","application/gzip", "file"),

    GIF(".gif","图形交换格式（GIF）","image/gif", "file"),

    HTM(".htm","超文本标记语言（HTML）","text/html", "file"),

    HTML(".html","超文本标记语言（HTML）","text/html", "file"),

    ICO(".ico","图标格式","image/vnd.microsoft.icon", "image"),

    ICS(".ics","iCalendar格式","text/calendar", "file"),

    JAR(".jar","Java存档","application/java-archive", "file"),

    JPEG(".jpeg","JPEG图像","image/jpeg", "image"),

    JPG(".jpg","JPEG图像","image/jpeg", "image"),

    JS(".js","JavaScript","text/javascript", "file"),

    JSON(".json","JSON格式","application/json", "file"),

    JSONLD(".jsonld","JSON-LD格式","application/ld+json", "file"),

    MID(".mid","乐器数字接口（MIDI）","audio/midi", "media"),

    MIDI(".midi","乐器数字接口（MIDI）","audio/midi", "media"),

    MJS(".mjs","JavaScript模块","text/javascript", "file"),

    MP3(".mp3","MP3音频","audio/mpeg", "media"),

    MPEG(".mpeg","MPEG视频","video/mpeg", "media"),

    MPKG(".mpkg","苹果安装程序包","application/vnd.apple.installer+xml", "file"),

    ODP(".odp","OpenDocument演示文稿文档","application/vnd.oasis.opendocument.presentation", "file"),

    ODS(".ods","OpenDocument电子表格文档","application/vnd.oasis.opendocument.spreadsheet", "file"),

    ODT(".odt","OpenDocument文字文件","application/vnd.oasis.opendocument.text", "file"),

    OGA(".oga","OGG音讯","audio/ogg", "media"),

    OGV(".ogv","OGG视频","video/ogg", "media"),

    OGX(".ogx","OGG","application/ogg", "file"),

    OPUS(".opus","OPUS音频","audio/opus", "media"),

    OTF(".otf","otf字体","font/otf", "file"),

    PNG(".png","便携式网络图形","image/png", "image"),

    PDF(".pdf","Adobe 可移植文档格式（PDF）","application/pdf", "file"),

    PHP(".php","php","application/x-httpd-php", "file"),

    PPT(".ppt","Microsoft PowerPoint","application/vnd.ms-powerpoint", "image"),

    PPTX(".pptx","Microsoft PowerPoint（OpenXML）","application/vnd.openxmlformats-officedocument.presentationml.presentation", "file"),

    RAR(".rar","RAR档案","application/vnd.rar", "file"),

    RTF(".rtf","富文本格式","application/rtf", "file"),

    SH(".sh","Bourne Shell脚本","application/x-sh", "file"),

    SVG(".svg","可缩放矢量图形（SVG）","image/svg+xml", "image"),

    SWF(".swf","小型Web格式（SWF）或Adobe Flash文档","application/x-shockwave-flash", "file"),

    TAR(".tar","磁带存档（TAR）","application/x-tar", "file"),

    TIF(".tif","标记图像文件格式（TIFF）","image/tiff", "image"),

    TIFF(".tiff","标记图像文件格式（TIFF）","image/tiff", "image"),

    TS(".ts","MPEG传输流","video/mp2t", "media"),

    TTF(".ttf","ttf字体","font/ttf", "file"),

    TXT(".txt","文本（通常为ASCII或ISO 8859- n","text/plain", "file"),

    VSD(".vsd","微软Visio","application/vnd.visio", "file"),

    WAV(".wav","波形音频格式","audio/wav", "media"),

    WEBA(".weba","WEBM音频","audio/webm", "media"),

    WEBM(".webm","WEBM视频","video/webm", "media"),

    WEBP(".webp","WEBP图像","image/webp", "image"),

    WOFF(".woff","Web开放字体格式（WOFF）","font/woff", "file"),

    WOFF2(".woff2","Web开放字体格式（WOFF）","font/woff2", "file"),

    XHTML(".xhtml","XHTML","application/xhtml+xml", "file"),

    XLS(".xls","微软Excel","application/vnd.ms-excel", "file"),

    XLSX(".xlsx","微软Excel（OpenXML）","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "file"),

    XML(".xml","XML","application/xml", "file"),

    XUL(".xul","XUL","application/vnd.mozilla.xul+xml", "file"),

    ZIP(".zip","ZIP","application/zip", "file"),

    MIME_3GP(".3gp", "3GPP audio/video container", "video/3gpp", "media"),

    MIME_3GP_WITHOUT_VIDEO(".3gp", "3GPP audio/video container doesn't contain video", "audio/3gpp2", "media"),

    MIME_3G2(".3g2", "3GPP2 audio/video container", "video/3gpp2", "media"),

    MIME_3G2_WITHOUT_VIDEO(".3g2", "3GPP2 audio/video container  doesn't contain video", "audio/3gpp2", "media"),

    MIME_7Z(".7z","7-zip存档","application/x-7z-compressed", "file")
    ;

    /**
     * @Feild: 文件扩展名
     */
    private String extension;

    /**
     * @Field: 文件类型解释
     */
    private String explain;

    /**
     * @Feild: 文件的content-type
     */
    private String mimeType;

    /**
     * @Feild: 文件的分类类型
     */
    private String fileType;

    MimeTypeEnum(String extension, String explain, String mimeType, String fileType) {
        this.extension = extension;
        this.explain = explain;
        this.mimeType = mimeType;
        this.fileType = fileType;
    }

    public String getExtension() {
        return extension;
    }

    public String getExplain() {
        return explain;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getFileType() {
        return fileType;
    }

    /**
     * 判断所传数值是否包含于枚举当中
     *
     * @param extension
     * @return
     */
    public static boolean isOutRangeMimeType(String extension) {
        List<String> mimeTypeEnumList = toList().stream().map(MimeTypeEnum::getExtension).collect(Collectors.toList());

        if (mimeTypeEnumList.contains(extension)) {
            return true;
        }

        return false;
    }

    /**
     * 获得所有枚举类型到list
     * @return
     */
    public static List<MimeTypeEnum> toList() {
        List<MimeTypeEnum> list = new ArrayList<>();
        MimeTypeEnum[] values = values();
        Collections.addAll(list, values);

        return list;
    }

    public static MimeTypeEnum findByExtension(String extension) {
        if(StringUtils.isBlank(extension)){
            return null;
        }

        for (MimeTypeEnum typesEnum : MimeTypeEnum.values()) {
            if (extension.equals(typesEnum.getExtension())) {
                return typesEnum;
            }
        }

        return null;
    }

    /**
     * 描述：Content-Type常用对照
     * @param extension
     * @return
     * @since
     */
    public static String getContentType(String extension) {
        MimeTypeEnum mimeTypeEnum = MimeTypeEnum.findByExtension(extension);
        if(mimeTypeEnum != null){
            return mimeTypeEnum.getMimeType();
        }

        return "application/octet-stream";
    }

    /**
     * 描述：获取文件应该存储到文件服务器的目录
     * @param extension
     * @return
     */
    public static String getClassifyType(String extension) {
        MimeTypeEnum mimeTypeEnum = MimeTypeEnum.findByExtension(extension);
        if(mimeTypeEnum != null){
            return mimeTypeEnum.getFileType();
        }

        return "file";
    }
}