package com.moloom.img.api.service;

import com.moloom.img.api.entity.ImgEntity;
import com.moloom.img.api.vo.DownloadVO;
import com.moloom.img.api.to.R;
import com.moloom.img.api.vo.UploadVo;
import org.springframework.http.ResponseEntity;

/**
 * @author: moloom
 * @date: 2024-10-22 18:26
 * @description:
 */
public interface ImgHandlerService {


    /**
     * @param vo info about upload file
     * @return
     * @author moloom
     * @date 2024-10-22 20:19:06
     * @description 处理上传图片
     */
    public R imghandler(UploadVo vo);

    /**
     * @param img
     * @return
     * @author moloom
     * @date 2024-10-22 18:41:09
     * @description 保存图片
     */
    public R saveImg(ImgEntity img);

    /**
     * @param vo vo
     * @return
     * @author moloom
     * @date 2024-11-04 00:34:04
     * @description 从服务器返回文件
     */
    public ResponseEntity<Object> download(DownloadVO vo);


}
