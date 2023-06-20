package com.spring.jpa.chap05_practice.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString(exclude = {"post"})
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_hash_tag")
public class HashTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "tag_no")
    private long id;

    private String tagName; //해시태그 이름

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "post_no")
    private Post post;

}















