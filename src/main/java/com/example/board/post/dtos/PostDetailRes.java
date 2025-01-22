package com.example.board.post.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//1.상세보기(PostDetailRes) : deletail/{id} => id, title, contents, authorEmail, createdTime, updatedTime
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PostDetailRes {
    private Long id;
    private String title;
    private String content;
    private String authorEmail;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
