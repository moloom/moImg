package com.moloom.img.api.controller;

import com.moloom.img.api.service.ImgService;
import com.moloom.img.api.service.TokensService;
import com.moloom.img.api.to.R;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @author: moloom
 * @date: 2024-12-11 20:36
 * @description: images api controller
 */
@RestController
@RequestMapping("/api/img")
@Slf4j
public class ImgController {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private ImgService imgService;

    @Resource
    private TokensService tokensService;


    /**
     * @param id
     * @param token
     * @return result of delete
     * @author moloom
     * @date 2024-12-11 23:00:08
     * @description Delete img by id;the request from the frontend
     */
    @DeleteMapping(path = "/delete/", consumes = "multipart/form-data")//TODO 提交的参数不对，等写页面时再改
    public R deleteImgById(@RequestBody Long id,
                           @RequestBody String token) {
        System.out.println("id=" + id + "\ttoken=" + token);
        return R.success("by id");
    }

    /**
     * @param url the image URL to be deleted;its maybe contained the extension
     * @return result of delete
     * @author moloom
     * @date 2024-12-11 23:05:55
     * @description Delete image by URL; the user's request is usually directly in the address bar
     */
    @DeleteMapping(path = "/delete/{url}")
    public R deleteImg(@PathVariable("url") String url,
                       @CookieValue(name = "token") String token) {
        // 校验 token
        if (!tokensService.checkAndCacheToken(token))
            return R.error("token is illegal");
        if (url == null || url.isBlank() || !imgService.checkAndCacheImg(url))
            return R.error("URL of images is not valid");

        //校验 url,判断url中是否包含扩展名

        //看是否有扩展名
        /*if (url.contains(".")) {
            //有扩展名则去掉拓展名
            String[] split = url.split("\\.");
            return imgService.deleteImgByUrl(url);
        } else if ()
            //没有扩展名
            return R.error("URL of images is not valid");*/

        System.out.println("url=" + url + "\tcookie=" + token);
        return R.success("by url");
    }
}
