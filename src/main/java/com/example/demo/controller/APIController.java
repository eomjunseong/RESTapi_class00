package com.example.demo.controller;

import com.example.demo.domain.Member;
import com.example.demo.domain.Post;
import com.example.demo.service.MemberService;
import com.example.demo.service.PostService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class APIController {


/*
*
*
*   테스트시 data.sql 참조
*
*
* */
    private final MemberService memberService;
    private final PostService postService;

/*
*
*   멤버 관련
*
*/
    //멤버 전체 조회 (총 멤버수 와 멤버 아이디 이름 반환)
    @GetMapping("/api/v1/members")
    public Result<List<MemberDto>> retrieveAllMembers(){
        List<Member> members = memberService.findAll();
        List<MemberDto> collect = members.stream()
                .map(s -> new MemberDto(s.getId(), s.getName()))
                .collect(Collectors.toList());
        return new Result<>(collect.size(),collect);
    }
    
    //특정 멤버 조회 // 귀차나서 entity로 반환...
    @GetMapping("api/v1/members/{id}")
    public Member retrieveAllMember(@PathVariable Long id){
        return memberService.findOne(id);
    }
/*
*
* 게시물 관련
*
* */
    //게시물 등록
    @PostMapping("/api/v1/members/{id}/posts")
    public ResponseEntity<Post> createPost(@PathVariable Long id, @RequestBody CreatePostRequest request) {
        Member member = memberService.findOne(id);
        Post post = new Post();
        post.setContent(request.getContent());
        post.setTitle(request.getTitle());
        post.setMember(member);
        postService.makePost(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    //회원 구분 없이 모든 게시글 조회
    //총 게시글수, 제목 , 내용, 작성자
    @GetMapping("/api/v1/posts")
    public Result<List<PostDTO>> findAllPost(){
        List<Post> posts = postService.findAll();
        List<PostDTO> collect = posts.stream()
                                    .map(s -> new PostDTO(s.getTitle(), s.getContent(), s.getMember().getName()))
                                    .collect(Collectors.toList());
        return new Result<>(collect.size(),collect);
    }

    //특정 회원 게시글 조회
    //그냥 entity로 반환
    @GetMapping("/api/v1/members/{id}/posts") //멤버 전체 조회
    public List<Post> retrieveMemberPosts(@PathVariable Long id){
        Member member = memberService.findOne(id);
        return postService.findUserPost(member);
    }

    //게시물 삭제 ..  게시물 ID 알아야함
    @DeleteMapping("/api/v1/posts/{id}")
    public void deletePost(@PathVariable Long id){
        postService.deletePostById(id);
    }


    //게시물 업데이트 .. 게시물 ID 알아야함
    //작성자. 바뀐 제목, 바뀐 내용 반환
    @PutMapping("/api/v1/posts/{id}")
    public UpdatePostResponse updatePost(@PathVariable Long id, @RequestBody UpdatePostRequest request){
        //업데이트 시킴
        postService.updatePost(id,request.getTitle(),request.getContent());

        //재조회
        Post post = postService.findById(id);

        return new UpdatePostResponse(post.getMember().getName(),post.getTitle(),post.getContent());
    }


    /*
    *
    * DTO
    *
    * */
    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count ;  //{}객체로 싸서 반환 하는 장점임
        private T data;
    }
    @Data
    @AllArgsConstructor
    static class MemberDto {
        private Long id;
        private String name;
    }

    @Data @AllArgsConstructor
    @NoArgsConstructor
    static class CreateMemberRequest {
        private String name;
    }
    @Data @AllArgsConstructor
    static class CreateMemberResponse {
        private Long id;
    }

    @Data @AllArgsConstructor
    @NoArgsConstructor
    static class CreatePostRequest{
        private String title;
        private String content;
    }

    @Data @AllArgsConstructor
    @NoArgsConstructor
    static class PostDTO {
        private String title;
        private String content;
        private String name; //작성자 이름
    }

    @Data @AllArgsConstructor
    @NoArgsConstructor

    static class UpdatePostRequest {
        private String title;
        private String content;
    }

    @Data @AllArgsConstructor
    static class UpdatePostResponse{
        private String name; //작성자
        private String title;
        private String content;
    }
}
