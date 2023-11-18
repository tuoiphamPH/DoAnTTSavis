package com.example.test_spring_boot.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "sale")
    private Integer sale;

    @Column(name = "content")
    private String content;

    private Long total;

    @Column(name = "image")
    private String image;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", referencedColumnName = "id")
    private ReviewEntity reviewEntity;


    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    private CategoryEntity categoryEntity;
    private Integer active;

}
