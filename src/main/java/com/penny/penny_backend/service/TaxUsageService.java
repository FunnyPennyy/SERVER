package com.penny.penny_backend.service;

import com.penny.penny_backend.domain.Student;
import com.penny.penny_backend.domain.Tax;
import com.penny.penny_backend.domain.TaxUsage;
import com.penny.penny_backend.domain.Teacher;
import com.penny.penny_backend.dto.CreateTaxUsageRequest;
import com.penny.penny_backend.repository.StudentRepository;
import com.penny.penny_backend.repository.TaxRepository;
import com.penny.penny_backend.repository.TaxUsageRepository;
import com.penny.penny_backend.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TaxUsageService {

    private final TaxUsageRepository taxUsageRepository;
    private final TaxRepository taxRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    // 모든 세금 사용 내역 조회
    public List<TaxUsage> getAllTaxUsage() {
        return taxUsageRepository.findAll();
    }

    // 새로운 세금 사용 내역 생성 및 Tax 현재 금액 갱신
    @Transactional
    public TaxUsage createTaxUsage(CreateTaxUsageRequest request) {
        // Tax 조회
        Tax tax = taxRepository.findById(request.getTaxId()) // 요청에 포함된 taxId로 세금 조회
                .orElseThrow(() -> new RuntimeException("Tax not found with ID: " + request.getTaxId()));

        // 사용 내역에 따라 세금 현황을 업데이트
        int updatedAmount = tax.getCurrentAmount() - request.getUsageAmount();
        tax.setCurrentAmount(updatedAmount);
        taxRepository.save(tax);

        // 기록자 조회
        Teacher teacher = null;
        Student student = null;

        if (request.getRecorderTeacherId() != null) {
            teacher = teacherRepository.findById(request.getRecorderTeacherId())
                    .orElseThrow(() -> new RuntimeException("Teacher not found with ID: " + request.getRecorderTeacherId()));
        }

        if (request.getRecorderStudentId() != null) {
            student = studentRepository.findById(request.getRecorderStudentId())
                    .orElseThrow(() -> new RuntimeException("Student not found with ID: " + request.getRecorderStudentId()));
        }

        LocalDate usageDate = LocalDate.now();

        // Builder 를 사용하여 TaxUsage 객체 생성
        TaxUsage taxUsage = TaxUsage.builder()
                .tax(tax)
                .usageDate(LocalDate.now())
                .usageDetail(request.getUsageDetail())
                .usageAmount(request.getUsageAmount())
                .recorderTeacher(teacher) // 기록자(Teacher 설정)
                .recorderStudent(student) // 기록자(Student 설정)
                .build();

        taxUsageRepository.save(taxUsage);
        return taxUsageRepository.save(taxUsage);
    }
}

