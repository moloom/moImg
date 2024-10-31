package com.moloom.img.api.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author: moloom
 * @date: 2024-10-13 23:49
 * @description: 随即字符串 生成器
 */
public class StringGenerator {

    // 基础字符
    private static final String CHARACTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    // 基础字符集的长度
    private static final int CHARACTERS_LENGTH = CHARACTERS.length();
    // token的长度
    private static final int TOKEN_LENGTH = 20;

    //文件资源的访问字符串长度
    private static final int URL_LENGTH = 32;


    public static String getToken() {
        return generateString(TOKEN_LENGTH);
    }

    public static String getURL() {
        return generateString(URL_LENGTH);
    }

    public static String generateString(int length) {
        //StringBuilder 比 StringBuffer 几乎快一倍
        StringBuilder string = new StringBuilder();

        //字符串拼接token
        for (int i = 0; i < length; ++i) {
            int randomIndex = (int) (Math.random() * CHARACTERS_LENGTH);
            string.append(CHARACTERS.charAt(randomIndex));
        }
        return string.toString();
    }
}
