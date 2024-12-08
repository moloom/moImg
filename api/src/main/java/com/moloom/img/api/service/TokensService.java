package com.moloom.img.api.service;

import com.moloom.img.api.entity.TokensEntity;
import com.moloom.img.api.to.R;
import org.jetbrains.annotations.NotNull;

/**
 * @author: moloom
 * @date: 2024-11-27 16:21
 * @description: tokens service
 */
public interface TokensService {

    /**
     * @return token
     * @author moloom
     * @date 2024-11-27 16:26:08
     * @description register a token and return it
     */
    public R registerInRedis();

    /**
     * @param token token obj
     * @author moloom
     * @date 2024-12-07 22:56:14
     * @description 1.ensure token stored in db, if not, insert it.</br>
     * 2.update it status to 2.</br>
     * 3.remove current token from redis.
     */
    public void ensureTokenStoredAndCorrectStatus(@NotNull TokensEntity token) throws Exception;

    /**
     * @param token
     * @return true if token exist or false
     * @author moloom
     * @date 2024-11-27 16:48:39
     * @description check token exist in DB
     */
    public boolean isExistInDB(String token);

    /**
     * @param token
     * @return <code>true</code> if the token exists in Redis or is found in the database and cached into Redis;</br>
     * <code>false</code> if the token is not found in either Redis or the database.
     * @author moloom
     * @date 2024-12-08 21:04:01
     * @description check if the token exists in Redis or the database.
     * if it exists in the database but not in Redis, cache it in Redis.
     */
    public boolean checkAndCacheToken(String token);
}
