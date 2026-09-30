package com.example.pollservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_answers")
public class UserAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long questionId;
    private int selectedOptionIndex;

    public UserAnswer() {
    }

    public UserAnswer(Long id, Long userId, Long questionId, int selectedOptionIndex) {
        this.id = id;
        this.userId = userId;
        this.questionId = questionId;
        this.selectedOptionIndex = selectedOptionIndex;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public int getSelectedOptionIndex() {
        return selectedOptionIndex;
    }

    public void setSelectedOptionIndex(int selectedOptionIndex) {
        this.selectedOptionIndex = selectedOptionIndex;
    }

    @Override
    public String toString() {
        return "UserAnswer{" +
                "id=" + id +
                ", userId=" + userId +
                ", questionId=" + questionId +
                ", selectedOptionIndex=" + selectedOptionIndex +
                '}';
    }
}
