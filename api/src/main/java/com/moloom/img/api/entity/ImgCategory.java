package com.moloom.img.api.entity;

/**
 * @author: moloom
 * @date: 2024-10-31 23:03
 * @description: 图片种类
 */
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

    public byte getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static ImgCategory matchFromValue(byte value) {
        switch (value) {
            case 1:
                return SOURCE;
            case 2:
                return THUMBNAIL;
            case 3:
                return CUSTOM;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return "ImgCategory{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
