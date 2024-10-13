package com.moloom.img.api.utils;

/**
 * @author: moloom
 * @date: 2024-10-13 23:49
 * @description: token 生成器
 */
public class TokenGenerator {

    private static final String CHARACTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int TOKEN_LENGTH = 32;
    private static final int MAX_ATTEMPTS = 10; // 最大尝试次数
    private static final int MAX_RETRIES = 3;  // 最大重试次数

    private static String getToken() {
        StringBuilder token = new StringBuilder();

        for (int i = 0; i < TOKEN_LENGTH; i++) {
            int randomIndex = (int) (Math.random() * CHARACTERS.length());
            token.append(CHARACTERS.charAt(randomIndex));
        }

        return token.toString();
    }
}
