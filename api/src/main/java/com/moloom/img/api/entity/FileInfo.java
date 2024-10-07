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
    @NonNull
    private long id;
    private String fileName;
    private String filePath;
    private long fileSize;
    private String fileType;
    private long imgWidth;
    private long imgHeight;
    private long access_count;
    private long access_user;
    private long created_by;
    private Timestamp created_time;
    private Timestamp updated_time;
    private Timestamp latest_view_time;
}
