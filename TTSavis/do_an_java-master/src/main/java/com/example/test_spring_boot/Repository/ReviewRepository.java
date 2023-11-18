package com.example.test_spring_boot.Repository;

import com.example.test_spring_boot.Entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity,Long> {

    @Query("select p.reviewEntity from ProductEntity p where p.id = ?1")
    ReviewEntity getByProductId(Long id);
}
