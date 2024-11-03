package com.moloom.img.api.to;

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
public class DownloadTO {
    private String url;
    private String extension;
    private String bucketName;
    private String storagePath;
}
