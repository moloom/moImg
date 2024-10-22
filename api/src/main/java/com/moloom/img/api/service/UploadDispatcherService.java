package com.moloom.img.api.service;

import com.moloom.img.api.to.R;
import com.moloom.img.api.vo.FileUploadVo;

/**
 * @author: moloom
 * @date: 2024-10-14 19:43
 * @description:
 */
public interface UploadDispatcherService {

    /**
     * @param
     * @return
     * @author moloom
     * @date 2024-10-20 04:21:21
     * @description 处理上传请求，把img、video和压缩包三种类型的请求转到对应的处理方法
     */
    public R uploadDispatcher(FileUploadVo fileUploadVo);

    //处理压缩包，解压缩

    //处理图片

    //处理视频，视频格式要转换成 hevc

    //处理普通文件
}
