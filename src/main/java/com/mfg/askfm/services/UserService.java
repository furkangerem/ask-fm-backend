package com.mfg.askfm.services;

import com.mfg.askfm.entities.User;
import com.mfg.askfm.repos.CommentRepository;
import com.mfg.askfm.repos.LikeRepository;
import com.mfg.askfm.repos.PostRepository;
import com.mfg.askfm.repos.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    public UserService(UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository, LikeRepository likeRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.likeRepository = likeRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public User updateUserById(Long userId, User user) {

        Optional<User> foundUser = userRepository.findById(userId);
        if (foundUser.isPresent()) {
            User tempUser = foundUser.get();
            tempUser.setUserName(user.getUsername());
            tempUser.setPassword(user.getPassword());
            userRepository.save(tempUser);
            return tempUser;
        } else
            return null;
    }

    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

    public List<Object> getUserActivity(Long userId) {

        List<Long> postIdList = postRepository.findLatestPostByUserId(userId);
        if (postIdList.isEmpty())
            return null;

        List<Object> comments = commentRepository.findCommentsByPostId(postIdList);
        List<Object> likes = likeRepository.findLikesByPostId(postIdList);
        List<Object> result = new ArrayList<>();
        result.addAll(comments);
        result.addAll(likes);

        return result;
    }
}
