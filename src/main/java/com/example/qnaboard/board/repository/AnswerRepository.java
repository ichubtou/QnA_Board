package com.example.qnaboard.board.repository;

import com.example.qnaboard.board.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
