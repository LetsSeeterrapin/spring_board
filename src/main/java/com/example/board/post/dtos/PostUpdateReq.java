package com.example.board.post.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//2.수정(PostUpdateReq) : update/{id} => form형식으로 title, contents,
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PostUpdateReq {
    private String title;
    private String content;
}
