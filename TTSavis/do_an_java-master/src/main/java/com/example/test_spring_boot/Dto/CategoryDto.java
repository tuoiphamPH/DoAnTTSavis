package com.example.test_spring_boot.Dto;

import com.example.test_spring_boot.Entity.CategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private Long id;
    private String name;
    private String CreateDate;
    private String CreateBy;

    public CategoryDto(CategoryEntity categoryEntity){
        if(categoryEntity!= null){
            if(categoryEntity.getId() != null)
                this.id = categoryEntity.getId();
            if(categoryEntity.getName() != null)
                this.name = categoryEntity.getName();
            if(categoryEntity.getCreateDate() != null){
                this.CreateDate = new SimpleDateFormat("yyyy-MM-dd HH:ss").format(categoryEntity.getCreateDate());
            }else {
                if(categoryEntity.getModifierDate() != null){
                    this.CreateDate =  new SimpleDateFormat("yyyy-MM-dd HH:ss").format(categoryEntity.getModifierDate());
                }
            }
            this.CreateBy = categoryEntity.getCreateBy();
        }
    }
}
