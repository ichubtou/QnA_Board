package com.example.qnaboard.exception;

import lombok.Getter;

public enum ExceptionCode {
    MEMBER_EXISTS(409, "Member exists"),
    MEMBER_NOT_EXISTS(404, "Member not exists"),
    QUESTION_NOT_EXISTS(404, "Question not exists"),
    ANSWER_NOT_EXISTS(404, "Answer not exists");


    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
