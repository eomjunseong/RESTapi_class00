package com.example.demo.service;


import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    //가입
    @Transactional
    public Long join(Member member){
        List<Member> byName = memberRepository.findByName(member.getName());
        if(!byName.isEmpty()){
            throw new IllegalStateException("존재하는 회원 ");
        }
        memberRepository.save(member);
        return member.getId();
    }

    //전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    //아이디 조회회
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

    //전체 조회
    public List<Member> findAll(){
        return memberRepository.findAll();
    }
}
