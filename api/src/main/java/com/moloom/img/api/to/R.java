package com.moloom.img.api.to;

import org.springframework.http.HttpStatus;

import java.util.LinkedHashMap;

/**
 * @author: moloom
 * @date: 2024-10-20 04:47
 * @description: api返回类：status: 状态码；msg: 提示信息，data: 数据。
 * HashMap类会颠倒key的顺序，所以这里使用LinkedHashMap
 */
public class R extends LinkedHashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    private R(int status, String msg) {
        put("status", status);
        put("msg", msg);
    }

    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public Integer getStatus() {
        return (Integer) this.get("status");
    }

    public R setData(Object data) {
        put("data", data);
        return this;
    }

    // 只能私有，外部不能new
    private static R r(int status, String msg) {
        return new R(status, msg);
    }

    /**
     * @param
     * @return
     * @author moloom
     * @date 2024-10-22 17:06:04
     * @description 默认错误，500, 服务内部错误
     */
    public static R error() {
        return error(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static R error(String msg) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR, msg);
    }

    public static R error(HttpStatus status) {
        return error(status, status.getReasonPhrase());
    }

    public static R error(HttpStatus status, String msg) {
        return r(status.value(), msg);
    }

    public static R success() {
        return success(HttpStatus.OK);
    }

    public static R success(String msg) {
        return success(HttpStatus.OK, msg);
    }

    public static R success(String msg, Object data) {
        return success(msg).setData(data);
    }

    public static R success(HttpStatus status) {
        return success(status, status.getReasonPhrase());
    }

    public static R success(HttpStatus status, String msg) {
        return r(status.value(), msg);
    }

}
