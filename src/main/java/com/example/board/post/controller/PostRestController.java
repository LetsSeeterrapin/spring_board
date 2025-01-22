package com.example.board.post.controller;

import com.example.board.post.Service.PostService;
import com.example.board.post.dtos.PostDetailRes;
import com.example.board.post.dtos.PostListRes;
import com.example.board.post.dtos.PostSaveReq;
import com.example.board.post.dtos.PostUpdateReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

//1.Post 객체
// -id, title(length=50, notnull), contents(length=3000), String appointment, LocalDateTime appointmentTime
// -author와의 관계 n:1 (컬럼명 author_id)
//        -basetime entity 적용 : createdTime, updatedTime
//2.PostRepository 생성
//3.PostController(RestController)
//1)글쓰기(post/create)
//  -title, contents, email 작성 (form-data)
//  -DTO명 : PostSaveReq
//2)글목록조회(post/list)
//  -id, title, author_email return (json)
//        -DTO명 : PostListRes
@RestController
@RequestMapping("/post/rest")
public class PostRestController {
    private final PostService postService;

    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/create")
    public String postCreate(@Valid PostSaveReq dto){
        postService.save(dto);
        return "ok";
    }

    @GetMapping("/List")
    public List<PostListRes> postList(){
        return postService.findAll();
    }

    @GetMapping("/List/paging")
//    페이징 처리를 위한 테이터 형식 : localhost:8080/post/list/paging?size=10&page=0&sort=createdTime,desc
    public Page<PostListRes> postListPaging(@PageableDefault(size = 10, sort = "createdTime",
            direction = Sort.Direction.DESC) Pageable pageable){
        return postService.findAllPaging(pageable);
    }

    @GetMapping("/detail")
    public PostDetailRes postDetail(@PathVariable Long id){
        return postService.findById(id);
    }

    @PostMapping("/update")
    public String postUpdate(@PathVariable Long id, @ModelAttribute PostUpdateReq dto){
        postService.update(id, dto);
        return "ok";
    }

    @GetMapping("/delete")
    public String postdelete(@PathVariable Long id){
        postService.delete(id);
        return "ok";
    }
}
