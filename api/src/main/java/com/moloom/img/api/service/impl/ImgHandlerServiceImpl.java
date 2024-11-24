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
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.time.Duration;
import java.util.Date;
import java.util.List;


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

    //redis中imgInfo的key前缀
    //imgInfo 有效期 10 天
    private static final String IMG_PREFIX_OF_REDIS = "imgInfo:all:";


    @PostConstruct
    public void initImgBucket() {
        if (bucketConfig == null || bucketConfig.getBuckets() == null || bucketConfig.getBuckets().size() == 0) {
            log.error("class BucketConfig::Arg bucketConfig injects error");
            throw new IllegalStateException();
        }
        //获取存储图片的bucket 名称，规定了，第一个是存图片的
        imgBucket = bucketConfig.getBuckets().get(0);
    }

    /**
     * @param
     * @return
     * @author moloom
     * @date 2024-11-23 16:32:03
     * @description preload ImgInfo to redis
     */
    @PostConstruct
    public void preloadImgInfoToRedis() {
        // 从数据库拉取所有数据
        List<ImgInfo> imgInfos = imgInfoDao.getAllImgInfos();
        imgInfos.forEach(imgInfo -> {
            // 将imgInfo对象存储到redis中，key为imgUrl，value为imgInfo对象，保存 10 天
            redisTemplate.opsForValue().set(IMG_PREFIX_OF_REDIS + imgInfo.getImgUrl(), imgInfo, Duration.ofDays(10L));
        });
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
    public ResponseEntity<Object> download(DownloadVO vo) {
        //参数校验
        if (!StringGenerator.validateURL(vo.getUrl()))
            return ResponseEntity.badRequest().body(R.error(HttpStatus.BAD_REQUEST, "invalid params"));

        String key = IMG_PREFIX_OF_REDIS + vo.getUrl();
        //get ImgInfo obj
        ImgInfo imgInfo;
        if (redisTemplate.hasKey(key)) {
            imgInfo = (ImgInfo) redisTemplate.opsForValue().get(key);
        } else {
            //if not exists in redis, search to db
            synchronized (key.intern()) {
                log.debug("get lock of {}", key);
                //拿到锁后再判断一次数据有没有存在。避免有多个请求在wait,而第一个请求拿到锁后，后面的请求又去db查询
                if (redisTemplate.hasKey(key)) {
                    imgInfo = (ImgInfo) redisTemplate.opsForValue().get(key);
                } else {
                    log.info("search to db");
                    imgInfo = imgInfoDao.selectOneByImgUrl(vo.getUrl());
                    //db也没数据时，存一个值为null的keys进去，失效 1 分钟，防止缓存穿透
                    if (imgInfo == null) {
                        log.debug("set a key {} with value is null", key);
                        redisTemplate.opsForValue().set(key, null, Duration.ofMinutes(1L));
                        return ResponseEntity.badRequest().body(R.error(HttpStatus.NOT_FOUND));
                    }
                    //有效数据缓存到redis
                    redisTemplate.opsForValue().set(key, imgInfo, Duration.ofDays(10L));

                }
            }
        }
        //return 404 when img not founded
        if (imgInfo == null)
            return ResponseEntity.badRequest().body(R.error(HttpStatus.NOT_FOUND));

        vo.setStoragePath(imgInfo.getStoragePath());

        //设置bucket
        vo.setBucketName(imgBucket.getBucketName());
        log.info(imgInfo.toString());
        log.info(vo.toString());
        //获取文件流
        InputStreamResource inputStream = new InputStreamResource(minioService.getObject(vo));

        HttpHeaders headers = new HttpHeaders();
        //设置浏览器处理图片的方式，默认是 attachment 下载。并设置文件名
        headers.setContentDisposition(ContentDisposition.inline().filename(imgInfo.getOriginalFullName()).build());
        //设置当前时间
        headers.setDate(new Date().getTime());
        //设置缓存时间 30 天
        headers.setCacheControl(CacheControl.maxAge(Duration.ofDays(30)).cachePublic());
        //设置最后修改时间
        headers.setLastModified(imgInfo.getUpdatedTime().getTime());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(imgInfo.getSize())
                .contentType(MediaType.parseMediaType(imgInfo.getContentType()))
                .body(inputStream);
    }
}
