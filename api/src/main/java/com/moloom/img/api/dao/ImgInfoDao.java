package com.moloom.img.api.dao;

import com.moloom.img.api.entity.ImgInfo;
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
public interface ImgInfoDao {

    /**
     * @param imgInfo
     * @return 0 represent execute error; > 0 represent is id of imgInfo
     * @author moloom
     * @date 2024-11-01 00:41:12
     * @description insert a row data
     */
    int insert(ImgInfo imgInfo);

    /**
     * @param imgInfo 值不为空的才会修改进数据库
     * @return affect row count;0 error or >0 success
     * @author moloom
     * @date 2024-11-01 00:54:42
     * @description update data,only update not null and not empty fields!
     */
    int update(ImgInfo imgInfo);


    /**
     * @param imgId
     * @return 0 error or >0 success
     * @author moloom
     * @date 2024-11-01 01:12:30
     * @description delete a record by img_id
     */
    @Delete("DELETE FROM img_info WHERE img_id = #{imgId}")
    int deleteById(Long imgId);

    /**
     * @param
     * @return list of imgInfo
     * @author moloom
     * @date 2024-11-23 22:47:40
     * @description select all imgInfo
     */
    @Select("SELECT * FROM img_info")
    List<ImgInfo> getAllImgInfos();

    /**
     * @param imgId the id of img
     * @return ImgInfo
     * @author moloom
     * @date 2024-11-01 01:20:59
     * @description select one record by img_id
     */
    @Select("SELECT * FROM img_info WHERE img_id = #{imgId}")
    ImgInfo selectOneByImgId(Long imgId);

    /**
     * @param imgUrl the url of img
     * @return ImgInfo
     * @author moloom
     * @date 2024-11-23 22:52:16
     * @description select one record by img_url
     */
    @Select("SELECT * FROM img_info WHERE img_url = #{imgUrl}")
    ImgInfo selectOneByImgUrl(String imgUrl);


}
