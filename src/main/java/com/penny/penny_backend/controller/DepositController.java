package com.penny.penny_backend.controller;


import com.penny.penny_backend.dto.DepositDto;
import com.penny.penny_backend.dto.DepositTypeDto;
import com.penny.penny_backend.service.DepositService;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class DepositController {
    private final DepositService depositService;

    public DepositController(DepositService depositService) {
        this.depositService = depositService;
    }

    @GetMapping("/deposits")
    public ResponseEntity<List<DepositDto>> getAllDeposits() {
        return ResponseEntity.ok(depositService.getAllDeposits());
    }

    @PostMapping("/deposits")
    public ResponseEntity<DepositDto> createDeposit(@RequestBody DepositDto depositDto) {
        return ResponseEntity.ok(depositService.createDeposit(depositDto));
    }

    @PostMapping("/deposits/{id}/terminate")
    public ResponseEntity<String> terminateDeposit(@PathVariable Long id, @RequestParam String userId) {
        return ResponseEntity.ok(depositService.terminateDeposit(id, userId));
    }

    @GetMapping("/deposits/types")
    public ResponseEntity<List<DepositTypeDto>> getAllDepositTypes() {
        return ResponseEntity.ok(depositService.getAllDepositTypes());
    }

    @PostMapping("/deposits/types")
    public ResponseEntity<DepositTypeDto> createDepositType(@RequestBody DepositTypeDto depositTypeDto) {
        return ResponseEntity.ok(depositService.createDepositType(depositTypeDto));
    }


}
