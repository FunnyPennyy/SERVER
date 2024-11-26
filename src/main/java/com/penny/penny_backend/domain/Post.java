package com.penny.penny_backend.domain;

//import java.util.*;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 게시글 ID
    private String title;  // 게시글 제목
    private String author;  // 게시글 작성자 = 다 선생님 아닌가?  +후에 fk로 변경
    private String content;  // 게시글 내용

    @CreationTimestamp
    private LocalDateTime createdAt; // 게시글 생성일자

    @Builder
    public Post(Long id, String title, String content, String author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }


}