package com.penny.penny_backend.service;

import com.penny.penny_backend.domain.Tax;
import com.penny.penny_backend.repository.TaxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TaxService {

    private final TaxRepository taxRepository;

    // 단일 Tax 의 현재 금액을 조회
    public int getCurrentTaxAmount() {
        Tax tax = taxRepository.findFirstByOrderById(); // 특정 Tax 레코드를 가져옴
        return tax.getCurrentAmount();
    }
}
