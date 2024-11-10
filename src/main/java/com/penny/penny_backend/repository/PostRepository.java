package com.penny.penny_backend.repository;

import com.penny.penny_backend.domain.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // 게시글을 최신순으로 조회
    @NonNull
    List<Post> findAll(@Nullable Sort sort);
}
