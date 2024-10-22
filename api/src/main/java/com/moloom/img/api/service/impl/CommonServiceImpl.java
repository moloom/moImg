package com.moloom.img.api.service.impl;

import com.moloom.img.api.dao.CommonDao;
import com.moloom.img.api.service.CommonService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author: moloom
 * @date: 2024-10-19 22:40
 * @description:
 */
@Service
public class CommonServiceImpl implements CommonService {

    @Resource
    private CommonDao commonDao;

    @Override
    public boolean isTokenExist(String token) {
        return commonDao.isTokenExist(token);
    }
}
