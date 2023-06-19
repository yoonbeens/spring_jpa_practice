package com.spring.jpa.chap05_practice.service;

import com.spring.jpa.chap05_practice.repository.HashTagRepository;
import com.spring.jpa.chap05_practice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional //JPA 레파지토리는 트랜잭션 단위로 동작하기 때문에 작성해주세요!
public class PostService {


    private final PostRepository postRepository;
    private final HashTagRepository hashTagRepository;

}
