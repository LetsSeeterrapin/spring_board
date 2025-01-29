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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public void save(PostSaveReq dto) throws EntityNotFoundException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Author author = authorRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("author is not found"));
        LocalDateTime appointmentTime = null;
        if (dto.getAppointment().equals("Y")) {
            if (dto.getAppointmentTime().isEmpty() || dto.getAppointmentTime() == null) {
                throw new IllegalArgumentException("시간이 비어져 있습니다.");
            } else {
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                appointmentTime = LocalDateTime.parse(dto.getAppointmentTime(), dateTimeFormatter);
                LocalDateTime now = LocalDateTime.now();
                if (appointmentTime.isBefore(now)) {
                    throw new IllegalArgumentException("시간이 과거입니다.");
                }
            }
            postRepository.save(dto.toEntity(author, appointmentTime));
        }
    }

    public List<PostListRes> findAll(){
        return postRepository.findAll().stream().map(Post::postListResFromEntity).collect(Collectors.toList());
    }
    public Page<PostListRes> findAllPaging(Pageable pageable){
        Page<Post> pagePosts = postRepository.findAll(pageable);
        return pagePosts.map(p->p.postListResFromEntity());
        }

    public List<PostListRes> listFetchJoin() {
//        일반JOIN : author를 join해서 post를 조회하긴 하나, author의 데이터는 실제 참조할 때 쿼리가 N+1번 발생.
//        List<Post> postList = postRepository.findAllJoin();
//        FETCH JOIN : author를 join해서 조회하고, author의 데이터도 join시점에서 가져옴. 쿼리 1번 발생.
        List<Post> postList = postRepository.findAllFetchJoin();
        return postList.stream().map(p->p.postListResFromEntity()).collect(Collectors.toList());
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
