package com.penny.penny_backend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@Entity
@AllArgsConstructor
public class Tax {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 세금 ID
    private Long classId;  // 학급 ID (FK)
    private Integer currentAmount;  // 세금 현황 (현재 금액)

    @OneToMany(mappedBy = "tax", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaxUsage> taxUsages;  // 세금 사용 내역 (1:N)

    public Tax() {

    }

    // 현재 금액 업데이트를 위한 Setter 메서드
    public void setCurrentAmount(int currentAmount) {
        this.currentAmount = currentAmount;
    }
}