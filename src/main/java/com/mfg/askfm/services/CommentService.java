package com.mfg.askfm.services;

import com.mfg.askfm.dtos.CommentCreateDto;
import com.mfg.askfm.dtos.CommentGetResponse;
import com.mfg.askfm.dtos.CommentUpdateDto;
import com.mfg.askfm.entities.Comment;
import com.mfg.askfm.entities.Post;
import com.mfg.askfm.entities.User;
import com.mfg.askfm.repos.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;

    public CommentService(CommentRepository commentRepository, UserService userService, PostService postService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
    }


    public List<CommentGetResponse> getAllComments(Optional<Long> postId, Optional<Long> userId) {

        List<Comment> comments = null;
        if (postId.isPresent() && userId.isPresent())
            comments = commentRepository.findByPostIdAndUserId(postId.get(), userId.get());
        else if (postId.isPresent())
            comments = commentRepository.findByPostId(postId.get());
        else if (userId.isPresent())
            comments = commentRepository.findByUserId(userId.get());

        return comments.stream().map(comment -> new CommentGetResponse(comment)).collect(Collectors.toList());
    }

    public Comment createComment(CommentCreateDto commentCreateDto) {

        User user = userService.getUserById(commentCreateDto.getUserId());
        Post post = postService.getPostById(commentCreateDto.getPostId());
        if (user == null || post == null)
            return null;

        Comment tempComment = new Comment();
        tempComment.setText(commentCreateDto.getText());
        tempComment.setPost(post);
        tempComment.setUser(user);
        tempComment.setCreateDate(new Date());
        return commentRepository.save(tempComment);
    }

    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    public Comment updateCommentById(Long commentId, CommentUpdateDto commentUpdateDto) {

        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isPresent()) {
            Comment tempComment = comment.get();
            tempComment.setText(commentUpdateDto.getText());
            return commentRepository.save(tempComment);
        } else
            return null;
    }

    public void deleteCommentById(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
