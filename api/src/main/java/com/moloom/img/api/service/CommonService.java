package com.moloom.img.api.service;

/**
 * @author: moloom
 * @date: 2024-10-12 23:54
 * @description:
 */
public interface CommonService {

    /**
     * @param token
     * @return 存在返回ture，否则返回false
     * @author moloom
     * @date 2024-10-19 22:31:49
     * @description 查看token是否重复
     */
    boolean isTokenExist(String token);
    //token在前端生成，后端查重，若重复则后端生成个新的，返回给前端

    //生成图片特征值

    //查询图片特征值
}
