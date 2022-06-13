package com.example.demo.controller;

import com.example.demo.domain.Member;
import com.example.demo.domain.Post;
import com.example.demo.service.MemberService;
import com.example.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final MemberService memberService;
    private final PostService postService;

    //게시물 등록
    @PostMapping("/members/{id}/posts")
    public ResponseEntity<Post> createPost(@PathVariable Long id, @RequestBody Post post) {
        Member member = memberService.findOne(id);
        post.setMember(member);
        postService.makePost(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    //회원 구분 없이 모든 게시글 조회
    @GetMapping("/posts")
    public List<Post> findAllPost(){
        return postService.findAll();
    }

    //특정 회원 게시글 조회
    @GetMapping("/members/{id}/posts") //멤버 전체 조회
    public List<Post> retrieveMemberPosts(@PathVariable Long id){
        Member member = memberService.findOne(id);
        return postService.findUserPost(member);
    }


}
