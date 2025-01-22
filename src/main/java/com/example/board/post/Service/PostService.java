package com.example.board.post.Service;

import com.example.board.author.domain.Author;
import com.example.board.author.repository.AuthorRepository;
import com.example.board.post.domain.Post;
import com.example.board.post.dtos.PostDetailRes;
import com.example.board.post.dtos.PostListRes;
import com.example.board.post.dtos.PostSaveReq;
import com.example.board.post.dtos.PostUpdateReq;
import com.example.board.post.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostService {
    public final PostRepository postRepository;
    private final AuthorRepository authorRepository;

    public PostService(PostRepository postRepository, AuthorRepository authorRepository) {
        this.postRepository = postRepository;
        this.authorRepository = authorRepository;
    }

    public void save(PostSaveReq dto) {
        Author author = authorRepository.findByEmail(dto.getEmail()).orElseThrow(()-> new EntityNotFoundException("author not found"));
        postRepository.save(dto.toEntity(author));
    }

    public List<PostListRes> findAll(){
        return postRepository.findAll().stream().map(Post::postListResFromEntity).collect(Collectors.toList());
    }
    public Page<PostListRes> findAllPaging(Pageable pageable){
        Page<Post> pagePosts = postRepository.findAll(pageable);
        return pagePosts.map(p->p.postListResFromEntity());
        }

    public PostDetailRes findById(Long id){
        return postRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("post not found")).postDetailFromEntity();

    }

    public void update(Long id, PostUpdateReq dto){
        Post post = postRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("post not found"));
        post.update(dto);
    }

    public void delete(Long id){
        postRepository.deleteById(id);
    }
}
