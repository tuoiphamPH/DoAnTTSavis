package com.example.test_spring_boot.Dto;

import com.example.test_spring_boot.Entity.PostEntity;
import com.example.test_spring_boot.Entity.ReviewEntity;
import com.example.test_spring_boot.Entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long id;
    private String content;
    private Long parentId;
    private Integer totalLike;
    private ReviewDto reviewDto;
    private UserDto userDto;
    private Date createDate;

    public PostDto(PostEntity postEntity){
        if(postEntity != null){
            this.id = postEntity.getId();
            this.content = postEntity.getContent();
            this.parentId = postEntity.getParentId();
            this.totalLike = postEntity.getTotalLike();
            this.reviewDto = new ReviewDto(postEntity.getReviewEntity());
            this.userDto = new UserDto(postEntity.getUserEntity());
            this.createDate = postEntity.getCreateDate();
        }
    }
}
