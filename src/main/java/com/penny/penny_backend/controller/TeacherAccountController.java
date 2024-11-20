//package com.penny.penny_backend.controller;
//
//import com.penny.penny_backend.domain.TeacherAccount;
//import com.penny.penny_backend.domain.TeacherAccountHistory;
//import com.penny.penny_backend.service.TeacherAccountService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/teacherAccounts")
//public class TeacherAccountController {
//
//    private final TeacherAccountService teacherAccountService;
//
//    @Autowired
//    public TeacherAccountController(TeacherAccountService teacherAccountService) {
//        this.teacherAccountService = teacherAccountService;
//    }
//
//    // 계좌 생성
//    @PostMapping("/create")
//    public ResponseEntity<TeacherAccount> createAccount(@RequestParam Long teacherId,
//                                                        @RequestParam String nickname,
//                                                        @RequestParam int initialAmount,
//                                                        @RequestParam String accountNum) {
//        try {
//            TeacherAccount teacherAccount = teacherAccountService.createAccount(teacherId, nickname, initialAmount, accountNum);
//            return new ResponseEntity<>(teacherAccount, HttpStatus.CREATED);
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    // 계좌 조회
//    @GetMapping("/{teacherId}")
//    public ResponseEntity<TeacherAccount> getAccountByTeacherId(@PathVariable Long teacherId) {
//        Optional<TeacherAccount> teacherAccount = teacherAccountService.getAccountByTeacherId(teacherId);
//        return teacherAccount.map(account -> new ResponseEntity<>(account, HttpStatus.OK))
//                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
//
//    // 계좌 사용 내역 조회
//    @GetMapping("/{teacherId}/history")
//    public ResponseEntity<List<TeacherAccountHistory>> getAccountHistoryByTeacherId(@PathVariable Long teacherId) {
//        List<TeacherAccountHistory> history = teacherAccountService.getAccountHistoryByTeacherId(teacherId);
//        return new ResponseEntity<>(history, HttpStatus.OK);
//    }
//
//    // 학생 월급 지급
////    @PostMapping("/{teacherId}/paySalary")
////    public ResponseEntity<String> paySalaryToStudents(@PathVariable Long teacherId) {
////        try {
////            teacherAccountService.paySalaryToStudents(teacherId);
////            return new ResponseEntity<>("월급 지급이 완료되었습니다.", HttpStatus.OK);
////        } catch (IllegalArgumentException e) {
////            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
////        }
//    }
//
////    // 학생으로부터 입금받기
////    @PostMapping("/receive")
////    public ResponseEntity<String> receiveFromStudent(@RequestParam String teacherAccountNum,
////                                                     @RequestParam String studentAccountNum,
////                                                     @RequestParam int amount) {
////        try {
////            Optional<TeacherAccount> teacherAccountOpt = teacherAccountService.isTeacherAccount(teacherAccountNum)
////                    ? teacherAccountService.getAccountByTeacherId(Long.parseLong(teacherAccountNum))
////                    : Optional.empty();
////
////            if (teacherAccountOpt.isEmpty()) {
////                return new ResponseEntity<>("선생님 계좌가 존재하지 않습니다.", HttpStatus.NOT_FOUND);
////            }
////
////            TeacherAccount teacherAccount = teacherAccountOpt.get();
////            teacherAccountService.receiveFromStudent(teacherAccount, amount);
////            return new ResponseEntity<>("입금 처리가 완료되었습니다.", HttpStatus.OK);
////        } catch (IllegalArgumentException e) {
////            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
////        }
////    }
//
////    // 계좌 유형 확인
////    @GetMapping("/isTeacherAccount")
////    public ResponseEntity<Boolean> isTeacherAccount(@RequestParam String accountNum) {
////        boolean isTeacherAccount = teacherAccountService.isTeacherAccount(accountNum);
////        return new ResponseEntity<>(isTeacherAccount, HttpStatus.OK);
////    }
//}