package com.moloom.img.api.service;

import com.moloom.img.api.to.R;
import com.moloom.img.api.vo.FileUploadVo;

/**
 * @author: moloom
 * @date: 2024-10-22 18:23
 * @description: 处理压缩包
 */
public interface ArchiveHandlerService {

    /**
     * @param fileUploadVo
     * @return
     * @author moloom
     * @date 2024-10-22 21:00:31
     * @description 压缩包处理器
     */
    public R archiveHandler(FileUploadVo fileUploadVo);
}
