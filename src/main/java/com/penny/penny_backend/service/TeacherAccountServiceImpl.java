package com.penny.penny_backend.service;

import com.penny.penny_backend.domain.*;
import com.penny.penny_backend.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class TeacherAccountServiceImpl implements TeacherAccountService {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final AccountRepository accountRepository;
    private final TeacherAccountRepository teacherAccountRepository;
    private final TeacherAccountHistoryRepository teacherAccountHistoryRepository;
    private final JobRepository jobRepository;
    private final AccountService accountService;

    public TeacherAccountServiceImpl(TeacherRepository teacherRepository,
                                     StudentRepository studentRepository,
                                     AccountRepository accountRepository,
                                     TeacherAccountRepository teacherAccountRepository,
                                     TeacherAccountHistoryRepository teacherAccountHistoryRepository,
                                     JobRepository jobRepository,
                                     AccountService accountService) {
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.accountRepository = accountRepository;
        this.teacherAccountRepository = teacherAccountRepository;
        this.teacherAccountHistoryRepository = teacherAccountHistoryRepository;
        this.jobRepository = jobRepository;
        this.accountService = accountService;
    }

    @Override
    public TeacherAccount createAccount(Long teacherId, String nickname) {
        // 비즈니스 로직: 통장 생성 전 유효성 검사 또는 기타 작업
        // initialAmount 몇으로 설정?
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("선생님이 존재하지 않습니다."));

        // accountNum 생성 알고리즘 필요
        String accountNum = "1111-11-1111";

        TeacherAccount teacherAccount = new TeacherAccount(nickname, 100000000, accountNum, teacher);
        return teacherAccountRepository.save(teacherAccount);
    }

    @Override
    public Optional<TeacherAccount> getAccountByTeacherId(Long teacherId) {
        return teacherAccountRepository.findById(teacherId);
    }

    @Override
    public List<TeacherAccountHistory> getAccountHistoryByTeacherId(Long teacherId) {
        // 계좌 존재 여부 확인
        TeacherAccount teacherAccount = teacherAccountRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("해당 Student ID의 계좌가 존재하지 않습니다."));

        // 존재하는 경우, 계좌 사용 내역 반환
        return teacherAccount.getTeacherAccountHistories();
    }

    @Override
    @Transactional
    public void paySalaryToStudents(Long teacherId) {
        // 1. teacherId를 사용하여 학급 ID(classId) 찾기
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("해당 teacherId를 가진 선생님이 없습니다."));
        Long classId = teacher.getClassroom().getClassroomId();

        // 2. 해당 classId를 가진 학생 목록 찾기
        List<Student> students = studentRepository.findByClassroom_ClassroomId(classId);

        TeacherAccount teacherAccount = teacherAccountRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("해당 teacherId를 가진 선생님 계좌가 존재하지 않습니다."));

        // 3. 각 학생의 직업에 따른 월급을 지급
        for (Student student : students) {
            Long jobId = student.getJob().getJobId();

            // 직업에 따른 월급 조회
            Job job = jobRepository.findById(jobId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 직업명을 가진 Job 정보가 없습니다."));
            int salary = job.getSalary();

            // 학생의 계좌 정보에 월급 지급 (amount 증가)
            Account account = accountRepository.findById(student.getStudentId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 studentId를 가진 계좌가 없습니다."));
            account.setAmount(account.getAmount() + salary);

            // 학생 계좌 내역 추가
            accountService.addAccountHistory(account, "월급", salary, false, teacherId, student.getStudentName(), teacher.getTeacherName());
            // 변경된 계좌 정보를 저장
            accountRepository.save(account);

            TeacherAccountHistory teacherAccountHistory = new TeacherAccountHistory(
                    "월급 지급", salary, true, student.getStudentId(), teacher.getTeacherName(), student.getStudentName(), teacherAccount);
//        teacherAccount.addTeacherAccountHistory(teacherAccountHistory);
            teacherAccountHistoryRepository.save(teacherAccountHistory);
        }
    }
//
    @Override
    @Transactional
    public void receiveFromStudent(TeacherAccount teacherAccount, Account studentAccount,
                                   int amount, String teacherName, String studentName) {

        // 선생님 계좌 조회
//        TeacherAccount teacherAccount = teacherAccountRepository.findById(teacherAccountId)
//                .orElseThrow(() -> new IllegalArgumentException("선생님 계좌가 존재하지 않습니다."));

        // 선생님 계좌 잔액 업데이트 및 계좌 내역 추가
        teacherAccount.setAmount(teacherAccount.getAmount() + amount);
        TeacherAccountHistory teacherAccountHistory = new TeacherAccountHistory(
                "입금", amount, false, studentAccount.getStudent().getStudentId(), teacherName, studentName, teacherAccount);
//        teacherAccount.addTeacherAccountHistory(teacherAccountHistory);
        teacherAccountHistoryRepository.save(teacherAccountHistory);
        teacherAccountRepository.save(teacherAccount);
    }

    @Override
    public void deleteTeacherAccount(Long teacherId) {
        TeacherAccount teacherAccount = teacherAccountRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("선생님 계좌가 존재하지 않습니다."));

        teacherAccountRepository.delete(teacherAccount);

    }

    @Override
    public boolean isTeacherAccount(String accountNum) {
        return teacherAccountRepository.findByAccountNum(accountNum).isPresent();
    }

//    @Override
//    public void addTeacherAccountHistory(TeacherAccount account, String content, int amount, boolean inOrOut, Long counterpartyId,
//                                   String myName, String counterpartyName) {
//        TeacherAccountHistory teacherAccountHistory = new TeacherAccountHistory(content, amount, inOrOut, counterpartyId, myName, counterpartyName, account);
////        account.addAccountHistory(accountHistory);
//        teacherAccountHistoryRepository.save(teacherAccountHistory);
//    }
}