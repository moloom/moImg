package com.moloom.img.api.entity;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author: moloom
 * @date: 2024-10-12 23:38
 * @description:
 */
@Data
public class FileTag {
    private Long id;
    private Long fileId;
    private Long tagId;
    private Long createdBy;
    private Timestamp createdTime;
}
