package com.penny.penny_backend.dto;

import com.penny.penny_backend.domain.School;
import lombok.Getter;

@Getter
public class SchoolResponse {
    private final String schoolName;
    private final String schoolAddress;

    public SchoolResponse(School school){
        this.schoolName = school.getSchoolName();
        this.schoolAddress = school.getSchoolAddress();


    }
}
