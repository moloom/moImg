package com.moloom.img.api.service;

import com.moloom.img.api.entity.ImgInfo;
import com.moloom.img.api.to.DownloadTO;
import com.moloom.img.api.to.R;
import com.moloom.img.api.vo.FileUploadVo;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

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
     * @description 处理上传图片
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

    /**
     * @author moloom
     * @date 2024-11-04 00:34:04
     * @param downloadTO
     * @return
     * @description 从服务器返回文件
     */
    public ResponseEntity<InputStreamResource> download(DownloadTO downloadTO);


}
