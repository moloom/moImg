package com.moloom.img.api.service;

import com.moloom.img.api.to.R;
import com.moloom.img.api.vo.FileUploadVo;

/**
 * @author: moloom
 * @date: 2024-10-22 18:25
 * @description:
 */
public interface VideoHandlerService {

    /**
     * @param fileUploadVo
     * @return
     * @author moloom
     * @date 2024-10-22 20:53:46
     * @description 视频处理器
     */
    public R videoHandler(FileUploadVo fileUploadVo);
}
