package com.penny.penny_backend.service;

import com.penny.penny_backend.domain.Job;
import com.penny.penny_backend.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public List<Job> getAllJobs() {
        return jobRepository.findAll(); // 데이터베이스에서 모든 Job 엔티티를 조회
    }
}
//