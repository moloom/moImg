package com.moloom.img.api.controller;

import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.UploadObjectArgs;
import io.minio.errors.*;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author: moloom
 * @date: 2024-10-03 19:31
 * @description: receive upload file
 */
@RestController
@RequestMapping(method = RequestMethod.GET)
public class UploadController {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private MinioClient minioClient;

    @Value("${moimg.upload.upload-root-path}")
    private String path;

    @RequestMapping(value = "/upload/{token}", method = RequestMethod.POST, consumes = "multipart/form-data")
    public String upload(@RequestParam("file") MultipartFile multipartFile,
                         HttpServletRequest request,
                         HttpSession session,
                         @PathVariable String token) throws Exception {
        //打印所有的参数
        System.out.println("上传地址:" + path);
        System.out.println("request.getRemoteHost() = " + request.getRemoteHost());
        System.out.println("X-Forwarded-For:" + request.getHeader("X-Forwarded-For"));
        System.out.println("X-real-ip:" + request.getHeader("X-Real-IP"));

        ObjectWriteResponse flag = minioClient.putObject(PutObjectArgs.builder()
                .bucket("img")
                .stream(multipartFile.getInputStream(), multipartFile.getSize(), -1).object("/1/2/3/4.jpg")
                .build());
        System.out.println(flag.etag() + "\n" + flag.bucket() + "\n" + flag.versionId());

//        System.out.println("multipartFile 是否为空：" + multipartFile == null);
//        multipartFile.transferTo(new File(path + "/f2.jpeg"));
        return "hahaha~";
    }

/*    @RequestMapping(value = "/uploadd/{token}", method = RequestMethod.POST, consumes = "multipart/form-data")
    public String upload(@RequestParam("file") FileInputStream inputStream,
                         HttpServletRequest request,
                         HttpSession session,
                         @PathVariable String token) throws IOException {
        //打印所有的参数
        System.out.println("path:" + path);
        System.out.println("request.getRemoteHost() = " + request.getRemoteHost());
        System.out.println("X-Forwarded-For:" + request.getHeader("X-Forwarded-For"));
        System.out.println("X-real-ip:" + request.getHeader("X-Real-IP"));
//        System.out.println("inputStream 是否为空：" + inputStream == null);
        FileCopyUtils.copy(inputStream, new FileOutputStream(path + "/f3.jpeg"));
        return "xixixixihahaha~";
    }*/

}
