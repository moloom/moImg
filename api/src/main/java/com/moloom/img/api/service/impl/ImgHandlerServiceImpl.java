package com.moloom.img.api.service.impl;

import com.moloom.img.api.config.BucketConfig;
import com.moloom.img.api.dao.ImgCameraInfoDao;
import com.moloom.img.api.dao.ImgInfoDao;
import com.moloom.img.api.entity.ImgCameraInfo;
import com.moloom.img.api.entity.ImgCategory;
import com.moloom.img.api.entity.ImgInfo;
import com.moloom.img.api.service.ImgHandlerService;
import com.moloom.img.api.to.Buckets;
import com.moloom.img.api.to.DownloadVO;
import com.moloom.img.api.to.R;
import com.moloom.img.api.utils.StringGenerator;
import com.moloom.img.api.vo.FileUploadVo;
import io.minio.*;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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


    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private MinioServiceImpl minioService;

    @Resource
    private ImgInfoDao imgInfoDao;

    @Resource
    private ImgCameraInfoDao imgCameraInfoDao;

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
            throw new IllegalStateException();
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

        //确保bucket存在
        boolean bucketExists = minioService.checkBucketExist(imgBucket.getBucketName());
        if (!bucketExists)
            minioService.makeBucket(imgBucket);

        //获取文件后缀名
        if (fileUploadVo.getFileExtension() == null || fileUploadVo.getFileExtension().isEmpty()) {
            try {
                //使用 Tika的MimeTypes获取文件后缀名
                String extension = MimeTypes.getDefaultMimeTypes().forName(fileUploadVo.getContentType()).getExtension();
                if (extension == null || extension.isEmpty())
                    throw new NullPointerException("get image extension error by mimetype,please check the uploaded file type is images!");
                fileUploadVo.setFileExtension(extension);
            } catch (MimeTypeException e) {
                throw new RuntimeException();
            }
        }
        //设置文件名
        if (fileUploadVo.getFileName() == null || fileUploadVo.getFileName().isEmpty()) {
            String filename = fileUploadVo.getMultipartFile().getOriginalFilename();
            int lastIndexOf = filename.lastIndexOf('.');
            if (lastIndexOf > 0)
                filename = filename.substring(0, lastIndexOf);
            fileUploadVo.setFileName(filename);
        }
        log.info("fileUploadVo.getMultipartFile().getOriginalFilename()文件名::{}", fileUploadVo.getMultipartFile().getOriginalFilename());
        //设置存储bucket名称
        fileUploadVo.setBucketName(imgBucket.getBucketName());
        //拼接存储路径，路径格式 {token}/{originalName}_{imgCategory}{extension}
        StringBuilder imgStoragePath = new StringBuilder()
                .append(fileUploadVo.getToken())
                .append("/")
                .append(fileUploadVo.getFileName())
                .append("_")
                .append(ImgCategory.SOURCE.getName())
                .append(fileUploadVo.getFileExtension());
        fileUploadVo.setFileStoragePath(imgStoragePath.toString());

        log.info("file info ::{}", fileUploadVo.toString());
        //--------------------------------存储img到minIO，异步处理(X)-------------------------------------
        ObjectWriteResponse response = minioService.putObject(fileUploadVo);
        log.info("ObjectWriteResponse::{}\n={}\n={}\n={}\n={}", response.etag(), response.bucket(), response.versionId(), response.object(), response.headers());
        //--------------------------------获取img元数据，保存到数据库，异步处理(X)-------------------------------------
        //初始化解析元数据要用到的对象
        Metadata metadata = new Metadata();
        Parser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler();
        ParseContext context = new ParseContext();
        try {
            // 解析图片的元数据，数据在 Metadata 对象中
            parser.parse(fileUploadVo.getMultipartFile().getInputStream(), handler, metadata, context);
        } catch (Exception e) {
            throw new RuntimeException("parse the images error");
        }
        log.info("元信息获取::{}", handler.toString());
        //getting the list of all metadata elements
        String[] metadataNames = metadata.names();
        log.info("\n\n");
            /*for (String name : metadataNames) {
                log.info("{}==={}", name, metadata.get(name));
            }*/

        //摄像头元信息插入到数据库

        //还有很多值像createdBy等都没插入进去!!!!!!!!!!!!!!!!!!!!!
        ImgCameraInfo imgCameraInfo = ImgCameraInfo.builder().build().fromMetadata(metadata);
        log.info(imgCameraInfo.toString());
        int flag = imgCameraInfoDao.insert(imgCameraInfo);
        if (flag > 0)
            log.info("imgCameraInfo插入成功");
        else
            log.info("imgCameraInfo插入失败");
        //插入图片信息到数据库
        ImgInfo img = ImgInfo.builder()
                .imgUrl(StringGenerator.getURL())
                .originalFullName(fileUploadVo.getFileName() + fileUploadVo.getFileExtension())
                .storageFullName(imgStoragePath.substring(imgStoragePath.indexOf("/") + 1, imgStoragePath.length()))
                .storagePath(fileUploadVo.getFileStoragePath())
                .size(fileUploadVo.getMultipartFile().getSize())
                .extension(fileUploadVo.getFileExtension())
                .contentType(fileUploadVo.getContentType())
                .token(fileUploadVo.getToken())
                .imgCategory(ImgCategory.SOURCE)
                .width(imgCameraInfo.getWidth())
                .build();
        int imgAffected = imgInfoDao.insert(img);

        return R.success().setData(img.getImgUrl());
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

    @Override
    public ResponseEntity<InputStreamResource> download(DownloadVO vo) {
        //判断img是否存在
        long imgId = imgInfoDao.imgExistById(vo.getUrl());
        if (imgId == 0)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        ImgInfo imgInfo = imgInfoDao.selectOneById(imgId);
        vo.setStoragePath(imgInfo.getStoragePath());

        //设置bucket
        vo.setBucketName(imgBucket.getBucketName());
        //获取文件流
        InputStreamResource inputStream = new InputStreamResource(minioService.getObject(vo));


        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + imgInfo.getOriginalFullName()); // inline 让浏览器直接展示
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType(imgInfo.getContentType()))
                .body(inputStream);
    }
}
