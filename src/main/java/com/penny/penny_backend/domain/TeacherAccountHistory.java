package com.penny.penny_backend.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class TeacherAccountHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;

    private String content;
    private int amount;
    private boolean inOrOut; // 입금일 경우 false, 송금일 경우 true
    private LocalDateTime datetime;
    private Long counterpartyId;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private TeacherAccount teacherAccount;
//
    private String myName;
    private String counterpartyName;

    public TeacherAccountHistory(String content, int amount, boolean inOrOut, Long counterpartyId,
                                 String myName, String counterpartyName, TeacherAccount teacherAccount) {
        this.inOrOut = inOrOut;
        this.counterpartyId = counterpartyId;
        this.content = content;
        this.datetime = LocalDateTime.now();;
        this.amount = amount;
        this.myName = myName;
        this.counterpartyName = counterpartyName;
        setTeacherAccount(teacherAccount);
    }

    public void setTeacherAccount(TeacherAccount teacherAccount) {
        this.teacherAccount = teacherAccount;
        if (teacherAccount != null && !teacherAccount.getTeacherAccountHistories().contains(this)) {
            teacherAccount.getTeacherAccountHistories().add(this); // teacherAccount에서 teacherAccountHistory를 추가
        }
    }
}
