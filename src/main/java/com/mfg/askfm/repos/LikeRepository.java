package com.mfg.askfm.repos;

import com.mfg.askfm.entities.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findByPostIdAndUserId(Long postId, Long userId);

    List<Like> findByPostId(Long postId);

    List<Like> findByUserId(Long userId);

    @Query(value = "SELECT 'liked', LKS.POST_ID, USR.USER_NAME FROM " +
            "LIKES LKS LEFT JOIN USERS USR ON USR.ID = LKS.USER_ID " +
            "WHERE LKS.POST_ID IN :postIdList LIMIT 6", nativeQuery = true)
    List<Object> findLikesByPostId(@Param("postIdList") List<Long> postIdList);
}
