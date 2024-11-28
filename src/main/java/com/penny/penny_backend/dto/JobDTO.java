package com.penny.penny_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JobDTO {
    private Long jobId;           // 직업 ID
    private String name;          // 직업 이름
    private String jobDescription; // 직업 설명
    private int salary;           // 월급
}