package com.example.qnaboard.member.service;

import com.example.qnaboard.exception.CustomException;
import com.example.qnaboard.exception.ExceptionCode;
import com.example.qnaboard.member.entity.Member;
import com.example.qnaboard.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member createMember(Member member) {
        verifyExistsEmail(member.getEmail());

        return memberRepository.save(member);
    }

    public void deleteMember(Long memberId) {
        Member member = findVerifiedMember(memberId);

        member.deleteMember(member);
    }

    @Transactional(readOnly = true)
    public Member findMember(Long memberId) {
        Member verifiedMember = findVerifiedMember(memberId);
        return verifiedMember;
    }

    @Transactional(readOnly = true)
    public Page<Member> findMembers(Integer page, Integer size) {
        return memberRepository.findAll(PageRequest.of(page, size, Sort.by("memberId").descending()));
    }

    public Member updateMember(Member member) {
        Member changeMember = findVerifiedMember(member.getMemberId());

        Optional.ofNullable(member.getNickname()).ifPresent(nickname -> changeMember.updateNickname(nickname));
        Optional.ofNullable(member.getPhone()).ifPresent(phone -> changeMember.updatePhone(phone));

        return changeMember;
    }

    public Member findVerifiedMember(Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);

        Member verifiedMember = member.orElseThrow(() -> new CustomException(ExceptionCode.MEMBER_NOT_EXISTS));

        return verifiedMember;
    }

    private void verifyExistsEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);

        if(member.isPresent()) {
            throw new CustomException(ExceptionCode.MEMBER_EXISTS);
        }
    }
}
