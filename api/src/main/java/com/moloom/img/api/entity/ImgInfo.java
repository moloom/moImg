package com.moloom.img.api.entity;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author: moloom
 * @date: 2024-10-08 02:02
 * @description: img info pojo
 */
@Data
public class ImgInfo {
    private Long imgId;
    private String imgUrl;  //资源访问字符串
    private String originalFullName;
    private String storageFullName;
    private String storagePath;
    private Long size;
    private String extension;
    private String token;
    private Long width;
    private Long height;
    private ImgCategory imgCategory;
    private String cameraInfo;
    private Float latitude;
    private Float longitude;
    private Float altitude;
    private Timestamp originalCreatedTime;
    private Timestamp originalUpdatedTime;
    private Long viewCount;
    private Long viewUser;
    private Long createdBy;
    private Timestamp createdTime;
    private Timestamp updatedTime;
    private Timestamp latestViewTime;
}
