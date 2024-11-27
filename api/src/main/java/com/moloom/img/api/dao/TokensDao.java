package com.moloom.img.api.dao;

import com.moloom.img.api.entity.Tokens;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: moloom
 * @date: 2024-11-18 20:51
 * @description: token dao
 */
@Mapper
public interface TokensDao {
    /**
     * @param token
     * @return
     * @author moloom
     * @date 2024-11-18 20:52:57
     * @description insert a record into tokens table
     */
    long insert(Tokens token);

    /**
     * @param token token object
     * @return
     * @author moloom
     * @date 2024-11-18 21:40:20
     * @description update a record in tokens table
     */
    long update(Tokens token);

    /**
     * @param token
     * @return
     * @author moloom
     * @date 2024-11-18 21:43:25
     * @description delete a record in tokens table by token
     */
    @Delete("delete from tokens where token = #{token}")
    long deleteByToken(String token);

    @Select("select * from tokens where token = #{token}")
    Tokens selectOneByToken(String token);

    @Select("select * from tokens")
    List<Tokens> selectAll();
}
