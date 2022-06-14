package com.example.demo.repository;

import com.example.demo.domain.Member;
import com.example.demo.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;

    //글 등록
    public void save(Post post){
        em.persist(post);
    }

    //글 조회
    public void findOne(Long id){
        em.find(Post.class,id);
    }

    //전체 조회
    public List<Post> findAll(){
        return em.createQuery("select p from Post p",Post.class).getResultList();
    }

    //회원으로 조회
    public List<Post> findUserPost(Member member){
        return em.createQuery("select p from Post p where p.member = :member",Post.class)
                .setParameter("member",member)
                .getResultList();
    }

    //삭제
    public void deletePostById(Long id){
        Post post = em.find(Post.class, id);
        em.remove(post);
    }

    //업데이트
    //merge x 변경 감지O
    public void updatePost(Long id, String title, String content){
        Post post = em.find(Post.class, id);
        post.setTitle(title);
        post.setContent(content);
    }


    public Post findById(Long id) {
        return em.find(Post.class,id);
    }
}
