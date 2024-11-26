package com.penny.penny_backend.service;

import com.penny.penny_backend.domain.Job;

import java.util.List;

public interface JobService {
    // 직업 목록 조회
    List<Job> getAllJobs();
}
