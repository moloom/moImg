package com.moloom.img.api.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

/**
 * @author: moloom
 * @date: 2024-10-19 23:41
 * @description:
 */
@Data
@Builder
@Accessors(chain = true)
public class Tokens {
    private Long tokenId;
    private String token;
    private Long userId;
    private Byte status;        //0 无效；1 临时有效，未绑定users,且未存入图片；2 临时有效，未绑定users,但已存入图片；3 已绑定users,user的临时token；4 已绑定users,user的永久token
    private Timestamp createdTime;
    private Timestamp updatedTime;
}
