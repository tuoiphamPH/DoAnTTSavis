package com.example.test_spring_boot.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "post")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", length = 5000)
    private String content;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "total_like")
    private Integer totalLike;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private ReviewEntity reviewEntity;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private UserEntity userEntity;

}
