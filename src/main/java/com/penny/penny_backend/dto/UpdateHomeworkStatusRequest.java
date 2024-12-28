package com.penny.penny_backend.dto;

import com.penny.penny_backend.domain.HomeworkStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateHomeworkStatusRequest {
    private HomeworkStatus.Status completionStatus;

}
