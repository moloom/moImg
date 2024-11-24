package com.moloom.img.api.vo;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: moloom
 * @date: 2024-10-22 18:06
 * @description:
 */
@Data
@Builder
@Accessors(chain = true)
public class ImgActionsVo {
    private Integer width;
    private Integer height; //
    private Float scale;    //等比缩放比例
}
