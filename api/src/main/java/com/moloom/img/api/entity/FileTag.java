package com.moloom.img.api.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

/**
 * @author: moloom
 * @date: 2024-10-12 23:38
 * @description:
 */
@Data
@Builder
@Accessors(chain = true)
public class FileTag {
    private Long id;
    private Long fileId;
    private Long tagId;
    private Long createdBy;
    private Timestamp createdTime;
}
