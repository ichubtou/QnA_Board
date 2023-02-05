package com.example.qnaboard.board.service;

import com.example.qnaboard.board.entity.Answer;

import com.example.qnaboard.board.repository.AnswerRepository;
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
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final MemberService memberService;
    private final QuestionService questionService;

    @Transactional(readOnly = true)
    public Answer findAnswer(Long answerId) {
        //JWT 에서 member 객체를 얻어서 answer 객체에 추가 필요

        return null;
    }

    public Answer createAnswer(Answer answer) {
        memberService.findVerifiedMember(answer.getAnswerId());
        questionService.findVerifiedQuestion(answer.getQuestion().getQuestionId());
        answer.getQuestion().changeQuestionStatusToAnswered();

        return answerRepository.save(answer);
    }

    @Transactional(readOnly = true)
    public Page<Answer> findAnswers(Integer page, Integer size) {
        return answerRepository.findAll(PageRequest.of(page, size, Sort.by("answerId").descending()));
    }

    public void deleteAnswer(Long answerId) {
        findVerifiedAnswer(answerId);
        answerRepository.deleteById(answerId);
    }

    public Answer updateAnswer(Answer answer) {
        Answer changeAnswer = findVerifiedAnswer(answer.getAnswerId());

        Optional.ofNullable(answer.getContent()).ifPresent(content -> changeAnswer.changeAnswerContent(answer.getContent()));

        return changeAnswer;
    }

    private Answer findVerifiedAnswer(Long answerId) {
        Optional<Answer> answer = answerRepository.findById(answerId);

        Answer verifiedAnswer = answer.orElseThrow(() -> new CustomException(ExceptionCode.ANSWER_NOT_EXISTS));

        return verifiedAnswer;
    }
}
