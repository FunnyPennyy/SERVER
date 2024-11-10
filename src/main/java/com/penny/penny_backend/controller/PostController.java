package com.penny.penny_backend.controller;

import com.penny.penny_backend.domain.Post;
import com.penny.penny_backend.dto.CreatePostRequest;
import com.penny.penny_backend.dto.UpdatePostRequest;
import com.penny.penny_backend.dto.PostResponse;
import com.penny.penny_backend.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
// @RequestMapping("/post")
public class PostController {

    @Autowired
    private final PostService postService;

    // 게시글 생성
    @PreAuthorize("hasRole('TEACHER')") // 선생님만
    @PostMapping("/post")
    public ResponseEntity<Post> createPost(@RequestBody CreatePostRequest request) {
        Post createdPost = postService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdPost);
    }

    // 게시글 수정
    @PreAuthorize("hasRole('TEACHER')") // 선생님만
    @PutMapping("/post/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody UpdatePostRequest request) {
        Post updatedPost = postService.update(id, request);
        return ResponseEntity.ok()
                .body(updatedPost);
    }

    // 게시글 조회 (단일 조회)
    @GetMapping("/post/{id}")
    public ResponseEntity<PostResponse> findPost(@PathVariable Long id) {
        Post post = postService.getPost(id);
        return ResponseEntity.ok(new PostResponse(post));
    }

    // 모든 게시글 조회 (옵션)
    @GetMapping("/post")
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        List<PostResponse> posts = postService.getAllPosts().stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(posts);
    }

    // 게시글 삭제
    @PreAuthorize("hasRole('TEACHER')") // 선생님만
    @DeleteMapping("/post/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok()
                .build();
    }

}
