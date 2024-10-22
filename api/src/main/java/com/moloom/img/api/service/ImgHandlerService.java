package com.moloom.img.api.service;

import com.moloom.img.api.entity.ImgInfo;
import com.moloom.img.api.to.R;
import com.moloom.img.api.vo.FileUploadVo;

/**
 * @author: moloom
 * @date: 2024-10-22 18:26
 * @description:
 */
public interface ImgHandlerService {


    /**
     * @param fileUploadVo
     * @return
     * @author moloom
     * @date 2024-10-22 20:19:06
     * @description 处理图片
     */
    public R imghandler(FileUploadVo fileUploadVo);

    /**
     * @param img
     * @return
     * @author moloom
     * @date 2024-10-22 18:41:09
     * @description 保存图片
     */
    public R saveImg(ImgInfo img);


}
