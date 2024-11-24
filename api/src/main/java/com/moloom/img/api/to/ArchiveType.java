package com.moloom.img.api.to;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author: moloom
 * @date: 2024-10-15 08:23
 * @description: 压缩包类型枚举
 */
public enum ArchiveType {
    ZIP("application/zip", Arrays.asList("zip"), "Compressed Archive File"),
    RAR("application/x-rar-compressed", Arrays.asList("rar"), "RAR archive"),
    SEVEN_Z("application/x-7z-compressed", Arrays.asList("7z"), "7-zip archive"),
    GZIP("application/gzip", Arrays.asList("gz", "tgz", "tar.gz"), "Gzip Compressed Archive"),
    TAR("application/x-tar", Arrays.asList("tar"), "GUN tar File Archive without compression (GUN Tape Archive)"),
    TAR_GZ("application/x-gtar", Arrays.asList("gtar"), "GNU tar Compressed File Archive (GNU Tape Archive)"),
    XZ("application/x-xz", Arrays.asList("xz", "tar.xz"), "A lossless data compression file format based on the LZMA algorithm"),
    LZIP("application/x-lzip", Arrays.asList("lz"), "Lzip (LZMA) compressed archive"),
    LZMA("application/x-lzma", Arrays.asList("lzma"), "LZMA compressed archive"),
    BZIP("application/x-bzip", Arrays.asList("bz", "tbz"), "Bzip UNIX Compressed File"),
    BZIP2("application/x-bzip2", Arrays.asList("bz2", "tbz2", "boz", "tar.bz2"), "Bzip 2 UNIX Compressed File");

    private final String mimeType;
    private final List<String> extensions;
    private final String comment;


    ArchiveType(String mimeType, List<String> extensions, String comment) {
        this.mimeType = mimeType;
        this.extensions = extensions;
        this.comment = comment;
    }

    public String getMimeType() {
        return mimeType;
    }

    public List<String> getExtensions() {
        return extensions;
    }

    public String getComment() {
        return comment;
    }

    /**
     * @param mimeType 要进行匹配的 MIME 类型
     * @return
     * @author moloom
     * @date 2024-10-15 10:12:08
     * @description 通过 MIME 类型查找匹配的 ArchiveType
     */
    public static Optional<ArchiveType> matchFromMimeType(String mimeType) {
        return Arrays.stream(values())
                .filter(archiveType -> archiveType.mimeType.equals(mimeType))
                .findFirst();
    }

    /**
     * @param extension 要进行匹配的文件扩展名
     * @return
     * @author moloom
     * @date 2024-10-15 09:17:52
     * @description 通过文件扩展名查找匹配的 ArchiveType
     */
    public static Optional<ArchiveType> matchFromExtension(String extension) {
        return Arrays.stream(values())
                .filter(archiveType -> archiveType.extensions.contains(extension.toLowerCase()))
                .findFirst();
    }
}

