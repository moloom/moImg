package com.moloom.img.api.config;

import com.moloom.img.api.exception.BadRequestException;
import com.moloom.img.api.exception.ExtensionMismatchException;
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
@ControllerAdvice
@Slf4j
public class ErrorResponseHandler {


    @ExceptionHandler({ExtensionMismatchException.class, BadRequestException.class})
    @ResponseBody
    public ResponseEntity<R> handleExtensionMismatchException(Exception ex) {
        // 打印异常栈信息
        log.error(ex.getMessage(), ex);
        // 返回自定义的错误信息
        return new ResponseEntity<>(R.error(HttpStatus.BAD_REQUEST, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    // 对系统内部所有错误统一处理，返回500和固定错误信息
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<R> handleInternalServerError(Exception ex) {

        // 打印异常栈信息
        log.error(ex.getMessage(), ex);
        // 返回自定义的错误信息
        return new ResponseEntity<>(R.error(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
