package com.moloom.img.api.service.impl;

import com.moloom.img.api.config.BucketConfig;
import com.moloom.img.api.dao.ImgInfoDao;
import com.moloom.img.api.entity.ImgCategory;
import com.moloom.img.api.entity.ImgInfo;
import com.moloom.img.api.service.ImgHandlerService;
import com.moloom.img.api.to.Buckets;
import com.moloom.img.api.to.R;
import com.moloom.img.api.utils.StringGenerator;
import com.moloom.img.api.vo.FileUploadVo;
import io.minio.*;
import io.minio.errors.*;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaMimeKeys;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.helpers.DefaultHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author: moloom
 * @date: 2024-10-22 18:28
 * @description:
 */
@Service("imgHandlerService")
@Slf4j
public class ImgHandlerServiceImpl implements ImgHandlerService {


    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private MinioServiceImpl minioService;

    @Resource
    private ImgInfoDao imgInfoDao;

    @Value("${moimg.upload.upload-root-path}")
    private String prePath;

    @Resource
    private BucketConfig bucketConfig;

    //存储图片的bucket名称
    private Buckets imgBucket;


    @PostConstruct
    public void initImgBucket() {
        if (bucketConfig == null || bucketConfig.getBuckets() == null || bucketConfig.getBuckets().size() == 0) {
            log.error("class BucketConfig::Arg bucketConfig injects error");
            throw new IllegalStateException("bucketConfig is null or empty");
        }
        //获取存储图片的bucket 名称，规定了，第一个是存图片的
        imgBucket = bucketConfig.getBuckets().get(0);
    }

    @Override
    public R imghandler(FileUploadVo fileUploadVo) {
        /*
         * 1.
         *
         */
        // 提取元信息，和存储双线程处理
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
        //确保bucket存在
        boolean bucketExists = minioService.checkBucketExist(imgBucket.getBucketName());
        if (!bucketExists)
            minioService.makeBucket(imgBucket);
        //处理：img存储在minIO的文件名及路径，存储于数据库的虚拟文件名，
        //拼接存储路径，路径格式 token/originalName_imgCategory.extension

        log.info("fileUploadVo.getMultipartFile().getContentType()::{}", fileUploadVo.getMultipartFile().getContentType());

        fileUploadVo.setFileName(fileUploadVo.getMultipartFile().getOriginalFilename());
        fileUploadVo.setFileExtension("jpg");
        fileUploadVo.setBucketName(imgBucket.getBucketName());
        StringBuilder imgStoragePath = new StringBuilder();
        imgStoragePath.append(fileUploadVo.getToken())
                .append("/")
                .append(fileUploadVo.getFileName())
                .append("_")
                .append(ImgCategory.SOURCE.getName())
                .append(".")
                .append(fileUploadVo.getFileExtension());
        fileUploadVo.setFileStoragePath(imgStoragePath.toString());

        log.info("file info ::{}", fileUploadVo.toString());
        //存储img到minIO，异步处理(X)
        ObjectWriteResponse response = minioService.putObject(fileUploadVo);
        log.info(response.toString());
        //获取img元数据，保存到数据库，异步处理(X)
        ImgInfo img = ImgInfo.builder()
                .imgUrl(StringGenerator.getURL())
                .originalFullName(fileUploadVo.getFileName())
                .imgCategory(ImgCategory.SOURCE)
                .size(fileUploadVo.getMultipartFile().getSize())
                .extension(fileUploadVo.getFileExtension())
                .storagePath(fileUploadVo.getFileStoragePath())
                .token(fileUploadVo.getToken())
                .build();
        int imgAffected = imgInfoDao.insertImgInfo(img);

        log.info("img::{}", img.toString());
        return R.success(response);


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
