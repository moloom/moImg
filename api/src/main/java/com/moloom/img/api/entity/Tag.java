package com.moloom.img.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author: moloom
 * @date: 2024-10-12 23:37
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class Tag {
    private Long tagId;
    private String tagName;
}
