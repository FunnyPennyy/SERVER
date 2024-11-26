package com.penny.penny_backend.controller;

import com.penny.penny_backend.domain.Account;
import com.penny.penny_backend.domain.TeacherAccount;
import com.penny.penny_backend.domain.TeacherAccountHistory;
import com.penny.penny_backend.dto.SalaryPaymentResponseDTO;
import com.penny.penny_backend.dto.TeacherAccountDTO;
import com.penny.penny_backend.dto.TeacherAccountHistoryDTO;
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
    public ResponseEntity<TeacherAccount> createAccount(@RequestBody Long teacherId,
                                                        @RequestBody String nickname) {
        try {
            TeacherAccount teacherAccount = teacherAccountService.createAccount(teacherId, nickname);
            return new ResponseEntity<>(teacherAccount, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // 계좌 조회
    @GetMapping("/{teacherId}")
    public ResponseEntity<TeacherAccountDTO> getAccountByTeacherId(@PathVariable Long teacherId) {
        Optional<TeacherAccount> teacherAccount = teacherAccountService.getAccountByTeacherId(teacherId);
        return teacherAccount.map(account -> {
            TeacherAccountDTO teacherAccountDTO = new TeacherAccountDTO(
                    account.getTeacherId(),
                    account.getNickname(),
                    account.getAmount()
            );
            return new ResponseEntity<>(teacherAccountDTO, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 계좌 사용 내역 조회
    @GetMapping("/{teacherId}/history")
    public ResponseEntity<List<TeacherAccountHistoryDTO>> getAccountHistoryByTeacherId(@PathVariable Long teacherId) {
        List<TeacherAccountHistory> historyList = teacherAccountService.getAccountHistoryByTeacherId(teacherId);

        List<TeacherAccountHistoryDTO> historyDTOList = historyList.stream()
                .map(history -> new TeacherAccountHistoryDTO(
                        history.getContent(),
                        history.getAmount(),
                        history.isInOrOut(),
                        history.getDatetime()
                )).toList();

        return new ResponseEntity<>(historyDTOList, HttpStatus.OK);
    }

     // 학생 월급 지급
     @PostMapping("/{teacherId}/payments")
     public ResponseEntity<SalaryPaymentResponseDTO> paySalaryToStudents(@PathVariable Long teacherId) {
         try {
             teacherAccountService.paySalaryToStudents(teacherId);
             SalaryPaymentResponseDTO response = new SalaryPaymentResponseDTO("월급 지급이 완료되었습니다.");
             return new ResponseEntity<>(response, HttpStatus.OK);
         } catch (IllegalArgumentException e) {
             SalaryPaymentResponseDTO response = new SalaryPaymentResponseDTO(e.getMessage());
             return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
         }
     }

    @DeleteMapping("/{teacherId}")
    public ResponseEntity<String> deleteTeacherAccount(@PathVariable Long teacherId) {
        try {
            teacherAccountService.deleteTeacherAccount(teacherId);
            return new ResponseEntity<>("선생님 계좌가 삭제되었습니다.", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
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