package com.mfg.askfm.dtos;

import com.mfg.askfm.entities.Like;
import lombok.Data;

@Data
public class LikeGetResponse {

    private Long id;
    private Long userId;
    private Long postId;

    public LikeGetResponse(Like entity) {
        this.id = entity.getId();
        this.userId = entity.getUser().getId();
        this.postId = entity.getPost().getId();
    }
}
