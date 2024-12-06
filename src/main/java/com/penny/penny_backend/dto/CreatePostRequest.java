package com.penny.penny_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.penny.penny_backend.domain.Post;
import com.penny.penny_backend.domain.Teacher;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreatePostRequest {
    private String title;
    private String content;
    private Long authorId; // 작성자 (Teacher ID)

    public Post toEntity(Teacher author) {
        return Post.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}