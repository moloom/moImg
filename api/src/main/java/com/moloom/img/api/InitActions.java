package com.moloom.img.api;

import com.moloom.img.api.config.BucketConfig;
import com.moloom.img.api.exception.ExtensionMismatchException;
import com.moloom.img.api.service.MinioService;
import com.moloom.img.api.to.Buckets;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;


/**
 * @author: moloom
 * @date: 2024-10-28 14:59
 * @description: 在spring启动后执行一些操作
 */
@Component
@Slf4j
public class InitActions implements InitializingBean {

    @Resource
    private MinioService minioService;

    //获取bucket
    @Resource
    private BucketConfig bucketConfig;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("\n\n" + String.valueOf(System.currentTimeMillis()));
        log.info("This is InitActions implements InitializingBean");
        //在应用启动时，检查minio的几个bucket是否存在
        checkMinioBucketExist();

    }

    /**
     * @param
     * @return
     * @author moloom
     * @date 2024-10-28 17:30:56
     * @description 检查minio的几个bucket是否存在
     */
    public void checkMinioBucketExist() {
        try {
            minioService.makeBucketsIfNotExist(bucketConfig.getBuckets());

//            throw new ExtensionMismatchException("hello");
        } catch (Exception e) {
            log.error("initial check minIO bucket error.please check minio status is running.");
            e.printStackTrace();
            //启动时检查minio bucket出错直接关闭app
            System.exit(1);
        }

    }
}
