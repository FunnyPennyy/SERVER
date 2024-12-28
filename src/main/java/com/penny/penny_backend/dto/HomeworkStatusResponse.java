package com.penny.penny_backend.dto;

import com.penny.penny_backend.domain.HomeworkStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeworkStatusResponse {
    private Long id;
    private Long studentId;
    private Long homeworkId;
    private HomeworkStatus.Status completionStatus;

    // Entity to DTO conversion method
    public HomeworkStatusResponse(HomeworkStatus status) {
        this.id = status.getId();
        this.studentId = status.getStudent().getId();
        this.homeworkId = status.getHomework().getId();
        this.completionStatus = status.getCompletionStatus();
    }

    public static HomeworkStatusResponse fromEntity(HomeworkStatus status) {
        return new HomeworkStatusResponse(
                status.getId(),
                status.getStudent().getId(),
                status.getHomework().getId(),
                status.getCompletionStatus()
        );
    }
}
