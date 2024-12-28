package com.penny.penny_backend.service;

import com.penny.penny_backend.domain.*;
import com.penny.penny_backend.dto.CreateHomeworkRequest;
import com.penny.penny_backend.dto.HomeworkStatusResponse;
import com.penny.penny_backend.dto.UpdateHomeworkStatusRequest;
import com.penny.penny_backend.repository.HomeworkRepository;
import com.penny.penny_backend.repository.HomeworkStatusRepository;
import com.penny.penny_backend.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeworkService {

    private final HomeworkRepository homeworkRepository;
    private final HomeworkStatusRepository homeworkStatusRepository;
    private final StudentRepository studentRepository;

    // 숙제 생성
    public Homework createHomework(CreateHomeworkRequest request) {
        Classroom classroom = Classroom.builder()
                .classroomId(request.getClassroomId())
                .build();

        Homework homework = Homework.builder()
                .classroom(classroom)
                .content(request.getContent())
                .build();

        // 숙제 저장
        Homework savedHomework = homeworkRepository.save(homework);

        // 해당 Classroom의 모든 학생 조회
        List<Student> students = studentRepository.findByClassroom_ClassroomId(request.getClassroomId());

        // 각 학생에 대해 HomeworkStatus 생성
        students.forEach(student -> {
            HomeworkStatus homeworkStatus = HomeworkStatus.builder()
                    .homework(savedHomework)
                    .student(student)
                    .completionStatus(HomeworkStatus.Status.PENDING)
                    .build();
            homeworkStatusRepository.save(homeworkStatus);
        });

        return savedHomework;
    }

    // 특정 학생의 숙제 상태 조회
    public HomeworkStatusResponse getHomeworkStatus(Long studentId, Long homeworkId) {
        HomeworkStatus homeworkStatus = homeworkStatusRepository.findByStudentIdAndHomeworkId(studentId, homeworkId)
                .orElseThrow(() -> new IllegalArgumentException("HomeworkStatus not found"));

        return new HomeworkStatusResponse(homeworkStatus);
    }

    // 숙제 상태 수정
    public HomeworkStatus updateHomeworkStatus(Long homeworkStatusId, HomeworkStatus.Status newStatus) {
        HomeworkStatus homeworkStatus = homeworkStatusRepository.findById(homeworkStatusId)
                .orElseThrow(() -> new IllegalArgumentException("HomeworkStatus not found"));

        homeworkStatus.setCompletionStatus(newStatus);
        return homeworkStatusRepository.save(homeworkStatus);
    }
}
