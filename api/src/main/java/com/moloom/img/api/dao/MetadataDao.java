package com.moloom.img.api.dao;

import com.moloom.img.api.entity.MetadataEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author: moloom
 * @date: 2024-11-17 19:55
 * @description: metadata Dao
 */
@Mapper
public interface MetadataDao {

    /**
     * @param metadataEntity
     * @return 0 represent execute error; > 0 represent is id of img
     * @author moloom
     * @date 2024-11-17 19:59:32
     * @description insert a record of metadata
     */
    int insert(MetadataEntity metadataEntity);

    /**
     * @param metadataEntity
     * @return
     * @author moloom
     * @date 2024-11-17 20:05:56
     * @description update a record of metadata,only update not null and not empty fields!
     */
    int update(MetadataEntity metadataEntity);

    /**
     * @param id
     * @return
     * @author moloom
     * @date 2024-11-17 20:23:42
     * @description delete a record of metadata
     */
    @Delete("DELETE FROM metadata WHERE metadata_id = #{id}")
    int deleteById(Long id);


    /**
     * @param id
     * @return
     * @author moloom
     * @date 2024-11-17 20:28:00
     * @description select a record of metadata
     */
    @Select("SELECT * FROM metadata WHERE metadata_id = #{id}")
    MetadataEntity selectOneById(Long id);

}
