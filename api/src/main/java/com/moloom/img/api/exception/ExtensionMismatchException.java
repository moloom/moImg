package com.moloom.img.api.exception;

/**
 * @author: moloom
 * @date: 2024-10-22 21:06
 * @description: 文件类型不匹配异常类，文件类型只支持 image、video和压缩包
 */
public class ExtensionMismatchException extends RuntimeException {

    public ExtensionMismatchException() {
        super("File type does not match any of the supported formats: image, video, or archive!");
    }

    public ExtensionMismatchException(String message) {
        super(message);
    }
}