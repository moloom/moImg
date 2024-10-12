package com.moloom.img.api.entity;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author: moloom
 * @date: 2024-10-12 23:32
 * @description:
 */
@Data
public class Users {
    private Long id;
    private String name;
    private String password;
    private String email;
    private String token;
    private Byte alive;
    private Timestamp createdTime;
    private Timestamp updatedTime;
    private Timestamp latestLoggedTime;
}
