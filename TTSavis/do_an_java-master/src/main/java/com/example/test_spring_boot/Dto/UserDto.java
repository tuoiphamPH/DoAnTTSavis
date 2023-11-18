package com.example.test_spring_boot.Dto;

import com.example.test_spring_boot.Entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String fullname;
    private String password;
    private String createDate;
    private String createBy;

    public UserDto(UserEntity userEntity){
       if(userEntity!= null){
           this.id = userEntity.getId();
           this.username = userEntity.getUsername();
           this.email = userEntity.getEmail();
           this.fullname = userEntity.getFullname();
           this.createBy = userEntity.getCreateBy();
           this.password = userEntity.getPassword();

           if(userEntity.getCreateDate() != null){
               this.createDate = new SimpleDateFormat("yyyy-MM-dd HH:ss").format(userEntity.getCreateDate());
           }else {
               if(userEntity.getModifierDate() != null){
                   this.createDate =  new SimpleDateFormat("yyyy-MM-dd HH:ss").format(userEntity.getModifierDate());
               }
           }
       }
    }
}
