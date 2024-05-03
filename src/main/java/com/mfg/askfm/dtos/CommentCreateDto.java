package com.mfg.askfm.dtos;

import lombok.Data;

@Data
public class CommentCreateDto {

    private Long postId;
    private Long userId;
    private String text;
}
