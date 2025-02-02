package com.example.board.author.controller;

import com.example.board.author.Service.AuthorService;
import com.example.board.author.dtos.AuthorDetailRes;
import com.example.board.author.dtos.AuthorListRes;
import com.example.board.author.dtos.AuthorSaveReq;
import com.example.board.author.dtos.AuthorUpdateReq;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;


//1.Author 객체
// -id, name(length=20, notnull), email(length=30, notnull, unique), password(notnull)
// -basetime entity 적용 : createdTime, updatedTime
//2.AuthorRepository 생성
//3.AuthorController(RestController)
//1)회원가입(author/create)
//  -name, email, password 으로 가입 (form-data)
//  -DTO명 : AuthorSaveReq
//2)회원목록조회(author/list)
//  -id, name, email만 return (json)
//        -DTO명 : AuthorListRes
@Controller
@RequestMapping("/author")
public class AuthorRestController {
    public final AuthorService authorService;

    public AuthorRestController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/create")
    public String authorCreateString(){
        return "/author/author_create";
    }

    @GetMapping("/login")
    public String authorLoginScreen(){
        return "/author/author_login";
    }


    @PostMapping("/create")
    public String authorCreate(@ModelAttribute @Valid AuthorSaveReq dto) { //@Requestbody 원래는 사용
        authorService.save(dto);
        return "redirect:/";
    }


    @GetMapping("/list")
    public String authorList(Model model) {
        List<AuthorListRes> authorListResList = authorService.findAll();
        model.addAttribute("authorList", authorListResList);
        return "author/author_list";
    }

    @GetMapping("/delete/{id}")
    public String authorDelete(@PathVariable Long id) {
        authorService.delete(id);
        return "OK";
    }

    @GetMapping("/detail/{id}")
    public String authorDetail (@PathVariable Long id, Model model) {
        AuthorDetailRes dto = authorService.findById(id);
        model.addAttribute("author", dto);
        return "author/author_detail";
    }

    @PostMapping("/update")
    public String authorUpdate (@PathVariable Long id, @ModelAttribute AuthorUpdateReq dto){
        authorService.update(id, dto);
        return "ok";
    }

}
