package com.moloom.img.api.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author moloom
 * @date 2024-9-28 21:50:45
 * @description 邮箱实体类，不入数据库
 */
@Data
@Builder
@Accessors(chain = true)
public class Mail {
    private String addressee;
    private String text;
    private String subject;

}
