package com.moloom.img.api.service.impl;

import com.moloom.img.api.config.BucketConfig;
import com.moloom.img.api.dao.DescriptionDao;
import com.moloom.img.api.dao.MetadataDao;
import com.moloom.img.api.dao.ImgDao;
import com.moloom.img.api.entity.DescriptionEntity;
import com.moloom.img.api.entity.ImgEntity;
import com.moloom.img.api.entity.MetadataEntity;
import com.moloom.img.api.entity.ImgCategory;
import com.moloom.img.api.service.ImgHandlerService;
import com.moloom.img.api.to.Buckets;
import com.moloom.img.api.utils.MoUtils;
import com.moloom.img.api.vo.DownloadVO;
import com.moloom.img.api.to.R;
import com.moloom.img.api.utils.StringGenerator;
import com.moloom.img.api.vo.UploadVo;
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
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    private ImgDao imgDao;

    @Resource
    private MetadataDao metadataDao;

    @Resource
    private DescriptionDao descriptionDao;

    @Value("${moimg.upload.upload-root-path}")
    private String prePath;

    @Resource
    private BucketConfig bucketConfig;

    //存储图片的bucket名称
    private Buckets imgBucket;

    //redis中imgInfo的key前缀
    @Resource
    private String imgInfoPrefix;

    @Resource
    private String serverHost;


    /**
     * @author moloom
     * @date 2024-10-31 02:11:28
     * @description init arg imgBucket from bucketConfig
     */
    @PostConstruct
    public void initImgBucket() {
        if (bucketConfig == null || bucketConfig.getBuckets() == null || bucketConfig.getBuckets().isEmpty()) {
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
     * @description preload ImgEntity to redis
     */
//    @PostConstruct
    public void preloadImgInfoToRedis() {
        // 从数据库拉取所有数据
        List<ImgEntity> imgEntities = imgDao.getAllImg();
        imgEntities.forEach(imgEntity -> {
            // 将imgInfo对象存储到redis中，key为imgUrl，value为imgInfo对象，保存 14+-7 天
            redisTemplate.opsForValue().set(imgInfoPrefix + imgEntity.getImgUrl(), imgEntity, Duration.ofDays(MoUtils.randomDays(14)));
        });
    }


    @Override
    public R imghandler(UploadVo vo) {
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
        if (vo.getFileExtension() == null || vo.getFileExtension().isEmpty()) {
            try {
                //使用 Tika的MimeTypes获取文件后缀名
                String extension = MimeTypes.getDefaultMimeTypes().forName(vo.getContentType()).getExtension();
                if (extension == null || extension.isEmpty())
                    throw new NullPointerException("get image extension error by mimetype,please check the uploaded file type is images!");
                vo.setFileExtension(extension);
            } catch (MimeTypeException e) {
                throw new RuntimeException();
            }
        }
        //设置文件名
        if (vo.getFileName() == null || vo.getFileName().isEmpty()) {
            String filename = vo.getMultipartFile().getOriginalFilename();
            int lastIndexOf = filename.lastIndexOf('.');
            if (lastIndexOf > 0)
                filename = filename.substring(0, lastIndexOf);
            vo.setFileName(filename);
        }
        //设置存储bucket名称
        vo.setBucketName(imgBucket.getBucketName());
        //拼接存储路径，路径格式 {token}/{originalName}_{imgCategory}{extension}
        StringBuilder imgStoragePath = new StringBuilder()
                .append(vo.getToken().getToken())
                .append("/")
                .append(vo.getFileName())
                .append("_")
                .append(ImgCategory.SOURCE.getName())
                .append(vo.getFileExtension());
        vo.setFileStoragePath(imgStoragePath.toString());

        log.info("file info ::{}", vo.toString());

        //存储img到minIO，
        // TODO 异步处理(X)
        ObjectWriteResponse response = minioService.putObject(vo);
        log.info("Etag={}", response.etag());

        //获取img元数据，保存到数据库
        //TODO 异步处理(X)
        //初始化解析元数据要用到的对象
        Metadata metadata = new Metadata();
        Parser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler();
        ParseContext context = new ParseContext();
        try {
            // 解析图片的元数据，数据在 Metadata 对象中
            parser.parse(vo.getMultipartFile().getInputStream(), handler, metadata, context);
        } catch (Exception e) {
            throw new RuntimeException("parse the images error");
        }
        log.info("元信息获取");
        //getting the list of all metadata elements
        String[] metadataNames = metadata.names();
        for (String name : metadataNames)
            log.info("{}->{}", name, metadata.get(name));

        //提取元信息并封装到 MetadataEntity 对象
        MetadataEntity metadataEntity = MetadataEntity.builder().createdBy(vo.getToken().getUserId()).build().fromMetadata(metadata);
        log.info(metadataEntity.toString());
        //若 description 不为空，则封装到 DescriptionEntity 对象并插入到数据库
        if (metadataEntity.getDescription() != null && !metadataEntity.getDescription().isBlank()) {
            DescriptionEntity description = DescriptionEntity.builder()
                    //description 属性数据库存储的类型是 text ，长度只有 2^16=65536；只存储 description 的前 65536 个字符
                    .description(StringGenerator.subStringByLength(metadataEntity.getDescription(), 65536))
                    .build();
            //把 description 插入到数据库
            descriptionDao.insert(description);
            //关联 metadata 和 description
            metadataEntity.setDescriptionId(description.getDescriptionId());
        }
        //插入元信息到数据库
        int flag = metadataDao.insert(metadataEntity);
        if (flag > 0)
            log.info("metadataEntity 插入成功");
        else
            log.info("metadataEntity 插入失败");
        // TODO geo信息收集，可以和 metadata 双线程处理
        //插入图片信息到数据库
        ImgEntity img = ImgEntity.builder()
                .imgUrl(StringGenerator.getURL())
                .originalFullName(vo.getFileName() + vo.getFileExtension())
                .storageFullName(imgStoragePath.substring(imgStoragePath.indexOf("/") + 1, imgStoragePath.length()))
                .storagePath(vo.getFileStoragePath())
                .size(vo.getMultipartFile().getSize())
                .extension(vo.getFileExtension())
                .contentType(vo.getContentType())
                .token(vo.getToken().getToken())
                .imgCategory(ImgCategory.SOURCE)
                .width(metadataEntity.getWidth())
                .length(metadataEntity.getLength())
                .metadataId(metadataEntity.getMetadataId())
                .createdBy(vo.getToken().getUserId())
                // TODO originalCreatedTime 和 originalModifiedTime 由前端获取，等写页面时再赋值
                .build();
        int imgAffected = imgDao.insert(img);
        log.info(img.toString());

        return R.success()
                .put("file name", img.getOriginalFullName())
                //拼接图片全链接;格式: {serverHost}/i/{imgURL}.{extension};例：http://localhost:8080/i/wv0o2EADJDkF4ixjyu3yxlq3jwt2qO0p.jpg
                .put("https URL", serverHost + "/i/" + img.getImgUrl() + img.getExtension());
    }

    @Override
    public R saveImg(ImgEntity img) {
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
    public ResponseEntity<Object> download(@NotNull DownloadVO vo) {
        //参数校验
        if (!StringGenerator.validateURL(vo.getUrl()))
            return ResponseEntity.badRequest().body(R.error(HttpStatus.BAD_REQUEST, "invalid params"));

        String key = imgInfoPrefix + vo.getUrl();
        //get ImgEntity obj
        //TODO 考虑用redis锁
        ImgEntity imgEntity;
        if (redisTemplate.hasKey(key)) {
            imgEntity = (ImgEntity) redisTemplate.opsForValue().get(key);
        } else {
            //if not exists in redis, search to db
            synchronized (key.intern()) {
                log.debug("get lock of {}", key);
                //拿到锁后再判断一次数据有没有存在。避免有多个请求在wait,而第一个请求拿到锁后，后面的请求又去db查询
                if (redisTemplate.hasKey(key)) {
                    imgEntity = (ImgEntity) redisTemplate.opsForValue().get(key);
                } else {
                    log.debug("search to db");
                    imgEntity = imgDao.selectOneByImgUrl(vo.getUrl());
                    //db也没数据时，存一个值为null的keys进去，失效 1 分钟，防止缓存穿透
                    if (imgEntity == null) {
                        log.debug("set a key {} with value is null", key);
                        redisTemplate.opsForValue().set(key, null, Duration.ofMinutes(1L));
                    } else
                        //有效数据缓存到redis
                        redisTemplate.opsForValue().set(key, imgEntity, Duration.ofDays(10L));
                }
            }
        }
        //return 404 when img not founded
        if (imgEntity == null)
            return ResponseEntity.badRequest().body(R.error(HttpStatus.NOT_FOUND));

        vo.setStoragePath(imgEntity.getStoragePath());

        //设置bucket
        vo.setBucketName(imgBucket.getBucketName());
        log.info(imgEntity.toString());
        log.info(vo.toString());
        //获取文件流
        InputStreamResource inputStream = new InputStreamResource(minioService.getObject(vo));

        HttpHeaders headers = new HttpHeaders();
        //设置浏览器处理图片的方式，默认是 attachment 下载。并设置文件名
        headers.setContentDisposition(ContentDisposition.inline().filename(imgEntity.getOriginalFullName()).build());
        //设置当前时间
        headers.setDate(new Date().getTime());
        //设置缓存时间 30 天
        headers.setCacheControl(CacheControl.maxAge(Duration.ofDays(30)).cachePublic());
        //设置最后修改时间
        headers.setLastModified(imgEntity.getUpdatedTime().getTime());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(imgEntity.getSize())
                .contentType(MediaType.parseMediaType(imgEntity.getContentType()))
                .body(inputStream);
    }
}
