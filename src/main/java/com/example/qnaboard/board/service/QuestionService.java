package com.example.qnaboard.board.service;

import com.example.qnaboard.board.entity.Question;
import com.example.qnaboard.board.repository.QuestionRepository;
import com.example.qnaboard.exception.CustomException;
import com.example.qnaboard.exception.ExceptionCode;
import com.example.qnaboard.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final MemberService memberService;

    @Transactional(readOnly = true)
    public Question findQuestion(Long questionId) {
        return findVerifiedQuestion(questionId);
    }

    public Question createQuestion(Question question) {
        //JWT 에서 member 객체를 얻어서 question 객체에 추가 필요

        return null;
    }

    @Transactional(readOnly = true)
    public Page<Question> findQuestions(Integer page, Integer size) {
        return questionRepository.findAll(PageRequest.of(page, size, Sort.by("questionId").descending()));
    }

    public Question updateQuestion(Question question) {
        Question changeQuestion = findVerifiedQuestion(question.getQuestionId());

        Optional.ofNullable(question.getTitle()).ifPresent(title -> changeQuestion.changeQuestionTitle(title));
        Optional.ofNullable(question.getContent()).ifPresent(content -> changeQuestion.changeQuestionContent(content));

        return changeQuestion;
    }

    public void deleteQuestion(Long questionId) {
        Question question = findVerifiedQuestion(questionId);

        question.softDeleteQuestion(question);
    }

    public Question findVerifiedQuestion(Long questionId) {
        return questionRepository.findById(questionId).orElseThrow(() -> new CustomException(ExceptionCode.QUESTION_NOT_EXISTS));
    }
}
