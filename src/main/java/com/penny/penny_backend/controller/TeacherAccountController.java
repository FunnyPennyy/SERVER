package com.penny.penny_backend.controller;

import com.penny.penny_backend.domain.Account;
import com.penny.penny_backend.domain.TeacherAccount;
import com.penny.penny_backend.domain.TeacherAccountHistory;
import com.penny.penny_backend.dto.*;
import com.penny.penny_backend.service.TeacherAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teacheraccounts")
public class TeacherAccountController {

    private final TeacherAccountService teacherAccountService;

    @Autowired
    public TeacherAccountController(TeacherAccountService teacherAccountService) {
        this.teacherAccountService = teacherAccountService;
    }

    // 계좌 생성
    @PostMapping
    public ResponseEntity<ApiResponseDTO<TeacherAccountDTO>> createAccount(@RequestBody TeacherAccountRequestDTO teacherAccountRequestDTO) {
        try {
            // RequestDTO에서 데이터 추출
            Long teacherId = teacherAccountRequestDTO.getTeacherId();
            String nickname = teacherAccountRequestDTO.getNickname();

            // teacherAccount 생성
            TeacherAccount teacherAccount = teacherAccountService.createAccount(teacherId, nickname);

            TeacherAccountDTO teacherAccountDTO = new TeacherAccountDTO(
                    teacherAccount.getTeacher().getTeacherId(),
                    teacherAccount.getTeacher().getTeacherName(),
                    teacherAccount.getAccountNum(),
                    teacherAccount.getNickname(),
                    teacherAccount.getAmount()
            );

            ApiResponseDTO<TeacherAccountDTO> response = new ApiResponseDTO<>(
                    "success",
                    "Account created successfully",
                    teacherAccountDTO
            );

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            ApiResponseDTO<TeacherAccountDTO> response = new ApiResponseDTO<>(
                    "error",
                    e.getMessage(),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // 계좌 조회
    @GetMapping("/{teacherId}")
    public ResponseEntity<ApiResponseDTO<TeacherAccountDTO>> getAccountByTeacherId(@PathVariable("teacherId") Long teacherId) {
        Optional<TeacherAccount> teacherAccount = teacherAccountService.getAccountByTeacherId(teacherId);

        return teacherAccount.map(value -> {
            TeacherAccountDTO teacherAccountDTO = new TeacherAccountDTO(
                    value.getTeacher().getTeacherId(),
                    value.getTeacher().getTeacherName(),
                    value.getAccountNum(),
                    value.getNickname(),
                    value.getAmount()
            );

            ApiResponseDTO<TeacherAccountDTO> response = new ApiResponseDTO<>(
                    "success",
                    "Account retrieved successfully",
                    teacherAccountDTO
            );

            return new ResponseEntity<>(response, HttpStatus.OK);
        }).orElseGet(() -> {
            ApiResponseDTO<TeacherAccountDTO> response = new ApiResponseDTO<>(
                    "error",
                    "Account not found",
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        });
    }

    // 계좌 사용 내역 조회
    @GetMapping("/{teacherId}/history")
    public ResponseEntity<ApiResponseDTO<List<TeacherAccountHistoryDTO>>> getAccountHistoryByTeacherId(@PathVariable("teacherId") Long teacherId) {
        try {
            List<TeacherAccountHistory> historyList = teacherAccountService.getAccountHistoryByTeacherId(teacherId);

            List<TeacherAccountHistoryDTO> historyDTOList = historyList.stream()
                    .map(history -> new TeacherAccountHistoryDTO(
                            history.getContent(),
                            history.getAmount(),
                            history.isInOrOut(),
                            history.getDatetime()
                    )).toList();

            ApiResponseDTO<List<TeacherAccountHistoryDTO>> response = new ApiResponseDTO<>(
                    "success",
                    "Account history retrieved successfully",
                    historyDTOList);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponseDTO<List<TeacherAccountHistoryDTO>> response = new ApiResponseDTO<>(
                    "error",
                    e.getMessage(),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

     // 학생 월급 지급
     @PostMapping("/{teacherId}/payments")
     public ResponseEntity<ApiResponseDTO<String>> paySalaryToStudents(@PathVariable("teacherId") Long teacherId) {
         try {
             teacherAccountService.paySalaryToStudents(teacherId);
             ApiResponseDTO<String> response = new ApiResponseDTO<>("success", "월급 지급이 완료되었습니다.", null);
             return new ResponseEntity<>(response, HttpStatus.OK);
         } catch (IllegalArgumentException e) {
             ApiResponseDTO<String> response = new ApiResponseDTO<>("error", e.getMessage(), null);
             return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
         }
     }

    @DeleteMapping("/{teacherId}")
    public ResponseEntity<ApiResponseDTO<String>> deleteTeacherAccount(@PathVariable("teacherId") Long teacherId) {
        try {
            teacherAccountService.deleteTeacherAccount(teacherId);
            ApiResponseDTO<String> response = new ApiResponseDTO<>("success", "Account deleted successfully", null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponseDTO<String> response = new ApiResponseDTO<>("error", e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // 학생으로부터 입금받기
//    @PostMapping("/receive")
//    public ResponseEntity<String> receiveFromStudent(@RequestParam String teacherAccountNum,
//                                                     @RequestParam String studentAccountNum,
//                                                     @RequestParam int amount) {
//        try {
//            Optional<TeacherAccount> teacherAccountOpt = teacherAccountService.isTeacherAccount(teacherAccountNum)
//                    ? teacherAccountService.getAccountByTeacherId(Long.parseLong(teacherAccountNum))
//                    : Optional.empty();
//
//            if (teacherAccountOpt.isEmpty()) {
//                return new ResponseEntity<>("선생님 계좌가 존재하지 않습니다.", HttpStatus.NOT_FOUND);
//            }
//
//            Optional<Account> studentAccountOpt = accountService.isStudentAccount(studentAccountNum)
//                    ? accountService.getAccountByStudentId(Long.parseLong(studentAccountNum))
//                    : Optional.empty();
//
//            if (studentAccountOpt.isEmpty()) {
//                return new ResponseEntity<>("학생 계좌가 존재하지 않습니다.", HttpStatus.NOT_FOUND);
//            }
//
//            TeacherAccount teacherAccount = teacherAccountOpt.get();
//            teacherAccountService.receiveFromStudent(teacherAccount, studentAccountNum, amount);
//            return new ResponseEntity<>("입금 처리가 완료되었습니다.", HttpStatus.OK);
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }

    // 계좌 유형 확인
//    @GetMapping("/isTeacherAccount")
//    public ResponseEntity<Boolean> isTeacherAccount(@RequestParam String accountNum) {
//        boolean isTeacherAccount = teacherAccountService.isTeacherAccount(accountNum);
//        return new ResponseEntity<>(isTeacherAccount, HttpStatus.OK);
//    }
}