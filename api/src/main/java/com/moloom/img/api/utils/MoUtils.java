package com.moloom.img.api.utils;

import org.jetbrains.annotations.NotNull;

/**
 * @author: moloom
 * @date: 2024-11-27 17:28
 * @description: class utils of moloom
 */
public class MoUtils {

    /**
     * @param day
     * @return days around  half param day
     * @author moloom
     * @date 2024-11-27 17:29:14
     * @description 在当前日期上加减天数，上下差不超过 day 的一半
     */
    public static long randomDays(int day) {
        long days = 5l;
        // 最低天数是 5~10
        if (day <= 5)
            days = days + (long) (Math.random() * 5);
        else
            // 天数是 day 的一半
            days = (day >> 1) + (long) (Math.random() * (day));
        return days;
    }
}
