package com.moloom.img.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author: moloom
 * @date: 2024-12-09 18:15
 * @description: DescriptionEntity, used to store description information of images
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Builder
public class DescriptionEntity {
    private Long descriptionId;
    private String description;

}
