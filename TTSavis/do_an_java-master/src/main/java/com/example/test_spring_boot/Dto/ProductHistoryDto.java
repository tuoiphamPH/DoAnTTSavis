package com.example.test_spring_boot.Dto;

import com.example.test_spring_boot.Entity.ProductEntity;
import com.example.test_spring_boot.Entity.ProductHistory;
import com.example.test_spring_boot.Entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductHistoryDto {
    private Long id;
    private Integer totalItem;
    private String nameProduct;
    private String nameCategory;
    private String createDate;
    private String username = "";
    private String fullname = "";
    private String price;
    private String image;
    private Integer sale;
    private ProductDto productDto;

    public ProductHistoryDto(ProductHistory productHistory){
        if(productHistory != null){
            this.id = productHistory.getId();
            this.totalItem = productHistory.getTotalItem();
            this.nameProduct = productHistory.getProductEntity().getName();
            this.nameCategory = productHistory.getProductEntity().getCategoryEntity().getName();
            if(productHistory.getCreateDate() != null){
                this.createDate = new SimpleDateFormat("yyyy-MM-dd HH:ss").format(productHistory.getCreateDate());
            }else {
                if(productHistory.getModifierDate() != null){
                    this.createDate =  new SimpleDateFormat("yyyy-MM-dd HH:ss").format(productHistory.getModifierDate());
                }
            }
            if(productHistory.getUserEntity() != null){
                this.productDto = new ProductDto(productHistory.getProductEntity());
            }
            if(productHistory.getUserEntity() != null && productHistory.getUserEntity().getUsername() != null)
                this.username = productHistory.getUserEntity().getUsername();
            if(productHistory.getUserEntity() != null && productHistory.getUserEntity().getFullname() != null)
                this.fullname = productHistory.getUserEntity().getFullname();
            this.price = productHistory.getProductEntity().getPrice().toString();
            this.image = productHistory.getProductEntity().getImage();
            this.sale = productHistory.getSale() == null ? 0 : productHistory.getSale();
        }
    }
}
