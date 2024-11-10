package com.penny.penny_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdatePostRequest {
    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdAt;

}