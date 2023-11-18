package com.example.test_spring_boot.Dto;

import com.example.test_spring_boot.Entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private Double price;
    private String image;
    private MultipartFile file;
    private Long categoryId;
    private String CreateDate;
    private String CreateBy;
    private String categoryName;
    private Integer rating;
    private String content;
    private Integer totalItem;
    private ReviewDto reviewDto;
    private Long total;

    private Integer sale;

    public ProductDto(ProductEntity productEntity){
        if(productEntity != null){
            this.total = productEntity.getTotal();
            if(productEntity.getId() != null)
                this.id = productEntity.getId();
            if(productEntity.getName() != null)
                this.name = productEntity.getName();
            if(productEntity.getPrice() != null)
                this.price = productEntity.getPrice();
            if(productEntity.getImage() != null)
                this.image = productEntity.getImage();
            if(productEntity.getCategoryEntity() != null){
                this.categoryId = productEntity.getCategoryEntity().getId();
                this.categoryName = productEntity.getCategoryEntity().getName();
            }
            this.CreateBy = productEntity.getCreateBy();
            this.CreateDate =  new SimpleDateFormat("yyyy-MM-dd").format(productEntity.getCreateDate());

            if(productEntity.getReviewEntity() != null && productEntity.getReviewEntity().getRating() != null){
                this.rating = productEntity.getReviewEntity().getRating();
            }
            if(productEntity.getContent() != null){
                this.content = productEntity.getContent();
            }
            if(productEntity.getReviewEntity() != null){
                this.reviewDto = new ReviewDto(productEntity.getReviewEntity());
            }
            if (productEntity.getSale() != null){
                this.sale = productEntity.getSale();
            }else {
                this.sale = 0;
            }
        }
    }
    public ProductDto(ProductEntity productEntity, CartDto cartDto){
        if(productEntity != null){
            if(productEntity.getId() != null)
                this.id = productEntity.getId();
            if(productEntity.getName() != null)
                this.name = productEntity.getName();
            if(productEntity.getImage() != null)
                this.image = productEntity.getImage();
            if(productEntity.getCategoryEntity() != null){
                this.categoryId = productEntity.getCategoryEntity().getId();
                this.categoryName = productEntity.getCategoryEntity().getName();
            }
            this.CreateBy = productEntity.getCreateBy();

            this.CreateDate =  new SimpleDateFormat("yyyy-MM-dd").format(productEntity.getCreateDate());
            if(productEntity.getReviewEntity() != null && productEntity.getReviewEntity().getRating() != null){
                this.rating = productEntity.getReviewEntity().getRating();
            }
            if(productEntity.getContent() != null){
                this.content = productEntity.getContent();
            }
            if(cartDto != null){
                this.totalItem = cartDto.getTotalItem();
            }
            if(productEntity.getPrice() != null)
                this.price = productEntity.getPrice() * this.totalItem;
            if (productEntity.getSale() != null){
                this.sale = productEntity.getSale();
            }else {
                this.sale = 0;
            }
        }
    }
}
