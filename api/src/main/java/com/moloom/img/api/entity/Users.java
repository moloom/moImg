package com.moloom.img.api.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

/**
 * @author: moloom
 * @date: 2024-10-12 23:32
 * @description:
 */
@Data
@Builder
@Accessors(chain = true)
public class Users {
    private Long userId;
    private String username;
    private String password;
    private String email;
    private Boolean emailActive;
    private Byte status;
    private Timestamp latestLoggedTime;
    private Timestamp createdTime;
    private Timestamp updatedTime;
}
