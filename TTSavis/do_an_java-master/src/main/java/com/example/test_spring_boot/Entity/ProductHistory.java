package com.example.test_spring_boot.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_history")
public class ProductHistory extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total")
    private Integer totalItem;

    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    private ProductEntity productEntity;
    @Column(name = "sale")
    private Integer sale;

    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    private UserEntity userEntity;
}
