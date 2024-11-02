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
public class Token {
    private String token;
    private long userId;
    private byte status;
    private Timestamp createdTime;
}
