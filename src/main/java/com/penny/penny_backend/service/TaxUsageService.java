package com.penny.penny_backend.service;

import com.penny.penny_backend.domain.Tax;
import com.penny.penny_backend.domain.TaxUsage;
import com.penny.penny_backend.dto.CreateTaxUsageRequest;
import com.penny.penny_backend.repository.TaxRepository;
import com.penny.penny_backend.repository.TaxUsageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TaxUsageService {

    private final TaxUsageRepository taxUsageRepository;
    private final TaxRepository taxRepository;

    // 모든 세금 사용 내역 조회
    public List<TaxUsage> getAllTaxUsage() {
        return taxUsageRepository.findAll();
    }

    // 새로운 세금 사용 내역 생성 및 Tax 현재 금액 갱신
    @Transactional
    public TaxUsage createTaxUsage(CreateTaxUsageRequest request) {
        Tax tax = taxRepository.findFirstByOrderById(); // 특정 Tax 레코드를 가져옴

        // 사용 내역에 따라 세금 현황을 업데이트
        int updatedAmount = tax.getCurrentAmount() - request.getUsageAmount();
        tax.setCurrentAmount(updatedAmount);
        taxRepository.save(tax);

        // **Builder 를 사용하여 TaxUsage 객체 생성**
        TaxUsage taxUsage = TaxUsage.builder()
                .tax(tax)
                .usageDate(request.getUsageDate())
                .usageDetail(request.getUsageDetail())
                .usageAmount(request.getUsageAmount())
                .recordedBy(request.getRecordedBy())
                .build();

        return taxUsageRepository.save(taxUsage);
    }
}

