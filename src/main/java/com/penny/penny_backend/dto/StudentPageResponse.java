package com.penny.penny_backend.dto;

import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StudentPageResponse {
    private Long id;
    private String username;
    //private String classroom;
    private Integer credit;
}
