package com.mfg.askfm.controllers;

import com.mfg.askfm.dtos.PostCreateDto;
import com.mfg.askfm.dtos.PostGetResponse;
import com.mfg.askfm.dtos.PostUpdateDto;
import com.mfg.askfm.entities.Post;
import com.mfg.askfm.services.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<PostGetResponse> getAllPosts(@RequestParam Optional<Long> userId) {
        return postService.getAllPosts(userId);
    }

    @PostMapping
    public Post createPost(@RequestBody PostCreateDto postCreateDto) {
        return postService.createPost(postCreateDto);
    }

    @GetMapping("/{postId}")
    public Post getPostById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }

    @PostMapping("/{postId}")
    public PostGetResponse getPostByIdWithLikes(@PathVariable Long postId) {
        return postService.getPostByIdWithLikes(postId);
    }

    @PutMapping("/{postId}")
    public Post updatePostById(@PathVariable Long postId, @RequestBody PostUpdateDto postUpdateDto) {
        return postService.updatePostById(postId, postUpdateDto);
    }

    @DeleteMapping("/{postId}")
    public void deletePostById(@PathVariable Long postId) {
        postService.deletePostById(postId);
    }

    // Testing Admin Only Request
    @GetMapping("/admin_only")
    public ResponseEntity<String> adminOnly() {
        return ResponseEntity.ok("It's working! Welcome back ADMIN <3");
    }
}
