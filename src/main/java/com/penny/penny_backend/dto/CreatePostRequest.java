package com.penny.penny_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.penny.penny_backend.domain.Post;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreatePostRequest {
    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdAt; // 게시글 생성일자

    public Post toEntity() {
        return Post.builder()
                .id(id)
                .title(title)
                .content(content)
                .createdAt(createdAt)
                .author(author)
                .build();
    }
}