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

    /**
     * @param length String length
     * @return String
     * @author moloom
     * @date 2024-10-14 05:28:21
     * @description generate random string
     */
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

    /**
     * @param token
     * @return true if valid or false
     * @author moloom
     * @date 2024-11-20 21:10:28
     * @description check token is valid
     */
    public static boolean validateToken(String token) {
        if (token == null || token.isBlank())
            return false;

        // 正则匹配 TOKEN_LENGTH 位纯字母和数字
        return token.matches("^[a-zA-Z0-9]{" + TOKEN_LENGTH + "}$");
    }

    /**
     * @param url img url
     * @return true if valid or false
     * @author moloom
     * @date 2024-11-23 21:03:15
     * @description check url is valid
     */
    public static boolean validateURL(String url) {
        if (url == null || url.isBlank())
            return false;
        return url.matches("^[a-zA-Z0-9]{" + URL_LENGTH + "}$");
    }
}
