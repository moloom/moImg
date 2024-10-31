package com.moloom.img.api.dao;

import com.moloom.img.api.entity.ImgInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
    public int insertImgInfo(ImgInfo imgInfo);

    /**
     * @param imgInfo 值不为空的才会修改进数据库
     * @return affect row count;0 error or >0 success
     * @author moloom
     * @date 2024-11-01 00:54:42
     * @description update data
     */
    public int updateImgInfo(ImgInfo imgInfo);


    /**
     * @param imgId
     * @return 0 error or >0 success
     * @author moloom
     * @date 2024-11-01 01:12:30
     * @description delete a record by img_id
     */
    @Delete("DELETE FROM img_info WHERE img_id = #{imgId}")
    public int deleteById(Long imgId);

    /**
     * @param imgId
     * @return
     * @author moloom
     * @date 2024-11-01 01:20:59
     * @description
     */
    @Select("SELECT * FROM img_info WHERE img_id = #{imgId}")
    public ImgInfo selectById(Long imgId);
}
