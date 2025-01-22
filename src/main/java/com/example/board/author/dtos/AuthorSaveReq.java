package com.example.board.author.dtos;

import com.example.board.author.domain.Author;
import com.example.board.author.domain.Role;
import com.example.board.author.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

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
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorSaveReq {
    @NotEmpty
    private String name;
    @NotEmpty
    private String email;
    @Size(min = 8)
    @NotEmpty
    private String password;
//    사용자가 String으로 입력해도 Role클래스로 자동변환
//    ex)ADMIN, USER 등으로 입력해서 ENUM클래스로 변환
    private Role role;

    public Author toEntity() {
        return Author.builder().name(this.name).email(this.email)
                .password(this.password)
                .role(this.role)
                .build();
    }


}
