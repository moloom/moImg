package com.moloom.img.api.dao;

import com.moloom.img.api.entity.Geo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author: moloom
 * @date: 2024-11-17 22:50
 * @description: 地理位置Dao
 */
@Mapper
public interface GeoDao {

    /**
     * @param geo
     * @return
     * @author moloom
     * @date 2024-11-17 22:51:27
     * @description insert a new record into Geo
     */
    public int insert(Geo geo);

    /**
     * @param geo
     * @return
     * @author moloom
     * @date 2024-11-17 22:52:22
     * @description update a record in Geo
     */
    public int update(Geo geo);

    /**
     * @param id
     * @return
     * @author moloom
     * @date 2024-11-17 22:52:56
     * @description delete a record in Geo
     */
    @Delete("DELETE FROM geo WHERE geo_id = #{id}")
    public int deleteById(Long id);

    /**
     * @param id
     * @return
     * @author moloom
     * @date 2024-11-17 22:53:46
     * @description select a record in Geo
     */
    @Select("SELECT * FROM geo WHERE geo_id = #{id}")
    public Geo selectOneById(Long id);

}
