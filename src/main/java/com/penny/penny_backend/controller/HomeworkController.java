package com.penny.penny_backend.controller;

import com.penny.penny_backend.domain.Homework;
import com.penny.penny_backend.domain.HomeworkStatus;
import com.penny.penny_backend.dto.*;
import com.penny.penny_backend.service.HomeworkService;
import com.penny.penny_backend.service.TaxService;
import com.penny.penny_backend.service.TaxUsageService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class HomeworkController {

    //@Autowired
    private final HomeworkService homeworkService;


    // 숙제 생성
    //@PreAuthorize("hasRole('TEACHER') or (hasRole('STUDENT') and principal.job == '통계청')")
    @PostMapping("/homework")
    public ResponseEntity<Homework> createHomework(@RequestBody CreateHomeworkRequest request) {
        Homework homework = homeworkService.createHomework(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(homework);
    }

    // 특정 학생의 숙제 상태 조회
    @GetMapping("/homeworkStatus/{studentId}/{homeworkId}")
    public ResponseEntity<HomeworkStatusResponse> getHomeworkStatus(
            @PathVariable Long studentId,
            @PathVariable Long homeworkId) {
        HomeworkStatusResponse response = homeworkService.getHomeworkStatus(studentId, homeworkId);
        return ResponseEntity.ok(response);
    }

    // 숙제 상태 수정
    @PutMapping("/homeworkStatus/{homeworkStatusId}")
    public ResponseEntity<HomeworkStatus> updateHomeworkStatus(
            @PathVariable("homeworkStatusId") Long homeworkStatusId,
            @RequestBody UpdateHomeworkStatusRequest request) {
        HomeworkStatus updatedStatus = homeworkService.updateHomeworkStatus(homeworkStatusId, request.getCompletionStatus());
        return ResponseEntity.ok(updatedStatus);
    }

}
