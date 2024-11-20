package com.penny.penny_backend.domain;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long classroomId;
}
