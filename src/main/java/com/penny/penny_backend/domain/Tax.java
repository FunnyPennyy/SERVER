package com.penny.penny_backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
public class Tax {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 세금 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id", nullable = false)
    @JsonIgnore // JSON 직렬화에서 제외
    private Classroom classroom; // 학급

    private Integer currentAmount;  // 세금 현황 (현재 금액)

    //@OneToMany(mappedBy = "tax", cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<TaxUsage> taxUsages;  // 세금 사용 내역 (1:N)


}