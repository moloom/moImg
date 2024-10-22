package com.moloom.img.api.controller;

import com.moloom.img.api.exception.ExtensionMismatchException;
import com.moloom.img.api.service.UploadDispatcherService;
import com.moloom.img.api.to.R;
import com.moloom.img.api.service.CommonService;
import com.moloom.img.api.vo.FileUploadVo;
import io.minio.MinioClient;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author: moloom
 * @date: 2024-10-03 19:31
 * @description: receive upload file
 */
@RestController
@Slf4j
public class UploadController {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private CommonService commonService;

    @Autowired
    private UploadDispatcherService imgUploadDispatcherService;

    @RequestMapping(value = "/upload/{token}", method = RequestMethod.POST, consumes = "multipart/form-data")
    public R upload(FileUploadVo fileUploadVo, @RequestParam("file") MultipartFile multipartFile,
                    @PathVariable("token") String token,
                    HttpServletRequest request) throws Exception {
        //打印所有的参数
        log.info("request.getRemoteHost() = " + request.getRemoteHost());
        log.info("X-Forwarded-For:" + request.getHeader("X-Forwarded-For"));
        log.info("X-real-ip:" + request.getHeader("X-Real-IP"));
        fileUploadVo.setToken(token);
        fileUploadVo.setMultipartFile(multipartFile);
        log.debug(fileUploadVo.toString());
        R r = null;
        try {
            r = imgUploadDispatcherService.uploadDispatcher(fileUploadVo);
        } catch (ExtensionMismatchException e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }
        return r;
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
