package com.example.demo.controller;

import com.example.demo.domain.Member;
import com.example.demo.domain.Post;
import com.example.demo.service.MemberService;
import com.example.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PostService postService;

    //멤버 전체 조회
    @GetMapping("/members")
    public List<Member> retrieveAllMembers(){
        return memberService.findAll();
    }
    //특정 멤버 조회
    @GetMapping("/members/{id}")
    public Member retrieveAllMember(@PathVariable Long id){
        return memberService.findOne(id);
    }

}
