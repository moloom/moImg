package com.moloom.img.api.to;

import java.util.LinkedHashMap;

/**
 * @author: moloom
 * @date: 2024-10-20 04:47
 * @description: api返回类：code: 0表示成功，其他表示失败；msg: 提示信息，data: 数据。
 * HashMap类会颠倒key的顺序，所以这里使用LinkedHashMap
 */
public class R<T> extends LinkedHashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public R() {
        put("code", 0);
        put("msg", "success");
    }

    public R(int code, String msg) {
        put("code", code);
        put("msg", msg);
    }

    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public Integer getCode() {
        return (Integer) this.get("code");
    }

    public R setData(Object data) {
        put("data", data);
        return this;
    }

    /**
     * @param
     * @return
     * @author moloom
     * @date 2024-10-22 17:06:04
     * @description 默认错误，500, 服务内部错误
     */
    public static R error() {
        return error(500, "service internal error");
    }

    public static R error(String msg) {
        return error(1, msg);
    }

    public static R error(int code, String msg) {
        return new R(code, msg);
    }

    public static R success() {
        return new R();
    }

    public static R success(String msg) {
        return new R().put("msg", msg);
    }

    public static R success(String msg, Object data) {
        return success(msg).put("data", data);
    }

    public static R success(Object data) {
        return new R().put("data", data);
    }

}
