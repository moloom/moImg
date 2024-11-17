package com.moloom.img.api.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.tika.metadata.Metadata;

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
    private Double latitude;         // 纬度
    private Double longitude;        // 经度
    private Double altitude;     // 海拔
    private String altitudeRef;     // 海拔单位
    private Float gpsSpeed;        // 速度
    private String gpsSpeedRef;     // 速度单位
    private Long imgInfoId;
    private Long createdBy;
    private Timestamp createdTime;
    private Timestamp updatedTime;

    // 从Metadata对象中提取经纬度信息
    public Geo fromMetadata(Metadata metadata) {
        if (metadata.get("GPS:GPS Longitude") != null && !metadata.get("GPS:GPS Longitude").isEmpty())
            this.setLongitude(pointToCoordinate(metadata.get("GPS:GPS Longitude")));
        if (metadata.get("GPS:GPS Latitude") != null && !metadata.get("GPS:GPS Latitude").isEmpty())
            this.setLatitude(pointToCoordinate(metadata.get("GPS:GPS Latitude")));
        //GPS:GPS Altitude的值为 0 m，需要去掉单位
        if (metadata.get("GPS:GPS Altitude") != null && !metadata.get("GPS:GPS Altitude").isEmpty()) {
            int index = metadata.get("GPS:GPS Altitude").indexOf(' ');
            this.setAltitude(Double.valueOf(metadata.get("GPS:GPS Altitude").substring(0, index)));
            this.setAltitudeRef(metadata.get("GPS:GPS Altitude").substring(index));
        }

        //GPS:GPS Speed的值为 0 km/h，需要去掉单位
        if (metadata.get("GPS:GPS Speed") != null && !metadata.get("GPS:GPS Speed").isEmpty())
            this.setGpsSpeed(Float.valueOf(metadata.get("GPS:GPS Speed").substring(0, metadata.get("GPS:GPS Speed").indexOf(' '))));
        this.setGpsSpeedRef(metadata.get("GPS:GPS Speed Ref"));
        return this;
    }

    // 将度分秒坐标转换为十进制坐标
    public static Double pointToCoordinate(String point) {
        if (point == null || point.isEmpty())
            return null;
        System.out.println("\n\n" + point + "\n");
        int degreeIndex = point.indexOf("°");
        int minuteIndex = point.indexOf("'");
        int secondIndex = point.indexOf("\"");
        Double degree = Double.parseDouble(point.substring(0, degreeIndex));
        Double minute = Double.parseDouble(point.substring(degreeIndex + 1, minuteIndex));
        Double second = Double.parseDouble(point.substring(minuteIndex + 1, secondIndex));
        Double coordinator = degree + minute / 60 + second / 60 / 60;
        return coordinator;
    }
}
