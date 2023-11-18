package com.example.test_spring_boot.Dto;

import com.example.test_spring_boot.Entity.PostEntity;
import com.example.test_spring_boot.Entity.ReviewEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private Long id;
    private Integer rating;

    public ReviewDto(ReviewEntity reviewEntity){
        if(reviewEntity != null){
            this.id = reviewEntity.getId();
            this.rating = reviewEntity.getRating();
        }
    }
}
