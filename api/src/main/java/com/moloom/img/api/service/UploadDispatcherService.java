package com.moloom.img.api.service;

import com.moloom.img.api.to.R;
import com.moloom.img.api.vo.UploadVo;

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
    public R uploadDispatcher(UploadVo vo);


    /**
     * @param vo
     * @return
     * @author moloom
     * @date 2024-10-22 21:00:31
     * @description 压缩包处理器
     */
    public R archiveHandler(UploadVo vo);

}
