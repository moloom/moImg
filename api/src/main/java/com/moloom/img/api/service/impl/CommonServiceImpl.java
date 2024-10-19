package com.moloom.img.api.service.impl;

import com.moloom.img.api.dao.CommonDao;
import com.moloom.img.api.service.CommonService;
import jakarta.annotation.Resource;

/**
 * @author: moloom
 * @date: 2024-10-19 22:40
 * @description:
 */
public class CommonServiceImpl implements CommonService {

    @Resource
    private CommonDao dao;

    @Override
    public boolean isTokenExist(String token) {
        return false;
    }
}
