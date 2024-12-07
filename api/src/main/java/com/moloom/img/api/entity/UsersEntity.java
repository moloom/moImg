package com.moloom.img.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

/**
 * @author: moloom
 * @date: 2024-10-12 23:32
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class UsersEntity {
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
