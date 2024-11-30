package com.penny.penny_backend.controller;

import com.penny.penny_backend.domain.Job;
import com.penny.penny_backend.dto.ApiResponseDTO;
import com.penny.penny_backend.dto.JobDTO;
import com.penny.penny_backend.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    // 모든 직업 목록 조회
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<JobDTO>>> getAllJobs() {
        try {
            List<Job> jobs = jobService.getAllJobs();
//            if (jobs.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
            // Job 엔티티를 JobDTO로 변환
            List<JobDTO> jobDTOs = jobs.stream()
                    .map(job -> new JobDTO(
                            job.getJobId(),
                            job.getName(),
                            job.getJobDescription(),
                            job.getSalary()
                    )).toList();

            ApiResponseDTO<List<JobDTO>> response = new ApiResponseDTO<>(
                    "success",
                    "Jobs retrieved successfully",
                    jobDTOs
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponseDTO<List<JobDTO>> response = new ApiResponseDTO<>(
                    "error",
                    "An error occurred while retrieving jobs",
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}