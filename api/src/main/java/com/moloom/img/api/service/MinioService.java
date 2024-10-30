package com.moloom.img.api.service;


import com.moloom.img.api.to.Buckets;
import io.minio.errors.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * @author: moloom
 * @date: 2024-10-28 18:44
 * @description: operations of minio
 */
public interface MinioService {

    /**
     * @param bucketName name of checked
     * @return true if bucket is exist
     * @author moloom
     * @date 2024-10-28 19:38:06
     * @description 检查minio的bucket是否存在
     */
    public boolean checkBucketExist(String bucketName);

    /**
     * @param buckets 创建的bucket信息
     * @return true if make successful
     * @author moloom
     * @date 2024-10-28 21:43:48
     * @description 创建 bucket
     */
    public boolean makeBucket(Buckets buckets);

    /**
     * @param buckets
     * @return true if make all successful
     * @author moloom
     * @date 2024-10-28 21:49:14
     * @description 若bucket不存在，则创建bucket
     */
    public boolean makeBucketsIfNotExist(ArrayList<Buckets> buckets);
}
