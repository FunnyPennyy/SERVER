package com.penny.penny_backend.domain;

//import java.util.*;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
//import org.hibernate.annotations.CreationTimestamp;

//@AllArgsConstructor  // ????
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class DepositType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 예끔상품(종류) id

    private String name; // 예금상품 이름

    private int duration; // 기간

    private int interest; // 이자율 (% 단위로 저장)


    @Builder
    public DepositType(Long id, String name, int duration, int interest) {
        this.id = id;
        this.name= name;
        this.duration = duration;
        this.interest = interest;

    }


}