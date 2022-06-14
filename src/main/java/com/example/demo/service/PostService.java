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

    //게시물 ID로 찾기
    public Post findById(Long id){
        return postRepository.findById(id);
    }

    //특정유저의 게시물 찾기
    public List<Post> findUserPost(Member member){
        return postRepository.findUserPost(member);
    }

    //게시글 삭제
    @Transactional
    public void deletePostById(Long id) {
        postRepository.deletePostById(id);
    }

    @Transactional
    public void updatePost(Long id ,String title, String content) {
        postRepository.updatePost(id, title,content);
    }
}
