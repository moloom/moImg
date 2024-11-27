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
        // default day is around 10 days
        if (day <= 5)
            return (long) (Math.random() * 10);
        return (long) (Math.random() * (day >> 1));

    }
}
