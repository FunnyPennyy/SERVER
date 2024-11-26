package com.penny.penny_backend.controller;

import com.penny.penny_backend.domain.Account;
import com.penny.penny_backend.domain.AccountHistory;
import com.penny.penny_backend.dto.AccountDTO;
import com.penny.penny_backend.dto.AccountHistoryDTO;
import com.penny.penny_backend.dto.TransferRequestDTO;
import com.penny.penny_backend.service.AccountService;
import com.penny.penny_backend.service.TeacherAccountService;
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
    private final TeacherAccountService teacherAccountService;

    @Autowired
    public AccountController(AccountService accountService, TeacherAccountService teacherAccountService) {
        this.accountService = accountService;
        this.teacherAccountService = teacherAccountService;
    }

    // 계좌 생성
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Long studentId,
                                                 @RequestBody String nickname) {
        try {
            System.out.println("studentId = " + studentId);
            Account account = accountService.createAccount(studentId, nickname);
            System.out.println("accout_created\n");
            return new ResponseEntity<>(account, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // 계좌 조회
    @GetMapping("/{studentId}")
    public ResponseEntity<AccountDTO> getAccountByStudentId(@PathVariable Long studentId) {
        Optional<Account> account = accountService.getAccountByStudentId(studentId);
        return account.map(value -> {
            // 엔티티를 DTO로 변환
            AccountDTO accountDTO = new AccountDTO(
                    value.getStudentId(),
                    value.getNickname(),
                    value.getAmount()
            );
            return new ResponseEntity<>(accountDTO, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 계좌 사용 내역 조회
    @GetMapping("/{studentId}/history")
    public ResponseEntity<List<AccountHistoryDTO>> getAccountHistoryByStudentId(@PathVariable Long studentId) {
        List<AccountHistory> historyList = accountService.getAccountHistoryByStudentId(studentId);

        // 엔티티 리스트를 DTO 리스트로 변환
        List<AccountHistoryDTO> historyDTOList = historyList.stream()
                .map(history -> new AccountHistoryDTO(
                        history.getContent(),
                        history.getAmount(),
                        history.isInOrOut(),
                        history.getDatetime()
                )).toList();

        return new ResponseEntity<>(historyDTOList, HttpStatus.OK);
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferRequestDTO transferRequestDTO) {
        try {
            String fromAccountNum = transferRequestDTO.getFromAccountNum();
            String toAccountNum = transferRequestDTO.getToAccountNum();
            int amount = transferRequestDTO.getAmount();

            if (accountService.isStudentAccount(toAccountNum)) {
                accountService.transferToStudent(fromAccountNum, toAccountNum, amount);
                return new ResponseEntity<>("Transfer to student successful", HttpStatus.OK);
            } else if (teacherAccountService.isTeacherAccount(toAccountNum)) {
                accountService.transferToTeacher(fromAccountNum, toAccountNum, amount);
                return new ResponseEntity<>("Transfer to teacher successful", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Recipient account not found", HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long studentId) {
        try {
            accountService.deleteAccount(studentId);
            return new ResponseEntity<>("계좌가 삭제되었습니다.", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}