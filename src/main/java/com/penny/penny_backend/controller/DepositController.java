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

    @GetMapping("/deposits")
    public ResponseEntity<List<DepositResponse>> getAllDeposits() {
        return ResponseEntity.ok(depositService.getAllDeposits());
    }

    @PostMapping("/deposits")
    public ResponseEntity<DepositResponse> createDeposit(@RequestBody DepositRequest depositRequest) {
        return ResponseEntity.ok(depositService.createDeposit(depositRequest));
    }

    @PostMapping("/deposits/{id}/terminate")
    public ResponseEntity<String> terminateDeposit(@PathVariable Long id, @RequestParam Long studentId) { // userId → studentId
        return ResponseEntity.ok(depositService.terminateDeposit(id, studentId));
    }

    @GetMapping("/deposits/types")
    public ResponseEntity<List<DepositTypeResponse>> getAllDepositTypes() {
        return ResponseEntity.ok(depositService.getAllDepositTypes());
    }

    @PostMapping("/deposits/types")
    public ResponseEntity<DepositTypeResponse> createDepositType(@RequestBody DepositTypeRequest depositTypeRequest) {
        return ResponseEntity.ok(depositService.createDepositType(depositTypeRequest));
    }


}
