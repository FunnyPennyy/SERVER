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
public class AccountHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;

    private String content;
    private int amount;
    private boolean inOrOut; // 입금일 경우 false, 송금일 경우 true
    private LocalDateTime datetime;
    private Long counterpartyId;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Account account;
//
    private String myName;
    private String counterpartyName;

    public AccountHistory(String content, int amount, boolean inOrOut, Long counterpartyId,
                          String myName, String counterpartyName, Account account) {
        this.content = content;
        this.amount = amount;
        this.inOrOut = inOrOut;
        this.datetime = LocalDateTime.now();
        this.counterpartyId = counterpartyId;
        this.myName = myName;
        this.counterpartyName = counterpartyName;
        setAccount(account);
    }

    public void setAccount(Account account) {
        this.account = account;
        if (account != null && !account.getAccountHistories().contains(this)) {
            account.getAccountHistories().add(this); // Account에서 AccountHistory를 추가
        }
    }
}
