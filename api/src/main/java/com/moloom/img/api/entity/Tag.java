package com.moloom.img.api.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: moloom
 * @date: 2024-10-12 23:37
 * @description:
 */
@Data
@Builder
@Accessors(chain = true)
public class Tag {
    private Long tagId;
    private String tagName;
}
