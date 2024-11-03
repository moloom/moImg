package com.moloom.img.api.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

/**
 * @author: moloom
 * @date: 2024-10-08 02:02
 * @description: img info pojo
 */
@Data
@Builder
@Accessors(chain = true)
public class ImgInfo {
    private long imgId;
    private String imgUrl;  //资源访问字符串
    private String originalFullName;
    private String storageFullName;
    private String storagePath;
    private long size;
    private String extension;
    private String contentType;
    private String token;
    private long width;
    private long height;
    private ImgCategory imgCategory;
    private String cameraInfo;
    private float latitude;
    private float longitude;
    private float altitude;
    private Timestamp originalCreatedTime;
    private Timestamp originalUpdatedTime;
    private long viewCount;
    private long viewUser;
    private long createdBy;
    private Timestamp createdTime;
    private Timestamp updatedTime;
    private Timestamp latestViewTime;

}
