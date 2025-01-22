package com.example.board.author.domain;

import com.example.board.author.dtos.AuthorDetailRes;
import com.example.board.author.dtos.AuthorListRes;
import com.example.board.author.dtos.AuthorUpdateReq;
import com.example.board.common.domain.BaseTimeEntity;
import lombok.*;
import javax.persistence.*;

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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@ToString
@Builder
public class Author extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20, nullable = false)
    private String name;
    @Column(length = 30, nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
//    enum은 기본적으로 숫자값으로 들어감으로, 별도로 STRING지정 필요
    @Enumerated(EnumType.STRING)
    private Role role;

    public AuthorListRes listDtoFromEntity(){
//        AuthorListRes안에 @Builder 어노테이션이있어야 builder().build()사용가능
        return AuthorListRes.builder().id(this.id).name(this.name).email(this.email).build();
    }

    public AuthorDetailRes detailFromEntity(){
        return AuthorDetailRes.builder().id(this.id).name(this.name)
                .email(this.email).password(this.password)
                .role(this.role).build();
    }

    public void updateDtoFromEntity(AuthorUpdateReq dto){
        this.name = dto.getName();
        this.password = dto.getPassword();
    }


}
