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

    public int update(ImgCameraInfo imgCameraInfo);

    @Delete("DELETE FROM img_camera_info WHERE img_camera_info_id = #{id}")
    public int deleteById(Long id);


    @Select("SELECT * FROM img_camera_info WHERE img_camera_info_id = #{id}")
    ImgCameraInfo selectOneById(Long id);

}
