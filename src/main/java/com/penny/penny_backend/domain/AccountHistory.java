package com.penny.penny_backend.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class AccountHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;

    private String content;
    private int amount;
    private boolean inOrOut;
    private LocalDateTime datetime;
    private Long sentUserId;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Account account;

    public AccountHistory(String content, int amount, boolean inOrOut, Long sentUserId, Account account) {
        this.content = content;
        this.amount = amount;
        this.inOrOut = inOrOut;
        this.datetime = LocalDateTime.now();
        this.sentUserId = sentUserId;
        setAccount(account);
    }

    protected AccountHistory() {
    }

    // getter & setter
    public Long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isInOrOut() {
        return inOrOut;
    }

    public void setInOrOut(boolean inOrOut) {
        this.inOrOut = inOrOut;
    }

    public LocalDateTime getDate() {
        return datetime;
    }

    public void setDate(LocalDateTime date) {
        this.datetime = datetime;
    }

    public Long getSentUserId() {
        return sentUserId;
    }

    public void setSentUserId(Long sentUserId) {
        this.sentUserId = sentUserId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
        if (account != null && !account.getAccountHistories().contains(this)) {
            account.getAccountHistories().add(this); // Account에서 AccountHistory를 추가
        }
    }

    @Override
    public String toString() {
        return "AccountHistory{" +
                "historyId=" + historyId +
                ", content='" + content + '\'' +
                ", amount=" + amount +
                ", inOrOut=" + inOrOut +
                ", date=" + datetime +
                ", sentUserId=" + sentUserId +
                ", account=" + (account != null ? account.getStudentId() : "null") +
                '}';
    }
}
