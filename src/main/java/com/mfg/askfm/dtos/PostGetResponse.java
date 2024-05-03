package com.mfg.askfm.dtos;

import com.mfg.askfm.entities.Post;
import lombok.Data;

import java.util.List;

@Data
public class PostGetResponse {

    private Long id;
    private Long userId;
    private String userName;
    private String title;
    private String text;
    private List<LikeGetResponse> postLikes;

    public PostGetResponse(Post post, List<LikeGetResponse> likeList) {
        this.id = post.getId();
        this.userId = post.getUser().getId();
        this.userName = post.getUser().getUsername();
        this.title = post.getTitle();
        this.text = post.getText();
        this.postLikes = likeList;
    }
}
