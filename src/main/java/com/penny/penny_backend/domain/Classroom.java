package com.penny.penny_backend.domain;

import jakarta.persistence.*;
import lombok.*;
import com.penny.penny_backend.domain.School;


@Entity
@Table(name = "classroom")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long classroomId;
    private Integer grade;
    private Integer classNum;
    private Integer year;

    @ManyToOne(fetch = FetchType.LAZY)
    private School school;

}
