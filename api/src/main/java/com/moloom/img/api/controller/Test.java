package com.moloom.img.api.controller;

import com.alibaba.fastjson.JSONArray;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: moloom
 * @date: 2024-09-23 21:20
 * @description:
 */
@RestController
@RequestMapping("/i")
public class Test {
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String TestConnection() {

        return JSONArray.toJSONString("Welcome to moImg!");
    }
}
