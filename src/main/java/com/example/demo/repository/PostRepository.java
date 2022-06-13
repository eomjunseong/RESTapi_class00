package com.example.demo.repository;

import com.example.demo.domain.Member;
import com.example.demo.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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


}
