package com.penny.penny_backend.controller;


import com.penny.penny_backend.dto.DepositRequest;
import com.penny.penny_backend.dto.DepositResponse;
import com.penny.penny_backend.dto.DepositTypeRequest;
import com.penny.penny_backend.dto.DepositTypeResponse;
import com.penny.penny_backend.service.DepositService;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class DepositController {

    private final DepositService depositService;

    public DepositController(DepositService depositService) {
        this.depositService = depositService;
    }
    
    // 사용자 모든 예금 조회
    @GetMapping("/deposit")
    public ResponseEntity<List<DepositResponse>> getAllDeposits(
            @RequestParam(name = "ownerId", required = false) Long ownerId // 파라미터 이름 명시
    ) {
        if (ownerId != null) {
            return ResponseEntity.ok(depositService.getDepositsByOwner(ownerId)); // 특정 사용자 예금 조회
        } else {
            return ResponseEntity.ok(depositService.getAllDeposits()); // 모든 예금 조회
        }
    }
    
    // 예금 생성
    @PostMapping("/deposit")
    public ResponseEntity<DepositResponse> createDeposit(@RequestBody DepositRequest depositRequest) {
        return ResponseEntity.ok(depositService.createDeposit(depositRequest));
    }
    
    // 예금 해지
    @DeleteMapping("/deposit/{id}/terminate")
    public ResponseEntity<String> terminateDeposit(
        @PathVariable("id") Long id,
        @RequestParam(name = "studentId") Long studentId // 이름 명시
    ) {
        return ResponseEntity.ok(depositService.terminateDeposit(id, studentId));
    }
    
    // 모든 예금상품 조회
    @GetMapping("/depositType")
    public ResponseEntity<List<DepositTypeResponse>> getAllDepositTypes() {
        return ResponseEntity.ok(depositService.getAllDepositTypes());
    }
    
    // 예금상품 생성
    @PostMapping("/depositType")
    public ResponseEntity<DepositTypeResponse> createDepositType(@RequestBody DepositTypeRequest depositTypeRequest) {
        return ResponseEntity.ok(depositService.createDepositType(depositTypeRequest));
    }


}
