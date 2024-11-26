package com.penny.penny_backend.dto;

import com.penny.penny_backend.domain.School;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateSchoolRequest {
    private Long schoolId;
    private String schoolName;
    private String schoolAddress;

    public School toEntity(){
        return School.builder()
                .name(schoolName)
                .address(schoolAddress)
                .build();
    }

}
