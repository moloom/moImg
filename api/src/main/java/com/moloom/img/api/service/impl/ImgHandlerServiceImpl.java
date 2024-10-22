package com.moloom.img.api.service.impl;

import com.moloom.img.api.entity.ImgInfo;
import com.moloom.img.api.service.ImgHandlerService;
import com.moloom.img.api.to.R;
import com.moloom.img.api.vo.FileUploadVo;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author: moloom
 * @date: 2024-10-22 18:28
 * @description:
 */
@Service("imgHandlerService")
@Slf4j
public class ImgHandlerServiceImpl implements ImgHandlerService {


    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private MinioClient minioClient;

    @Value("${moimg.upload.upload-root-path}")
    private String prePath;

    @Override
    public R imghandler(FileUploadVo fileUploadVo) {
        /*
         * 1.
         *
         */

        return null;
    }

    @Override
    public R saveImg(ImgInfo img) {
        /*ObjectWriteResponse flag = minioClient.putObject(PutObjectArgs.builder()
                .bucket("img")
                .stream(multipartFile.getInputStream(), multipartFile.getSize(), -1).object("/1/2/3/4.jpg")
                .build());
        System.out.println(flag.etag() + "\n" + flag.bucket() + "\n" + flag.versionId());*/

//        System.out.println("multipartFile 是否为空：" + multipartFile == null);
//        multipartFile.transferTo(new File(path + "/f2.jpeg"));
        return null;
    }
}
