package com.mfg.askfm.services;

import com.mfg.askfm.dtos.LikeCreateDto;
import com.mfg.askfm.dtos.LikeGetResponse;
import com.mfg.askfm.entities.Like;
import com.mfg.askfm.entities.Post;
import com.mfg.askfm.entities.User;
import com.mfg.askfm.repos.LikeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserService userService;
    private final PostService postService;

    public LikeService(LikeRepository likeRepository, UserService userService, PostService postService) {
        this.likeRepository = likeRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public List<LikeGetResponse> getAllLikes(Optional<Long> postId, Optional<Long> userId) {

        List<Like> likeList;
        if (postId.isPresent() && userId.isPresent()) {
            likeList = likeRepository.findByPostIdAndUserId(postId.get(), userId.get());
        } else if (postId.isPresent())
            likeList = likeRepository.findByPostId(postId.get());
        else if (userId.isPresent())
            likeList = likeRepository.findByUserId(userId.get());
        else
            likeList = likeRepository.findAll();

        return likeList.stream().map(like -> new LikeGetResponse(like)).collect(Collectors.toList());
    }

    public Like createLike(LikeCreateDto likeCreateDto) {

        User user = userService.getUserById(likeCreateDto.getUserId());
        Post post = postService.getPostById(likeCreateDto.getPostId());
        if (user == null || post == null)
            return null;

        Like tempLike = new Like();
        tempLike.setPost(post);
        tempLike.setUser(user);
        return likeRepository.save(tempLike);
    }

    public Like getLikeById(Long likeId) {
        return likeRepository.findById(likeId).orElse(null);
    }

    public void deleteLikeById(Long likeId) {
        likeRepository.deleteById(likeId);
    }
}
