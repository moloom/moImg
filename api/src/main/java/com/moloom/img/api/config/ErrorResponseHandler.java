package com.moloom.img.api.config;

import com.moloom.img.api.to.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: moloom
 * @date: 2024-11-21 00:05
 * @description: error response handler
 */
//@ControllerAdvice
@Slf4j
public class ErrorResponseHandler {
    // 处理所有500错误
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<R> handleInternalServerError(Exception ex) {

        // 打印异常栈信息
        log.error(ex.getMessage(), ex);
        // 返回自定义的错误信息
        return new ResponseEntity<>(R.error(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
