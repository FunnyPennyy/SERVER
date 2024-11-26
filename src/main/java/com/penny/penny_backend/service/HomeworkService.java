package com.penny.penny_backend.service;

import com.penny.penny_backend.domain.HomeworkStatus;

import java.util.List;

public interface HomeworkService {
    // 학급에 있는 학생들의 숙제 상태 조회
    List<HomeworkStatus> getHomeworkStatusByClassId(Long classId);

    // 숙제 상태 수정
    void updateHomeworkStatusByClassId(Long classId, Long homeworkId, boolean status);
}
//