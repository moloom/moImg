package com.moloom.img.api.service.impl;

import com.moloom.img.api.entity.ImgInfo;
import com.moloom.img.api.service.ImgHandlerService;
import com.moloom.img.api.to.R;
import com.moloom.img.api.vo.FileUploadVo;
import io.minio.*;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaMimeKeys;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.helpers.DefaultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

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
    public R imghandler(FileUploadVo fileUploadVo) throws Exception {
        /*
         * 1.
         *
         */
        // 提取元信息
        /*try (InputStream inputStream = fileUploadVo.getMultipartFile().getInputStream()) {
            Metadata metadata = new Metadata();
            BodyContentHandler handler = new BodyContentHandler();
            // 使用 Tika 处理输入流
            Tika tika = new Tika();
            tika.parse(inputStream, metadata);

            // 获取元信息
            String cameraModel = metadata.get("Exif.Image.Model");
            String dateTimeOriginal = metadata.get("Exif.Image.DateTimeOriginal");
            // 处理和存储元信息
        } catch (Exception e) {
            // 处理异常
        }*/
        boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket("images").build());
        if (!bucketExists) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket("images").build());
        } else
            System.out.println("bucket qwertyuiop is exists");
        ObjectWriteResponse flag = minioClient.putObject(PutObjectArgs.builder()
                .bucket("images")
                .stream(fileUploadVo.getMultipartFile().getInputStream(), fileUploadVo.getMultipartFile().getSize(), -1).object(fileUploadVo.getMultipartFile().getName())
                .build());
        System.out.println(flag.etag() + "\n" + flag.bucket() + "\n" + flag.versionId());

        return R.success(flag);
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
