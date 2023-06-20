package com.spring.jpa.chap05_practice.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@ToString(exclude = {"hashTags"})
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "post_no")
    private long id;

    @Column(nullable = false)
    private String writer; //작성자

    @Column(nullable = false)
    private String title; //제목

    private String content; //내용

    @CreationTimestamp
    @Column(updatable = false)
    private  LocalDateTime createDate; //작성 시간

    @UpdateTimestamp
    private LocalDateTime updateDate;

    //실제 테이블에는 들어가 있지 않지만 연관관계 매핑을 통한 조회 용도
    @OneToMany(mappedBy = "post")
    @Builder.Default //특정 필드를 지정한 값으로 초기화 하는 것을 강제
    private List<HashTag> hashTags = new ArrayList<>();

    // 양방향 매핑에서 리스트쪽에 데이터를 추가하는 편의메서드 생성
    public void addHashTag(HashTag hashTag) {
        hashTags.add(hashTag);
        if(this != hashTag.getPost()) {
            hashTag.setPost(this);
        }
    }


}



















