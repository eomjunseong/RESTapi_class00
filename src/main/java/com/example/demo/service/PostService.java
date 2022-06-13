package com.example.demo.service;

import com.example.demo.domain.Member;
import com.example.demo.domain.Post;
import com.example.demo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    //게시물 등록
    @Transactional
    public Long makePost(Post post){
        postRepository.save(post);
        return post.getId();
    }

    //게시물 전체 찾기
    public List<Post> findAll(){
        return postRepository.findAll();
    }

    //특정유저의 게시물 찾기
    public List<Post> findUserPost(Member member){
        return postRepository.findUserPost(member);
    }

}
