package com.penny.penny_backend.dto;

import com.penny.penny_backend.domain.TaxUsage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateTaxUsageRequest {

    private LocalDate usageDate;
    private String usageDetail;
    private int usageAmount;
    private Long recordedBy; // 기록한 사람의 ID (Teacher or 국세청 학생)

    public TaxUsage toEntity() {
        return TaxUsage.builder()
                .usageDate(usageDate)
                .usageDetail(usageDetail)
                .usageAmount(usageAmount)
                .recordedBy(recordedBy)
                .build();
    }
}
