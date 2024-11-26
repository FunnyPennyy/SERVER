package com.penny.penny_backend.domain;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "school")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long schoolId;



    @Column(name = "name", nullable = false)
    private String schoolName;

    @Column(name = "address", nullable = false)
    private String schoolAddress;


    public String getSchoolName() {
        return schoolName;
    }
    public String getSchoolAddress() {
        return schoolAddress;
    }

    @Builder
    public School(String name, String address){
        this.schoolName = name;
        this.schoolAddress = address;
    }



}
