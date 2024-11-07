package com.penny.penny_backend.account.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class TeacherAccountHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;

    private boolean inOrOut;
    private int sentUserId;
    private String content;
    private LocalDateTime datetime;
    private int amount;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private TeacherAccount teacherAccount;

    public TeacherAccountHistory(Long historyId, boolean inOrOut, int sentUserId, String content, int amount, TeacherAccount teacherAccount) {
        this.historyId = historyId;
        this.inOrOut = inOrOut;
        this.sentUserId = sentUserId;
        this.content = content;
        this.datetime = LocalDateTime.now();;
        this.amount = amount;
        this.teacherAccount = teacherAccount;
    }

    protected TeacherAccountHistory() {
    }

    // getter & setter
    public Long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }

    public boolean isInOrOut() {
        return inOrOut;
    }

    public void setInOrOut(boolean inOrOut) {
        this.inOrOut = inOrOut;
    }

    public int getSentUserId() {
        return sentUserId;
    }

    public void setSentUserId(int sentUserId) {
        this.sentUserId = sentUserId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDate() {
        return datetime;
    }

    public void setDate(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public TeacherAccount getTeacherAccount() {
        return teacherAccount;
    }

    public void setTeacherAccount(TeacherAccount teacherAccount) {
        this.teacherAccount = teacherAccount;
        if (teacherAccount != null && !teacherAccount.getTeacherAccountHistories().contains(this)) {
            teacherAccount.getTeacherAccountHistories().add(this); // teacherAccount에서 teacherAccountHistory를 추가
        }
    }

    @Override
    public String toString() {
        return "TeacherAccountHistory{" +
                "historyId=" + historyId +
                ", content='" + content + '\'' +
                ", amount=" + amount +
                ", inOrOut=" + inOrOut +
                ", date=" + datetime +
                ", sentUserId=" + sentUserId +
                ", account=" + (teacherAccount != null ? teacherAccount.getTeacherId() : "null") +
                '}';
    }
}
