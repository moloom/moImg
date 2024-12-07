package com.moloom.img.api.controller;

import com.moloom.img.api.service.TokensService;
import com.moloom.img.api.to.R;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @author: moloom
 * @date: 2024-11-20 18:06
 * @description: tokens controller
 */
@RestController
@RequestMapping("/api/token")
public class TokensController {

    @Resource
    private TokensService tokensService;

    /**
     * @return String token if success or error
     * @author moloom
     * @date 2024-11-20 23:39:03
     * @description generate a token and save it to redis
     */
    @GetMapping("/reg")
    public R register() {
        return tokensService.registerInRedis();
    }
}
