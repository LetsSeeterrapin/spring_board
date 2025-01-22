package com.example.board.post.dtos;

import com.example.board.author.domain.Author;
import com.example.board.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostSaveReq {
    @NotEmpty
    private String title;
    private String content;
    @NotEmpty
    private String email;

    public Post toEntity(Author author){
        return Post.builder().title(this.title).content(this.content).author(author).build();
    }
}
