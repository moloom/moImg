package com.moloom.img.api.entity;

import lombok.Getter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author: moloom
 * @date: 2024-10-31 23:03
 * @description: 图片种类
 */
@Getter
public enum ImgCategory {
    SOURCE("source", 1),
    THUMBNAIL("thumbnail", 2),
    CUSTOM("custom", 3);


    private final String name;
    private final byte value;

    ImgCategory(String name, int value) {
        this.name = name;
        this.value = (byte) value;
    }

    @Nullable
    public static ImgCategory matchFromValue(byte value) {
        return switch (value) {
            case 1 -> SOURCE;
            case 2 -> THUMBNAIL;
            case 3 -> CUSTOM;
            default -> null;
        };
    }

    @NotNull
    @Contract(pure = true)
    @Override
    public String toString() {
        return "ImgCategory{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
