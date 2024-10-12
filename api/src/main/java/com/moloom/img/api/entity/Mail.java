package com.moloom.img.api.entity;

import lombok.Data;

@Data
public class Mail {
    private String addressee;
    private String text;
    private String subject;

}
