package com.mfg.askfm.services;

import com.mfg.askfm.dtos.LikeGetResponse;
import com.mfg.askfm.dtos.PostCreateDto;
import com.mfg.askfm.dtos.PostGetResponse;
import com.mfg.askfm.dtos.PostUpdateDto;
import com.mfg.askfm.entities.Post;
import com.mfg.askfm.entities.User;
import com.mfg.askfm.repos.PostRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final LikeService likeService;


    public PostService(PostRepository postRepository, UserService userService, @Lazy LikeService likeService) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.likeService = likeService;
    }

    public List<PostGetResponse> getAllPosts(Optional<Long> userId) {
        List<Post> postList;
        if (userId.isPresent())
            postList = postRepository.findByUserId(userId.get());
        else
            postList = postRepository.findAll();
        return postList.stream().map(p -> {
            List<LikeGetResponse> likeList = likeService.getAllLikes(Optional.of(p.getId()), Optional.empty());
            return new PostGetResponse(p, likeList);
        }).collect(Collectors.toList());
    }

    public Post createPost(PostCreateDto postCreateDto) {

        User user = userService.getUserById(postCreateDto.getUserId());
        if (user == null)
            return null;

        Post tempPost = new Post();
        tempPost.setTitle(postCreateDto.getTitle());
        tempPost.setText(postCreateDto.getText());
        tempPost.setUser(user);
        tempPost.setCreateDate(new Date());

        return postRepository.save(tempPost);
    }

    public Post getPostById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    public PostGetResponse getPostByIdWithLikes(Long postId) {

        Post post = postRepository.findById(postId).orElse(null);
        List<LikeGetResponse> likes = likeService.getAllLikes(Optional.ofNullable(null), Optional.of(postId));
        return new PostGetResponse(post, likes);
    }

    public Post updatePostById(Long postId, PostUpdateDto postUpdateDto) {

        Optional<Post> foundPost = postRepository.findById(postId);
        if (foundPost.isPresent()) {
            Post tempPost = foundPost.get();
            tempPost.setText(postUpdateDto.getText());
            tempPost.setTitle(postUpdateDto.getTitle());
            return postRepository.save(tempPost);
        } else
            return null;
    }

    public void deletePostById(Long postId) {
        postRepository.deleteById(postId);
    }
}
