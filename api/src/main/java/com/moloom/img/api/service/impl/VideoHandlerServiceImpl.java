package com.moloom.img.api.service.impl;

import com.moloom.img.api.service.VideoHandlerService;
import com.moloom.img.api.to.R;
import com.moloom.img.api.vo.FileUploadVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author: moloom
 * @date: 2024-10-22 20:52
 * @description:
 */
@Service("videoHandlerService")
@Slf4j
public class VideoHandlerServiceImpl implements VideoHandlerService {
    @Override
    public R videoHandler(FileUploadVo fileUploadVo) {
//        视频格式要转换成 hevc
        return null;
    }
}
