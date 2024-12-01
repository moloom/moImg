package com.moloom.img.api.exception;

/**
 * @author: moloom
 * @date: 2024-12-01 17:49
 * @description: 参数错误异常类
 */
public class BadRequestException extends RuntimeException {

    public BadRequestException() {
        super("bad request parameters");
    }

    public BadRequestException(String message) {
        super(message);
    }
}
