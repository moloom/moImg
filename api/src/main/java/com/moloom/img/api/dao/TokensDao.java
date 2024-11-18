package com.moloom.img.api.dao;

import com.moloom.img.api.entity.Tokens;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: moloom
 * @date: 2024-11-18 20:51
 * @description: token dao
 */
@Mapper
public interface TokensDao {
    /**
     * @author moloom
     * @date 2024-11-18 20:52:57
     * @param token
     * @return
     * @description insert a record into tokens table
     */
    public int insert(Tokens token);
}
