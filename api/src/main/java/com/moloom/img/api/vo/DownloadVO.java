package com.moloom.img.api.vo;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: moloom
 * @date: 2024-11-04 00:30
 * @description: download TO
 */
@Data
@Accessors(chain = true)
@Builder
public class DownloadVO {
    private String url;
    private String extension;
    private String bucketName;
    private String storagePath;
}
