package com.example.board.author.Service;

import com.example.board.author.domain.Author;
import com.example.board.author.dtos.AuthorDetailRes;
import com.example.board.author.dtos.AuthorListRes;
import com.example.board.author.dtos.AuthorSaveReq;
import com.example.board.author.dtos.AuthorUpdateReq;
import com.example.board.author.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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
@Service
@Transactional
public class AuthorService {
    public final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public void save(AuthorSaveReq dto){
    if(authorRepository.findByEmail(dto.getEmail()).isPresent()){
        throw new IllegalArgumentException("이미 존재하는 이메일입니다");
    }
    authorRepository.save(dto.toEntity());
    }

    public List<AuthorListRes> findAll(){
        return authorRepository.findAll().stream().map(a->a.listDtoFromEntity()).collect(Collectors.toList());
    }

    public AuthorDetailRes findById(Long id){
        return authorRepository.findById(id).orElseThrow(()->new NoSuchElementException("없는아이디입니다.")).detailFromEntity();
    }

    public void update(Long id, AuthorUpdateReq dto){
        Author author = authorRepository.findById(id).orElseThrow(()->new EntityNotFoundException("author is not found"));
        author.updateDtoFromEntity(dto);
    }


}
