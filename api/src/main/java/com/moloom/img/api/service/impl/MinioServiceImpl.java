package com.moloom.img.api.service.impl;

import com.moloom.img.api.service.MinioService;
import com.moloom.img.api.to.Buckets;
import com.moloom.img.api.vo.DownloadVO;
import com.moloom.img.api.vo.UploadVo;
import io.minio.*;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * @author: moloom
 * @date: 2024-10-28 19:40
 * @description: operations of minio
 */
@Service("minioService")
@Slf4j
public class MinioServiceImpl implements MinioService {

    @Resource
    private MinioClient minioClient;

    @Override
    public boolean checkBucketExist(String bucketName) {
        try {
            boolean bucketExists = minioClient.bucketExists(
                    BucketExistsArgs.builder()
                            .bucket(bucketName)
                            .build());
            if (bucketExists)
                log.info("checkBucketExist()::minIO bucket {} is exist", bucketName);
            else
                log.info("checkBucketExist()::minIO bucket {} is not exist", bucketName);
            return bucketExists;
        } catch (Exception e) {
            log.error("checkBucketExist()::check minIO bucket error.please check minio status is running.");
            throw new RuntimeException();
        }
    }

    @Override
    public boolean makeBucket(Buckets bucket) {
        //判空
        if (bucket == null || bucket.getBucketName() == null)
            return false;
        try {
            //先判断是否存在同名bucket,不存在再创建
            boolean bucketExists = this.checkBucketExist(bucket.getBucketName());
            if (!bucketExists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket.getBucketName()).build());
            }
            //存储tags
            if (bucket.getBucketTags() != null && !bucket.getBucketTags().isEmpty())
                minioClient.setBucketTags(SetBucketTagsArgs.builder().bucket(bucket.getBucketName()).tags(bucket.getBucketTags()).build());
            return true;
        } catch (Exception e) {
            log.error("makeBucket()::make bucket error");
            throw new RuntimeException();
        }
    }

    @Override
    public boolean makeBucketsIfNotExist(ArrayList<Buckets> buckets) {
        if (buckets == null || buckets.isEmpty())
            return false;
        for (int i = 0; i < buckets.size(); i++) {
            if (!this.makeBucket(buckets.get(i))) {
                log.info("makeBucketsIfNotExist()::make bucket error");
                return false;
            }
        }
        return true;
    }

    @Override
    public ObjectWriteResponse putObject(UploadVo vo) {
        try {
            return minioClient.putObject(
                    PutObjectArgs
                            .builder()
                            .bucket(vo.getBucketName())
                            .stream(vo.getMultipartFile().getInputStream(), vo.getMultipartFile().getSize(), -1)
                            .contentType(vo.getContentType())
                            .object(vo.getFileStoragePath())
                            .build());
        } catch (Exception e) {
            log.error("putObject()::put object to minIO error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public InputStream getObject(DownloadVO vo) {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(vo.getBucketName())
                            .object(vo.getStoragePath())
                            .build());
        } catch (Exception e) {
            log.error("getObject()::get object from minIO error");
            throw new RuntimeException(e);
        }
    }
}
