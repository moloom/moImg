package com.moloom.img.api.service.impl;

import com.moloom.img.api.exception.BadRequestException;
import com.moloom.img.api.exception.ExtensionMismatchException;
import com.moloom.img.api.service.*;
import com.moloom.img.api.to.R;
import com.moloom.img.api.vo.UploadVo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

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

    //redis中 token 的key前缀
    @Resource
    private String tokensPrefix;

    @Override
    public R uploadDispatcher(@NotNull UploadVo vo, MultipartFile[] multipartFiles) {
        /**
         * 查看这请求有没有问题
         * 1.获取文件类型
         * 2.若是压缩包，则解压后再查看文件类型
         * 3.如果是图片则调用图片处理服务，若是视频则调用视频处理服务，若是其他类型则调用文件处理服务。（三种文件存不同的bucket）
         * 4.视频的话，需要判断是否是mp4,若不是默认转为mp4，不想转换的话，需要在上传时加一个参数
         */

        Tika tika = new Tika();
        ArrayList<R> handlerResult = new ArrayList<R>();
        log.info("number of upload file is {}", multipartFiles.length);

        //TODO 获取上传用户的id,在TokensServiceImpl中实现，方法名 hasRegister()，若没注册则注册
//        Tokens tokens = ((Tokens)redisTemplate.opsForValue().get(tokensPrefix + vo.getToken()));

        //开始处理每个上传的文件
        Arrays.stream(multipartFiles).forEach(file -> {
            //把文件信息存入单个vo
            UploadVo uploadVo;
            try {
                uploadVo = (UploadVo) vo.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
            uploadVo.setMultipartFile(file);
            //用 Tika 获取文件类型
            try (InputStream inputStream = file.getInputStream()) {
                String contentType = tika.detect(inputStream);
                log.info("tike获取的类型：{}", contentType);
                if (contentType == null)
                    throw new BadRequestException();
                uploadVo.setContentType(contentType);
            } catch (Exception e) {
                throw new BadRequestException("Some errors have been detected in the uploaded files. Please check if the uploaded files are of a valid image, video, or zip type");
            }

            //匹配文件类型对应的处理方法
            if (uploadVo.getContentType().startsWith("image/"))
                handlerResult.add(imgHandlerService.imghandler(uploadVo));
            else if (uploadVo.getContentType().startsWith("video/"))
                handlerResult.add(videoHandlerService.videoHandler(uploadVo));
            else if (uploadVo.getContentType().equals("application/octet-stream"))
                //未上传文件的处理
                throw new BadRequestException("file not upload");
            else if (uploadVo.getContentType().startsWith("application/")) {
                handlerResult.add(archiveHandler(uploadVo));
            } else throw new ExtensionMismatchException();
        });

        //检查处理结果，如果全成功则返回成功，否则返回失败。且都返回每个文件的处理结果
        boolean b = handlerResult.stream().allMatch(r -> r.getStatus() == HttpStatus.OK.value());
        System.out.println("上传结果:"+b);
        return R.success();
    }

    @Override
    public R archiveHandler(UploadVo vo) {
        return null;
    }

}
