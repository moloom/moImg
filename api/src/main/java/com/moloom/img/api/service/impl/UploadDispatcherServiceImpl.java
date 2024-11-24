package com.moloom.img.api.service.impl;

import com.moloom.img.api.exception.ExtensionMismatchException;
import com.moloom.img.api.service.*;
import com.moloom.img.api.to.R;
import com.moloom.img.api.vo.UploadVo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author: moloom
 * @date: 2024-10-12 23:58
 * @description:
 */
@Service("uploadDispatcherService")
@Slf4j
public class UploadDispatcherServiceImpl implements UploadDispatcherService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    @Resource
    private ImgHandlerService imgHandlerService;
    @Resource
    private VideoHandlerService videoHandlerService;


    @Override
    public R uploadDispatcher(@NotNull UploadVo vo) {
        /**
         * 查看这请求有没有问题
         * 1.获取文件类型
         * 2.若是压缩包，则解压后再查看文件类型
         * 3.如果是图片则调用图片处理服务，若是视频则调用视频处理服务，若是其他类型则调用文件处理服务。（三种文件存不同的bucket）
         * 4.视频的话，需要判断是否是mp4,若不是默认转为mp4，不想转换的话，需要在上传时加一个参数
         */

        Tika tika = new Tika();
        //前端已经实现了获取文件类型；如果是前端页面请求：就不需要再获取一次文件类型；api请求才需要获取类型
        if (vo.getContentType() == null || vo.getContentType().isEmpty())
            try (InputStream inputStream = vo.getMultipartFile().getInputStream()) {
                String contentType = tika.detect(inputStream);
                log.info("tike获取的类型：{}", contentType);
                if (contentType != null)
                    vo.setContentType(contentType);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        //匹配文件类型对应的处理方法
        if (vo.getContentType().startsWith("image/"))
            return imgHandlerService.imghandler(vo);
        else if (vo.getContentType().startsWith("video/"))
            return videoHandlerService.videoHandler(vo);
        else if (vo.getContentType().startsWith("application/")) {
            return archiveHandler(vo);
        } else throw new ExtensionMismatchException();
    }

    @Override
    public R archiveHandler(UploadVo vo) {
        return null;
    }

}
