package com.mfg.askfm.dtos;

import lombok.Data;

@Data
public class PostCreateDto {

    private String text;
    private String title;
    private Long userId;
}
