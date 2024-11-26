package com.penny.penny_backend.service;

import com.penny.penny_backend.domain.HomeworkStatus;
import com.penny.penny_backend.repository.HomeworkStatusRepository;

import java.util.List;

public class HomeworkServiceImpl implements HomeworkService {
    private final HomeworkStatusRepository homeworkStatusRepository;
    private final StudentRepository studentRepository;

    public HomeworkServiceImpl (HomeworkStatusRepository homeworkStatusRepository, StudentRepository studentRepository) {
        this.homeworkStatusRepository = homeworkStatusRepository;
        this.studentRepository = studentRepository;
    }
//
    @Override
    public List<HomeworkStatus> getHomeworkStatusByClassId(Long classId) {
        // 학급 ID로 학생 ID들을 조회한 후, 해당 학생들의 숙제 상태 조회
        List<Long> studentIds = studentRepository.findStudentIdsByClassId(classId);
        return homeworkStatusRepository.findByStudentIdIn(studentIds);
    }

    @Override
    public void updateHomeworkStatusByClassId(Long classId, Long homeworkId, boolean status) {
        // 학급 ID로 학생 ID들을 조회한 후, 해당 학생들의 숙제 상태를 수정
        List<Long> studentIds = studentRepository.findStudentIdsByClassId(classId);
        List<HomeworkStatus> homeworkStatuses = homeworkStatusRepository.findByStudentIdInAndHomeworkId(studentIds, homeworkId);

        for (HomeworkStatus homeworkStatus : homeworkStatuses) {
            homeworkStatus.setStatus(status);
        }

        homeworkStatusRepository.saveAll(homeworkStatuses);
    }
}
