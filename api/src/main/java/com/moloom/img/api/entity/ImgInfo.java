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
    private String fileName;
    private String fileUrl;
    private Long fileSize;
    private String fileExtension;
    private Long imgWidth;
    private Long imgHeight;
    private String camera_info;
    private Float latitude;
    private Float longitude;
    private Float altitude;
    private Long viewCount;
    private Long viewUser;
    private Long createdBy;
    private Timestamp createdTime;
    private Timestamp updatedTime;
    private Timestamp latestViewTime;
}
