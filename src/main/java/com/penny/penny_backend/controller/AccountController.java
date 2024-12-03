package com.penny.penny_backend.controller;

import com.penny.penny_backend.domain.Account;
import com.penny.penny_backend.domain.AccountHistory;
import com.penny.penny_backend.dto.*;
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
    public ResponseEntity<ApiResponseDTO<AccountDTO>> createAccount(@RequestBody AccountRequestDTO accountRequestDTO) {
        try {
            // RequestDTO에서 데이터 추출
            Long studentId = accountRequestDTO.getStudentId();
            String nickname = accountRequestDTO.getNickname();

            // Account 생성
            Account account = accountService.createAccount(studentId, nickname);

            // 생성된 Account를 DTO로 변환하여 반환
            AccountDTO accountDTO = new AccountDTO(
                    account.getStudent().getStudentId(),
                    account.getStudent().getStudentName(),
                    account.getAccountNum(),
                    account.getNickname(),
                    account.getAmount()
            );

            ApiResponseDTO<AccountDTO> response = new ApiResponseDTO<>(
                    "success",
                    "Account created successfully",
                    accountDTO
            );

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            ApiResponseDTO<AccountDTO> response = new ApiResponseDTO<>(
                    "error",
                    e.getMessage(),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // 계좌 조회
    @GetMapping("/{studentId}")
    public ResponseEntity<ApiResponseDTO<AccountDTO>> getAccountByStudentId(@PathVariable("studentId") Long studentId) {
        Optional<Account> account = accountService.getAccountByStudentId(studentId);

        return account.map(value -> {
            // Entity -> DTO 변환
            AccountDTO accountDTO = new AccountDTO(
                    value.getStudent().getStudentId(),
                    value.getStudent().getStudentName(),
                    value.getAccountNum(),
                    value.getNickname(),
                    value.getAmount()
            );

            // DTO를 ApiResponse로 감싸서 반환
            ApiResponseDTO<AccountDTO> response = new ApiResponseDTO<>(
                    "success",
                    "Account retrieved successfully",
                    accountDTO
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        }).orElseGet(() -> {
            ApiResponseDTO<AccountDTO> response = new ApiResponseDTO<>(
                    "error",
                    "Account not found",
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        });
    }

    // 계좌 사용 내역 조회
    @GetMapping("/{studentId}/history")
    public ResponseEntity<ApiResponseDTO<List<AccountHistoryDTO>>> getAccountHistoryByStudentId(@PathVariable("studentId") Long studentId) {
        try {
            List<AccountHistory> historyList = accountService.getAccountHistoryByStudentId(studentId);

            // 엔티티 리스트를 DTO 리스트로 변환
            List<AccountHistoryDTO> historyDTOList = historyList.stream()
                    .map(history -> new AccountHistoryDTO(
                            history.getContent(),
                            history.getAmount(),
                            history.isInOrOut(),
                            history.getDatetime()
                    )).toList();

            ApiResponseDTO<List<AccountHistoryDTO>> response = new ApiResponseDTO<>(
                    "success",
                    "Account history retrieved successfully",
                    historyDTOList);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponseDTO<List<AccountHistoryDTO>> response = new ApiResponseDTO<>(
                    "error",
                    e.getMessage(),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // 계좌 이체
    @PostMapping("/transfer")
    public ResponseEntity<ApiResponseDTO<String>> transfer(@RequestBody TransferRequestDTO transferRequestDTO) {
        try {
            String fromAccountNum = transferRequestDTO.getFromAccountNum();
            String toAccountNum = transferRequestDTO.getToAccountNum();
            int amount = transferRequestDTO.getAmount();

            if (accountService.isStudentAccount(toAccountNum)) {
                accountService.transferToStudent(fromAccountNum, toAccountNum, amount);
                ApiResponseDTO<String> response = new ApiResponseDTO<>(
                        "success",
                        "Transfer to student successful",
                        null
                );
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else if (teacherAccountService.isTeacherAccount(toAccountNum)) {
                accountService.transferToTeacher(fromAccountNum, toAccountNum, amount);
                ApiResponseDTO<String> response = new ApiResponseDTO<>(
                        "success",
                        "Transfer to teacher successful",
                        null
                );
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                ApiResponseDTO<String> response = new ApiResponseDTO<>(
                        "error",
                        "Recipient account not found",
                        null
                );
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            ApiResponseDTO<String> response = new ApiResponseDTO<>(
                    "error",
                    e.getMessage(),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<ApiResponseDTO<String>> deleteAccount(@PathVariable("studentId") Long studentId) {
        try {
            accountService.deleteAccount(studentId);
            ApiResponseDTO<String> response = new ApiResponseDTO<>("success", "Account deleted successfully", null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponseDTO<String> response = new ApiResponseDTO<>("error", e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}