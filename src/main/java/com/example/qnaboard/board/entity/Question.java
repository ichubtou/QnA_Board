package com.example.qnaboard.board.entity;

import com.example.qnaboard.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    private QuestionPublishStatus questionPublishStatus;

    @Enumerated(EnumType.STRING)
    private QuestionStatus questionStatus = QuestionStatus.QUESTION_REGISTRATION;

    @OneToOne
    private Answer answer;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    public enum QuestionPublishStatus {
        QUESTION_PUBLIC("공개글"),
        QUESTION_PRIVATE("비공개글");

        @Getter
        private String status;

        QuestionPublishStatus(String status) {
            this.status = status;
        }
    }

    public enum QuestionStatus {
        QUESTION_REGISTRATION("질문 등록"),
        QUESTION_ANSWERED("답변 완료"),
        QUESTION_DELETE("질문 삭제");

        @Getter
        private String status;

        QuestionStatus(String status) {
            this.status = status;
        }
    }
}
