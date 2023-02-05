package com.example.qnaboard.board.repository;

import com.example.qnaboard.board.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
