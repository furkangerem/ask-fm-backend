package com.mfg.askfm.repos;

import com.mfg.askfm.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostIdAndUserId(Long postId, Long userId);

    List<Comment> findByPostId(Long postId);

    List<Comment> findByUserId(Long userId);

    @Query(value = "SELECT 'commented on', CMT.POST_ID, USR.USER_NAME FROM COMMENT CMT LEFT JOIN USERS USR ON USR.ID = CMT.USER_ID " +
            "WHERE CMT.POST_ID IN :postIdList LIMIT 6", nativeQuery = true)
    List<Object> findCommentsByPostId(@Param("postIdList") List<Long> postIdList);
}
