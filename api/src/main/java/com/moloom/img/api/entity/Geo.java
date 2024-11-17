package com.moloom.img.api.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

/**
 * @author: moloom
 * @date: 2024-11-17 22:06
 * @description: 地理位置信息
 */
@Data
@Accessors(chain = true)
@Builder
public class Geo {
    private Long geoId;
    private Float latitude;         // 纬度
    private Float longitude;        // 经度
    private Float altitude;     // 海拔
    private String altitudeRef;     // 海拔单位
    private String gpsSpeed;        // 速度
    private String gpsSpeedRef;     // 速度单位
    private Long imgInfoId;
    private Long createdBy;
    private Timestamp createdTime;
    private Timestamp updatedTime;
}
