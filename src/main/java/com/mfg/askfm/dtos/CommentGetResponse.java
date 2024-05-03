package com.mfg.askfm.dtos;

import com.mfg.askfm.entities.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentGetResponse {

    Long id;
    Long userId;
    String text;
    String userName;

    public CommentGetResponse(Comment comment) {
        this.id = comment.getId();
        this.userId = comment.getId();
        this.text = comment.getText();
        this.userName = comment.getUser().getUsername();
    }
}
