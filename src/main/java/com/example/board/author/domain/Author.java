package com.example.board.author.domain;

import com.example.board.author.dtos.AuthorDetailRes;
import com.example.board.author.dtos.AuthorListRes;
import com.example.board.author.dtos.AuthorUpdateReq;
import com.example.board.common.domain.BaseTimeEntity;
import com.example.board.post.domain.Post;
import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

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

//        OneToMany의 기본값이 fetch lazy라 별도의 설정은 없다.
//    mappedBy에 ManyToOne쪽의 변수명을 문자열로 지정.
    @OneToMany(mappedBy = "author", cascade = ALL)
//    빌더패턴에서 변수 초기화(디폴트값) 시 Builder.Default 어노테이션 사용
    @Builder.Default
    private List<Post> posts = new ArrayList<>();


    public AuthorListRes listDtoFromEntity(){
//        AuthorListRes안에 @Builder 어노테이션이있어야 builder().build()사용가능
        return AuthorListRes.builder().id(this.id).name(this.name).email(this.email).build();
    }

    public AuthorDetailRes detailFromEntity(){
        return AuthorDetailRes.builder()
                .id(this.id)
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .role(this.role)
                .postCount(this.posts.size())
                .createdTime(this.getCreatedTime())
                .build();
    }

    public void updateDtoFromEntity(AuthorUpdateReq dto){
        this.name = dto.getName();
        this.password = dto.getPassword();
    }


}
