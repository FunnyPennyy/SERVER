package com.penny.penny_backend.domain;

import lombok.*;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class TaxUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 세금 사용 내역 고유 ID

    @ManyToOne(fetch = FetchType.LAZY)
    private Tax tax;  // 세금 (FK)
    private LocalDate usageDate;  // 사용 날짜
    private String usageDetail;  // 사용 내역 (어디에 썼는지)
    private Integer usageAmount;  // 사용 금액 (얼마나 썼는지)
    private Long recordedBy;  // 해당 사용 내역을 기록한 사람 (관리용)

    public TaxUsage(Tax tax, LocalDate usageDate, String usageDetail, int usageAmount, Long recordedBy) {
        this.tax = tax;
        this.usageDate = usageDate;
        this.usageDetail = usageDetail;
        this.usageAmount = usageAmount;
        this.recordedBy = recordedBy;
    }
}