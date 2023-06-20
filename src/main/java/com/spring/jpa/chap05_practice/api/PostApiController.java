package com.spring.jpa.chap05_practice.api;

import com.spring.jpa.chap05_practice.dto.*;
import com.spring.jpa.chap05_practice.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostApiController {

    // 리소스: 게시물 (Post)
    /*
        게시물 목록 조회: /posts           - GET
        게시물 개별 조회: /posts/{id}      - GET
        게시물 등록:     /posts           - POST
        게시물 수정:     /posts     - PUT(전체 수정), PATCH(일부 수정)
        게시물 삭제:     /posts/{id}      - DELETE

     */

    private final PostService postService;

    @GetMapping
    public ResponseEntity<?> List(PageDTO pageDTO) {
        log.info("/api/v1/posts?page={}&size={}", pageDTO.getPage(), pageDTO.getSize());

        PostListResponseDTO dto = postService.getPosts(pageDTO);

        return ResponseEntity
                .ok()
                .body(dto);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable long id) {
        log.info("/api/v1/post/{}: GET");

        try {
            PostDetailResponseDTO dto = postService.getDetail(id);
            return ResponseEntity.ok().body(dto); //문제가 없다면 이렇게 처리
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


    @PostMapping
    public ResponseEntity<?> create(
            @Validated @RequestBody PostCreateDTO dto
            , BindingResult result // 검증 에러 정보를 가진 객체
    ) {

        log.info("/api/v1/posts: POST - payload: {}", dto);

        if(dto == null) {
            return ResponseEntity
                    .badRequest()
                    .body("등록 게시물 정보를 전달해주세요");
        }

        ResponseEntity<List<FieldError>> fieldErrors = getValidatedResult(result);
        if (fieldErrors != null) return fieldErrors;

        try {
            PostDetailResponseDTO responseDTO = postService.insert(dto);
            return ResponseEntity
                    .ok()
                    .body(responseDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .internalServerError()
                    .body("미안 서버 터졌어... 원인: " + e.getMessage());
        }


    }

    private static ResponseEntity<List<FieldError>> getValidatedResult(BindingResult result) {
        if(result.hasErrors()) { //입력값 검증에 걸림
            List<FieldError> fieldErrors = result.getFieldErrors();
            fieldErrors.forEach(err -> {
                log.warn("invalid client data - {}", err.toString());
            });

            return ResponseEntity
                    .badRequest()
                    .body(fieldErrors);
        }
        return null;
    }

    //게시물 수정
    @RequestMapping(method = {RequestMethod.PATCH, RequestMethod.PUT})
    public ResponseEntity<?> update(
            @Validated @RequestBody PostModifyDTO dto,
            BindingResult result, HttpServletRequest request) {

        log.info("/api/v1/posts {} - dto: {}"
                ,request.getMethod(), dto);

        ResponseEntity<List<FieldError>> fieldErrors = getValidatedResult(result);
        if(fieldErrors != null) return fieldErrors;

        PostDetailResponseDTO responseDTO = postService.modify(dto);

        return ResponseEntity.ok().body(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        log.info("/api/vi/posts/{} DELETE!", id);

        try {
            postService.delete(id);
            return ResponseEntity.ok("DEL SUCCESS");
        } catch (Exception e) {
            e.printStackTrace();
            return  ResponseEntity
                    .internalServerError()
                    .body(e.getMessage());
        }
    }


}














