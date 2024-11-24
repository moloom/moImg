package com.moloom.img.api.controller;

import com.moloom.img.api.service.ImgHandlerService;
import com.moloom.img.api.service.UploadDispatcherService;
import com.moloom.img.api.vo.DownloadVO;
import com.moloom.img.api.to.R;
import com.moloom.img.api.utils.StringGenerator;
import com.moloom.img.api.vo.UploadVo;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author: moloom
 * @date: 2024-10-03 19:31
 * @description: receive upload file
 */
@RestController
@Slf4j
public class UpDownLoadController {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    @Resource
    private UploadDispatcherService imgUploadDispatcherService;

    @Resource
    private ImgHandlerService imgHandlerService;

    @RequestMapping(value = "/api/upload/{token}", method = RequestMethod.POST, consumes = "multipart/form-data")
    public R upload(UploadVo vo, @RequestParam("file") MultipartFile multipartFile,
                    @PathVariable("token") String token,
                    HttpServletRequest request) {

        // check token is not empty
        if (token == null || token.isEmpty())
            return R.error(HttpStatus.BAD_REQUEST, "token is empty");
        //check token and valid and registered
        if (!StringGenerator.validateToken(token) || redisTemplate.opsForValue().get("token:all:" + token) == null)
            return R.error(HttpStatus.BAD_REQUEST, "token is illegal");

        //打印所有的参数
        log.info("request.getRemoteHost() = " + request.getRemoteHost());
        log.info("X-Forwarded-For:" + request.getHeader("X-Forwarded-For"));
        log.info("X-real-ip:" + request.getHeader("X-Real-IP"));
        vo.setToken(token).setMultipartFile(multipartFile);
        log.info("controller::::{}", vo.toString());
        try {
            return R.success().setData(imgUploadDispatcherService.uploadDispatcher(vo));
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }
    }

    @GetMapping("/i/{url}.{extension}")
    public ResponseEntity<Object> download(
            @PathVariable String url,
            @PathVariable String extension) {

        return imgHandlerService.download(DownloadVO.builder()
                .url(url)
                .extension(extension)
                .build());

    }

}
