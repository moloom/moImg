package com.moloom.img.api.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author: moloom
 * @date: 2024-10-12 23:56
 * @description: 公共的 Dao
 */
@Mapper
@Repository
public interface CommonDao {

    @Select("select token from token where tokens = ${token}")
    public boolean isTokenExist(String token);
}
