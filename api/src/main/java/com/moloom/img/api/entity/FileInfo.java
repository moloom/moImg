package com.moloom.img.api.entity;

import lombok.Data;
import lombok.NonNull;

import java.sql.Timestamp;

/**
 * @author: moloom
 * @date: 2024-10-08 02:02
 * @description: file info pojo
 */
@Data
public class FileInfo {
    private Long id;
    private String fileName;
    private String fileUrl;
    private Long fileSize;
    private String fileType;
    private String token;
    private Long imgWidth;
    private Long imgHeight;
    private Long viewCount;
    private Long viewUser;
    private Long createdBy;
    private Timestamp createdTime;
    private Timestamp updatedTime;
    private Timestamp latestViewTime;
}
