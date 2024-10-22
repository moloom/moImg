package com.moloom.img.api.service.impl;

import com.moloom.img.api.exception.ExtensionMismatchException;
import com.moloom.img.api.service.*;
import com.moloom.img.api.to.R;
import com.moloom.img.api.vo.FileUploadVo;
import io.minio.MinioClient;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private CommonService commonService;

    @Autowired
    private ImgHandlerService imgHandlerService;
    @Autowired
    private VideoHandlerService videoHandlerService;
    @Autowired
    private ArchiveHandlerService archiveHandlerService;


    @Override
    public R uploadDispatcher(FileUploadVo fileUploadVo) {
        /**
         * 查看这请求有没有问题
         * 1.获取文件类型
         * 2.若是压缩包，则解压后再查看文件类型
         * 3.如果是图片则调用图片处理服务，若是视频则调用视频处理服务，若是其他类型则调用文件处理服务。（三种文件存不同的bucket）
         * 4.视频的话，需要判断是否是mp4,若不是默认转为mp4，不想转换的话，需要在上传时加一个参数
         */


        //前端已经实现了获取文件类型；如果是前端页面请求：就不需要再获取一次文件类型；api请求才需要获取类型
        if (fileUploadVo.getFileExtension().isBlank())
            try (InputStream inputStream = fileUploadVo.getMultipartFile().getInputStream()) {
                Tika tika = new Tika();
                String extension = tika.detect(inputStream);
                if (extension != null)
                    fileUploadVo.setFileExtension(extension);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        if (fileUploadVo.getFileExtension().startsWith("image/"))
            return imgHandlerService.imghandler(fileUploadVo);
        else if (fileUploadVo.getFileExtension().startsWith("video/"))
            return videoHandlerService.videoHandler(fileUploadVo);
        else if (fileUploadVo.getFileExtension().startsWith("application/")) {
            archiveHandlerService.archiveHandler(fileUploadVo);
        } else throw new ExtensionMismatchException();


        return null;
    }

}
