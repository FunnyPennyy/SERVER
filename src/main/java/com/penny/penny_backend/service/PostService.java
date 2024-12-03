package com.penny.penny_backend.service;

import com.penny.penny_backend.domain.Post;
import com.penny.penny_backend.domain.Teacher;
import com.penny.penny_backend.dto.CreatePostRequest;
import com.penny.penny_backend.dto.UpdatePostRequest;
import com.penny.penny_backend.repository.PostRepository;
import com.penny.penny_backend.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final TeacherRepository teacherRepository;

    // 게시글 생성
    public Post createPost(CreatePostRequest request) {
        Teacher author = teacherRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found"));

        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .author(author)
                .build();

        return postRepository.save(post);
    }

    // 게시글 단일 조회
    public Post getPost(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    // 모든 게시글 조회 (최신순 정렬)
    public List<Post> getAllPosts() {
        return postRepository.findAll(Sort.by(Sort.Order.desc("createdAt")));
    }

    // 게시글 수정
    public Post update(Long id, UpdatePostRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found: " + id));

        post.update(request.getTitle(), request.getContent());

        return postRepository.save(post);
    }

    // 게시글 삭제
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
