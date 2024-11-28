package com.penny.penny_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeacherAccountRequestDTO {
    private Long teacherId; // 선생님 ID
    private String nickname; // 계좌 별칭
}