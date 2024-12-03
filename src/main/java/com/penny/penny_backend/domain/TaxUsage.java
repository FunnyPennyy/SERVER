package com.penny.penny_backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JoinColumn(name = "tax_id", nullable = false) // Tax FK
    private Tax tax;  // 세금 (FK)

    private LocalDate usageDate;  // 사용 날짜
    private String usageDetail;  // 사용 내역 (어디에 썼는지)
    private Integer usageAmount;  // 사용 금액 (얼마나 썼는지)

    // Teacher 반환 메서드
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recorder_teacher_id", nullable = true) // 기록자(선생님 ID) FK
    @JsonIgnore // Teacher 정보를 제외
    private Teacher recorderTeacher;

    // Student 반환 메서드
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recorder_student_id", nullable = true) // 기록자(학생 ID) FK
    @JsonIgnore // Student 정보를 제외
    private Student recorderStudent;

    // 기록자(Teacher 또는 Student) 설정 메서드
    public void setRecorder(Teacher teacher, Student student) {
        this.recorderTeacher = teacher;
        this.recorderStudent = student;
    }

    // Tax ID 반환 메서드 추가
    public Long getTaxId() {
        return tax != null ? tax.getId() : null;
    }

}