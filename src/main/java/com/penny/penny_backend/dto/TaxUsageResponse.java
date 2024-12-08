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
    private Long taxId;
    private LocalDate usageDate;
    private String usageDetail;
    private int usageAmount;
    private Long recorderTeacherId; // 기록한 선생님 ID
    private Long recorderStudentId; // 기록한 학생 ID

    public TaxUsageResponse(TaxUsage taxUsage) {
        this.id = taxUsage.getId();
        this.taxId = taxUsage.getTax().getId();
        this.usageDate = taxUsage.getUsageDate();
        this.usageDetail = taxUsage.getUsageDetail();
        this.usageAmount = taxUsage.getUsageAmount();

        // 기록자 Teacher ID 설정
        if (taxUsage.getRecorderTeacher() != null) {
            this.recorderTeacherId = taxUsage.getRecorderTeacher().getId();  // Teacher ID
        }

        // 기록자 Student ID 설정
        if (taxUsage.getRecorderStudent() != null) {
            this.recorderStudentId = taxUsage.getRecorderStudent().getId();  // Student ID
        }

    }
}
