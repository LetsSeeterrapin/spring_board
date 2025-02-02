package com.example.board.author.dtos;

import com.example.board.author.domain.Role;
import com.example.board.common.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

//1.author 삭제   => casecade되는지도 확인
//  : /author/delete/{id}
//
//2.author 상세조회
//  : /author/detail/{id}
//  : AuthorDetail(dto명) - id, name, email, password, role, postCount, createdTime
//
//3.author 업데이트
//  : /author/update/{id}
//  : AuthorUpdate - name, password만
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthorDetailRes {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Role role;
    private int postCount;
    private LocalDateTime createdTime;

}
