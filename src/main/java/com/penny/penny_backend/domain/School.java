package com.penny.penny_backend.domain;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "school")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long schoolId;

    @Column(name = "school_name", nullable = false)
    private String schoolName;
    @Column(name = "school_address", nullable = false)
    private String schoolAddress;
}
