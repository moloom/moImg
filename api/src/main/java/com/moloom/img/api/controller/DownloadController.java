package com.moloom.img.api.controller;

import com.moloom.img.api.service.ImgHandlerService;
import com.moloom.img.api.to.DownloadTO;
import jakarta.annotation.Resource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author: moloom
 * @date: 2024-11-04 00:22
 * @description: img or video download controller
 */
@Controller
public class DownloadController {

    @Resource
    private ImgHandlerService imgHandlerService;

    @GetMapping("/i/{url}.{extension}")
    public ResponseEntity<InputStreamResource> download(
            @PathVariable String url,
            @PathVariable String extension) {

        return imgHandlerService.download(DownloadTO.builder()
                .url(url)
                .extension(extension)
                .build());

    }

}