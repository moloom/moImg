package com.moloom.img.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

/**
 * @author: moloom
 * @date: 2024-10-08 02:02
 * @description: img info pojo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private Integer width;
    private Integer length;
    private ImgCategory imgCategory;
    private Long imgCameraInfoId;
    private Long geoId;
    private Timestamp originalCreatedTime;      //图片文件的创建时间；    两属性由前端页面获取，若用户是用api上传，则这两个属性为 null ！
    private Timestamp originalUpdatedTime;      //图片文件的修改时间
    private Long viewCount;
    private Long viewUser;
    private Long createdBy;
    private Timestamp createdTime;
    private Timestamp updatedTime;
    private Timestamp latestViewTime;

}
