package com.example.board.post.domain;

import com.example.board.author.domain.Author;
import com.example.board.common.domain.BaseTimeEntity;
import com.example.board.post.dtos.PostDetailRes;
import com.example.board.post.dtos.PostListRes;
import com.example.board.post.dtos.PostUpdateReq;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
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

//1.상세보기(PostDetailRes) : deletail/{id} => id, title, contents, authorEmail, createdTime, updatedTime
//2.수정(PostUpdateReq) : update/{id} => form형식으로 title, contents,
//3.삭제 : delete/{id}
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 3000)
    private String content;
    private String appointment;
    private LocalDateTime appointmentTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    public PostListRes postListResFromEntity(){
        return PostListRes.builder()
                .id(this.id)
                .title(this.title)
                .authorEmail(this.author.getEmail())
                .build();
    }

    public PostDetailRes postDetailFromEntity(){
        return PostDetailRes.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .authorEmail(this.author.getEmail())
                .createdTime(this.getCreatedTime())
                .updatedTime(this.getUpdatedTime())
                .build();
    }

    public void update(PostUpdateReq dto){
        this.title = dto.getTitle();
        this.content = dto.getContent();
    }

    public void updateAppointmentToN(String appointment) {
        this.appointment = appointment;
//        01.23 수업 미참여 부분
    }


}
