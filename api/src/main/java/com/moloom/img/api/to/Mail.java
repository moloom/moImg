package com.moloom.img.api.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author moloom
 * @date 2024-9-28 21:50:45
 * @description 邮箱实体类，不入数据库
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class Mail {
    private String addressee;
    private String text;
    private String subject;

}
