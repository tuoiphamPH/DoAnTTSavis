package com.example.test_spring_boot.Dto.Func;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReivewUserDto {

    private Long idProduct;
    private String fullname;
    private String email;
    private String content;

}
