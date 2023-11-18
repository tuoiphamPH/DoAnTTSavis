package com.example.test_spring_boot.Repository;

import com.example.test_spring_boot.Dto.PostDto;
import com.example.test_spring_boot.Entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity,Long> {

    @Query("select new com.example.test_spring_boot.Dto.PostDto(p) from PostEntity p where p.reviewEntity.id = ?1")
    List<PostDto> getDtoByReviewId(Long id);
}
