package com.penny.penny_backend.controller;

import com.penny.penny_backend.domain.Account;
import com.penny.penny_backend.domain.AccountHistory;
import com.penny.penny_backend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // 계좌 생성
    @PostMapping("/create")
    public ResponseEntity<Account> createAccount(@RequestBody Long studentId,
                                                 @RequestBody String nickname,
                                                 @RequestBody int initialAmount,
                                                 @RequestBody String accountNum) {
        System.out.println("studentId = " + studentId);
        try {
            System.out.println("studentId = " + studentId);
            Account account = accountService.createAccount(studentId, nickname, initialAmount, accountNum);
            System.out.println("accout_created\n");
            return new ResponseEntity<>(account, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // 계좌 조회
    @GetMapping("/{studentId}")
    public ResponseEntity<Account> getAccountByStudentId(@PathVariable Long studentId) {
        Optional<Account> account = accountService.getAccountByStudentId(studentId);
        return account.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 계좌 사용 내역 조회
    @GetMapping("/{studentId}/history")
    public ResponseEntity<List<AccountHistory>> getAccountHistoryByStudentId(@PathVariable Long studentId) {
        List<AccountHistory> history = accountService.getAccountHistoryByStudentId(studentId);
        return new ResponseEntity<>(history, HttpStatus.OK);
    }

    // 학생 간 이체
    @PostMapping("/transfer/student")
    public ResponseEntity<String> transferToStudent(@RequestParam String fromAccountNum,
                                                    @RequestParam String toAccountNum,
                                                    @RequestParam int amount) {
        try {
            accountService.transferToStudent(fromAccountNum, toAccountNum, amount);
            return new ResponseEntity<>("Transfer to student successful", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 학생이 선생님에게 이체
    @PostMapping("/transfer/teacher")
    public ResponseEntity<String> transferToTeacher(@RequestParam String fromAccountNum,
                                                    @RequestParam String toAccountNum,
                                                    @RequestParam int amount) {
        try {
            accountService.transferToTeacher(fromAccountNum, toAccountNum, amount);
            return new ResponseEntity<>("Transfer to teacher successful", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

//    // 계좌 유형 확인
//    @GetMapping("/isStudentAccount")
//    public ResponseEntity<Boolean> isStudentAccount(@RequestParam String accountNum) {
//        boolean isStudentAccount = accountService.isStudentAccount(accountNum);
//        return new ResponseEntity<>(isStudentAccount, HttpStatus.OK);
//    }
}