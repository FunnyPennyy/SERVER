package com.penny.penny_backend.controller;

import com.penny.penny_backend.domain.Tax;
import com.penny.penny_backend.domain.TaxUsage;
import com.penny.penny_backend.dto.CreateTaxUsageRequest;
import com.penny.penny_backend.dto.TaxUsageResponse;
import com.penny.penny_backend.service.TaxService;
import com.penny.penny_backend.service.TaxUsageService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class TaxController {

    //@Autowired
    private final TaxService taxService;

    //@Autowired
    private final TaxUsageService taxUsageService;

    // 특정 학급의 세금 조회
    @GetMapping("/tax/{classroomId}")
    public ResponseEntity<Tax> getTaxByClassroomId(@PathVariable("classroomId") Long classroomId) {
        Tax tax = taxService.getTaxByClassroomId(classroomId);

        return ResponseEntity.ok(tax);
    }

    // 세금 사용 내역 목록 조회
    @GetMapping("/taxusage")
    public ResponseEntity<List<TaxUsageResponse>> getAllTaxUsage() {
        List<TaxUsageResponse> taxUsages = taxUsageService.getAllTaxUsage().stream()
                .map(TaxUsageResponse::new) // TaxUsage -> TaxUsageResponse 변환
                .collect(Collectors.toList());
        return ResponseEntity.ok(taxUsages);
    }

    // 세금 사용 내역 생성
    //@PreAuthorize("hasRole('TEACHER') or (hasRole('STUDENT') and principal.job == '국세청')")
    @PostMapping("/taxusage")
    public ResponseEntity<TaxUsage> createTaxUsage(@RequestBody CreateTaxUsageRequest request) {
        TaxUsage createdTaxUsage = taxUsageService.createTaxUsage(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTaxUsage);
    }

}
