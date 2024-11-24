package com.moloom.img.api.service;


import com.moloom.img.api.to.Buckets;
import com.moloom.img.api.vo.DownloadVO;
import com.moloom.img.api.vo.UploadVo;
import io.minio.ObjectWriteResponse;

import java.io.InputStream;
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

    /**
     * @param vo 存储的img和img信息
     * @return
     * @author moloom
     * @date 2024-10-31 01:30:22
     * @description Uploads given stream as object in bucket.
     */
    public ObjectWriteResponse putObject(UploadVo vo);

    /**
     * @param vo info about download
     * @return return InputStream,and must be closed after use to release network resources.
     * @author moloom
     * @date 2024-10-31 01:35:09
     * @description Gets data of an object.
     */
    public InputStream getObject(DownloadVO vo);
}
