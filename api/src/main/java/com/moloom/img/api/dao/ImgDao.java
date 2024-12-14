package com.moloom.img.api.dao;

import com.moloom.img.api.entity.ImgEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: moloom
 * @date: 2024-11-01 00:36
 * @description: img database operation
 */
@Mapper
public interface ImgDao {

    /**
     * @param imgEntity
     * @return 0 represent execute error; > 0 represent is id of imgEntity
     * @author moloom
     * @date 2024-11-01 00:41:12
     * @description insert a row data
     */
    int insert(ImgEntity imgEntity);

    /**
     * @param imgEntity 值不为空的才会修改进数据库
     * @return the number of rows affected
     * @author moloom
     * @date 2024-11-01 00:54:42
     * @description update data,only update not null and not empty fields!
     */
    int update(ImgEntity imgEntity);


    /**
     * @param imgId
     * @return the number of rows affected
     * @author moloom
     * @date 2024-11-01 01:12:30
     * @description delete a record by img_id
     */
    @Delete("DELETE FROM img WHERE img_id = #{imgId}")
    int deleteById(Long imgId);

    /**
     * @param imgUrl
     * @return the number of rows affected
     * @author moloom
     * @date 2024-12-12 20:31:52
     * @description delete a record by img_url
     */
    @Delete("DELETE FROM img WHERE img_url = #{imgUrl}")
    int deleteByUrl(String imgUrl);

    /**
     * @param
     * @return list of imgInfo
     * @author moloom
     * @date 2024-11-23 22:47:40
     * @description select all imgInfo
     */
    @Select("SELECT * FROM img")
    List<ImgEntity> getAllImg();

    /**
     * @param imgId the id of img
     * @return ImgEntity
     * @author moloom
     * @date 2024-11-01 01:20:59
     * @description select one record by img_id
     */
    @Select("SELECT * FROM img WHERE img_id = #{imgId}")
    ImgEntity selectOneByImgId(Long imgId);

    /**
     * @param imgUrl the url of img
     * @return ImgEntity
     * @author moloom
     * @date 2024-11-23 22:52:16
     * @description select one record by img_url
     */
    @Select("SELECT * FROM img WHERE img_url = #{imgUrl}")
    ImgEntity selectOneByImgUrl(String imgUrl);


}
