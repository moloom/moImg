package com.moloom.img.api.dao;

import com.moloom.img.api.entity.GeoEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: moloom
 * @date: 2024-11-17 22:50
 * @description: 地理位置Dao
 */
@Mapper
public interface GeoDao {

    /**
     * @param geoEntity
     * @return
     * @author moloom
     * @date 2024-11-17 22:51:27
     * @description insert a new record into GeoEntity
     */
    public int insert(GeoEntity geoEntity);

    /**
     * @param geoEntity
     * @return
     * @author moloom
     * @date 2024-11-17 22:52:22
     * @description update a record in GeoEntity
     */
    public int update(GeoEntity geoEntity);

    /**
     * @param id
     * @return
     * @author moloom
     * @date 2024-11-17 22:52:56
     * @description delete a record in GeoEntity
     */
    @Delete("DELETE FROM geo WHERE geo_id = #{id}")
    public int deleteById(Long id);

    /**
     * @param id
     * @return
     * @author moloom
     * @date 2024-11-17 22:53:46
     * @description select a record in GeoEntity
     */
    public GeoEntity selectOneById(Long id);

}
