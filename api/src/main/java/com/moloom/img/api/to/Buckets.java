package com.moloom.img.api.to;

import lombok.Data;

import java.util.HashMap;

/**
 * @author: moloom
 * @date: 2024-10-28 20:28
 * @description: bucket TO class
 */
@Data
public class Buckets {
    private String bucketName;
    private HashMap<String, String> bucketTags;
}
