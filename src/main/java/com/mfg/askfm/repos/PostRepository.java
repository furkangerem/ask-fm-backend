package com.mfg.askfm.repos;

import com.mfg.askfm.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUserId(Long userId);

    @Query(value = "SELECT ID FROM POST WHERE USER_ID = :userId ORDER BY CREATION_DATE DESC LIMIT 6", nativeQuery = true)
    List<Long> findLatestPostByUserId(@Param("userId") Long userId);
}
