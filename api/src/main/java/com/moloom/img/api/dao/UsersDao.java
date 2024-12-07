package com.moloom.img.api.dao;

import com.moloom.img.api.entity.UsersEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author: moloom
 * @date: 2024-10-13 00:04
 * @description: users 表持久层
 */
@Mapper
public interface UsersDao {

    /**
     * @param user
     * @return
     * @author moloom
     * @date 2024-10-13 00:09:25
     * @description 登录
     */
    @Select("select * from where name=${name} and password=${password}")
    public UsersEntity searchUserByNameAndPassword(UsersEntity user);

    /**
     * @param token
     * @return
     * @author moloom
     * @date 2024-10-13 00:14:52
     * @description 通过token查询用户
     */
    /*@Select("select * from where token=${token}")
    public UsersEntity searchUserByToken(String token);*/

    /**
     * @param user
     * @return id:id > 0 则插入成功,id < 0 则插入失败
     * @author moloom
     * @date 2024-10-13 00:16:27
     * @description 插入一条Users
     */
    public int insertUser(UsersEntity user);

    /**
     * @param user 条件为id
     * @return
     * @author moloom
     * @date 2024-10-13 00:17:53
     * @description 修改用户
     */
    public int updateUser(UsersEntity user);

    /**
     * @param id
     * @return
     * @author moloom
     * @date 2024-10-13 00:17:57
     * @description 删除一个用户
     */
//    public int deleteUser(Long id);

}
