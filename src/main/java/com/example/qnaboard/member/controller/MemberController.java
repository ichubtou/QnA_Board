package com.example.qnaboard.member.controller;

import com.example.qnaboard.dto.MultiResponseDto;
import com.example.qnaboard.dto.SingleResponseDto;
import com.example.qnaboard.member.dto.MemberDto;
import com.example.qnaboard.member.entity.Member;
import com.example.qnaboard.member.mapper.MemberMapper;
import com.example.qnaboard.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/members")
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberMapper memberMapper;

    @GetMapping("/{member-id}")
    public ResponseEntity getMember(@PathVariable("member-id") Long memberId) {
        Member findMember = memberService.findMember(memberId);
        MemberDto.Response response = memberMapper.memberToMemberResponse(findMember);

        return new ResponseEntity(new SingleResponseDto(response), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity postMember(@RequestBody MemberDto.Post postMemberDto) {
        Member member = memberMapper.memberPostToMember(postMemberDto);
        Member createdMember = memberService.createMember(member);

        MemberDto.Response response = memberMapper.memberToMemberResponse(createdMember);

        return new ResponseEntity(new SingleResponseDto(response), HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity patchMember(@RequestBody MemberDto.Patch patchMemberDto) {
        Member patchMember = memberMapper.memberPatchToMember(patchMemberDto);
        Member member = memberService.updateMember(patchMember);

        MemberDto.Response response = memberMapper.memberToMemberResponse(member);

        return new ResponseEntity(new SingleResponseDto(response), HttpStatus.OK);
    }

    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(@PathVariable("member-id") Long memberId) {
        memberService.deleteMember(memberId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity getMembers(@Positive @RequestParam Integer page, @Positive @RequestParam Integer size) {
        Page<Member> pageMembers = memberService.findMembers(page - 1, size);
        List<Member> members =pageMembers.getContent();

        return new ResponseEntity(new MultiResponseDto<>(memberMapper.membersToMembersResponse(members), pageMembers), HttpStatus.OK);
    }
}
