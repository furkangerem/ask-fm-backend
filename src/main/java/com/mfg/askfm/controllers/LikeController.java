package com.mfg.askfm.controllers;

import com.mfg.askfm.dtos.LikeCreateDto;
import com.mfg.askfm.dtos.LikeGetResponse;
import com.mfg.askfm.entities.Like;
import com.mfg.askfm.services.LikeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/likes")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping
    public List<LikeGetResponse> getAllLikes(@RequestParam Optional<Long> postId, @RequestParam Optional<Long> userId) {
        return likeService.getAllLikes(postId, userId);
    }

    @PostMapping
    public Like createLike(@RequestBody LikeCreateDto likeCreateDto) {
        return likeService.createLike(likeCreateDto);
    }

    @GetMapping("/{likeId}")
    public Like getLikeById(@PathVariable Long likeId) {
        return likeService.getLikeById(likeId);
    }

    @DeleteMapping("/{likeId}")
    public void deleteLikeById(@PathVariable Long likeId) {
        likeService.deleteLikeById(likeId);
    }
}
