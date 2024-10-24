package com.moloom.img.api.utils;

/**
 * @author: moloom
 * @date: 2024-10-13 23:49
 * @description: 随即字符串 生成器
 */
public class StringGenerator {

    // 基础字符
    private static final String CHARACTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    // 字符集的长度
    private static final int CHARACTERS_LENGTH = CHARACTERS.length();
    // token的长度
    private static final int TOKEN_LENGTH = 32;


    public static String getToken() {
        //StringBuilder 比 StringBuffer 几乎快一倍
        StringBuilder token = new StringBuilder();

        //字符串拼接token
        for (int i = 0; i < TOKEN_LENGTH; ++i) {
            int randomIndex = (int) (Math.random() * CHARACTERS_LENGTH);
            token.append(CHARACTERS.charAt(randomIndex));
        }

        return token.toString();
    }
}
