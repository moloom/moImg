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
    private Long imgId;
    private String imgUrl;  //资源访问字符串
    private String originalFullName;
    private String storageFullName;
    private String storagePath;
    private Long size;
    private String extension;
    private String contentType;
    private String token;
    private Long width;
    private Long length;
    private ImgCategory imgCategory;
    private String imgCameraInfoId;
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
