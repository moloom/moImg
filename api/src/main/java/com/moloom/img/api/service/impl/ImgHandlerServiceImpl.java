package com.moloom.img.api.service.impl;

import com.moloom.img.api.config.BucketConfig;
import com.moloom.img.api.entity.ImgInfo;
import com.moloom.img.api.service.ImgHandlerService;
import com.moloom.img.api.to.R;
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
    private MinioClient minioClient;

    @Value("${moimg.upload.upload-root-path}")
    private String prePath;

    @Resource
    private BucketConfig bucketConfig;

    //存储图片的bucket名称
    private String imgBucketName;

    @PostConstruct
    public void initImgBucketName() {
        if (bucketConfig == null || bucketConfig.getBuckets() == null || bucketConfig.getBuckets().size() == 0) {
            log.error("bucketConfig::Arg bucketConfig injects error");
            throw new IllegalStateException("bucketConfig is null or empty");
        }
        //获取存储图片的bucket 名称，规定了，第一个是存图片的
        imgBucketName = bucketConfig.getBuckets().get(0).getBucketName();
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
        try {
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(imgBucketName).build());
            if (!bucketExists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(imgBucketName).build());
            } else log.info("bucket " + imgBucketName + " is exists");
            ObjectWriteResponse flag = minioClient.putObject(PutObjectArgs.builder().bucket(imgBucketName).stream(fileUploadVo.getMultipartFile().getInputStream(), fileUploadVo.getMultipartFile().getSize(), -1).object(fileUploadVo.getMultipartFile().getName() + ".jpg").build());
            System.out.println(flag.etag() + "\n" + flag.bucket() + "\n" + flag.versionId());
            return R.success(flag);
        } catch (IOException e) {
            log.debug("storage error");
            throw new RuntimeException(e);
        } catch (ServerException e) {
            throw new RuntimeException(e);
        } catch (InsufficientDataException e) {
            throw new RuntimeException(e);
        } catch (ErrorResponseException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (InvalidResponseException e) {
            throw new RuntimeException(e);
        } catch (XmlParserException e) {
            throw new RuntimeException(e);
        } catch (InternalException e) {
            throw new RuntimeException(e);
        }


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
