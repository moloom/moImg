package com.moloom.img.api.dao;

import com.moloom.img.api.entity.DescriptionEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: moloom
 * @date: 2024-12-09 18:33
 * @description: Description dao
 */
@Mapper
public interface DescriptionDao {

    /**
     * @author moloom
     * @date 2024-12-09 18:34:28
     * @param descriptionEntity
     * @return the number of rows affected
     * @description insert description
     */
    int insert(DescriptionEntity descriptionEntity);

    /**
     * @author moloom
     * @date 2024-12-09 18:34:28
     * @param descriptionEntity
     * @return the number of rows affected
     * @description update description
     */
    int update(DescriptionEntity descriptionEntity);

    /**
     * @author moloom
     * @date 2024-12-09 18:34:28
     * @param id
     * @return the number of rows affected
     * @description delete description by id
     */
    @Delete("DELETE FROM description WHERE description_id = #{id}")
    int deleteById(Long id);
}
