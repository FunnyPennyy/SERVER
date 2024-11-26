package com.penny.penny_backend.dto;

import com.penny.penny_backend.domain.TaxUsage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TaxUsageResponse {

    private Long id;
    private LocalDate usageDate;
    private String usageDetail;
    private int usageAmount;

    public TaxUsageResponse(TaxUsage taxUsage) {
        this.id = taxUsage.getId();
        this.usageDate = taxUsage.getUsageDate();
        this.usageDetail = taxUsage.getUsageDetail();
        this.usageAmount = taxUsage.getUsageAmount();
    }
}
