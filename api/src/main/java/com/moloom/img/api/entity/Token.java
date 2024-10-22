package com.moloom.img.api.entity;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author: moloom
 * @date: 2024-10-19 23:41
 * @description:
 */
@Data
public class Token {
    private String token;
    private Long userId;
    private Byte status;
    private Timestamp createdTime;
}
