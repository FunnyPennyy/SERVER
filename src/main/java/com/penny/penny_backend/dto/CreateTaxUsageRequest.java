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

    private Long taxId;
    private String usageDetail;
    private int usageAmount;
    private Long recorderTeacherId; // 기록한 선생님 ID
    private Long recorderStudentId; // 기록한 학생 ID

    /*public TaxUsage toEntity() {
        return TaxUsage.builder()
                .usageDate(usageDate)
                .usageDetail(usageDetail)
                .usageAmount(usageAmount)
                .recordedByTeacher(recordedByTeacher)
                .recordedByTeacher(recordedByTeacher)
                .build();
    }*/
}
