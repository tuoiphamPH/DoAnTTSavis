package com.example.test_spring_boot.Repository;

import com.example.test_spring_boot.Dto.ProductDto;
import com.example.test_spring_boot.Entity.ProductEntity;
import com.example.test_spring_boot.Entity.ReceiptEntity;
import com.example.test_spring_boot.Entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Long> {
    @Query("select p from ProductEntity p where p.active = 1 order by p.CreateDate desc")
    Page<ProductEntity> getByPage(Pageable pageable);

    @Query("select new com.example.test_spring_boot.Dto.ProductDto(p) from ProductEntity p where p.id = ?1 and p.active = 1")
    ProductDto getDtoById(Long id);

    @Query("select u from ProductEntity u where u.name like ?1 and u.active = 1")
    ProductEntity getByName(String name);
    @Query("select r from ProductEntity r where r.id in ?1 and r.active = 1")
    List<ProductEntity> getAllById(List<Long> id);
}
