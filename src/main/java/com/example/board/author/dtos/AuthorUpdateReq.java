package com.example.board.author.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//1.author 삭제   => casecade되는지도 확인
//  : /author/delete/{id}
//
//2.author 상세조회
//  : /author/detail/{id}
//  : AuthorDetail(dto명) - id, name, email, password, role, postCount, createdTime
//
//3.author 업데이트
//  : /author/update/{id}
//  : AuthorUpdateReq - name, password만
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthorUpdateReq {
    private String name;
    private String password;
}
