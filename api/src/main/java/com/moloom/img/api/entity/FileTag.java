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
    private long id;
    private long fileId;
    private long tagId;
    private long createdBy;
    private Timestamp createdTime;
}
