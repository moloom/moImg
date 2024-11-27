package com.moloom.img.api.service;

import com.moloom.img.api.to.R;

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
    public R register();

    /**
     * @param token
     * @return true if token exist or false
     * @author moloom
     * @date 2024-11-27 16:48:39
     * @description check token exist
     */
    public boolean isExist(String token);
}
