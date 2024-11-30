package com.penny.penny_backend.controller;

import com.penny.penny_backend.domain.Post;
import com.penny.penny_backend.dto.CreatePostRequest;
import com.penny.penny_backend.dto.UpdatePostRequest;
import com.penny.penny_backend.dto.PostResponse;
import com.penny.penny_backend.service.PostService;
import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
// @RequestMapping("/post")
public class PostController {

    private final PostService postService;

    // 게시글 생성 (선생님만)
    //@PreAuthorize("hasRole('TEACHER')") // 선생님만
    @PostMapping("/post")
    public ResponseEntity<PostResponse> createPost(@RequestBody CreatePostRequest request) {
        Post post = postService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new PostResponse(post));
    }

    // 게시글 수정 (선생님만)
//    @PreAuthorize("hasRole('TEACHER')") // 선생님만
    @PutMapping("/post/{id}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable Long id, @RequestBody UpdatePostRequest request) {
        Post post = postService.update(id, request);
        return ResponseEntity.ok(new PostResponse(post));
    }

    // 게시글 단일 조회 (선생님 학생 모두)
    @GetMapping("/post/{id}")
    public ResponseEntity<PostResponse> findPost(@PathVariable Long id) {
        Post post = postService.getPost(id);
        return ResponseEntity.ok(new PostResponse(post));
    }

    // 모든 게시글 조회 (선생님 학생 모두)
    @GetMapping("/post")
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        List<PostResponse> posts = postService.getAllPosts().stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(posts);
    }

    // 게시글 삭제 (선생님만)
//    @PreAuthorize("hasRole('TEACHER')") // 선생님만
    @DeleteMapping("/post/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok().build();
    }

}
