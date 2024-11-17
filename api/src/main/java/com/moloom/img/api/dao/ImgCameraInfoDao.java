package com.moloom.img.api.dao;

import com.moloom.img.api.entity.ImgCameraInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author: moloom
 * @date: 2024-11-17 19:55
 * @description: 摄像头信息Dao
 */
@Mapper
public interface ImgCameraInfoDao {

    /**
     * @param imgCameraInfo
     * @return 0 represent execute error; > 0 represent is id of imgInfo
     * @author moloom
     * @date 2024-11-17 19:59:32
     * @description insert a record of imgCameraInfo
     */
    public int insert(ImgCameraInfo imgCameraInfo);

    /**
     * @param imgCameraInfo
     * @return
     * @author moloom
     * @date 2024-11-17 20:05:56
     * @description update a record of imgCameraInfo,only update not null and not empty fields!
     */
    public int update(ImgCameraInfo imgCameraInfo);

    /**
     * @author moloom
     * @date 2024-11-17 20:23:42
     * @param id
     * @return
     * @description delete a record of imgCameraInfo
     */
    @Delete("DELETE FROM img_camera_info WHERE img_camera_info_id = #{id}")
    public int deleteById(Long id);


    /**
     * @author moloom
     * @date 2024-11-17 20:28:00
     * @param id
     * @return
     * @description select a record of imgCameraInfo
     */
    @Select("SELECT * FROM img_camera_info WHERE img_camera_info_id = #{id}")
    ImgCameraInfo selectOneById(Long id);

}
