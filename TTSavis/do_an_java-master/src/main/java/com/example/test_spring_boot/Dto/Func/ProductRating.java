package com.example.test_spring_boot.Dto.Func;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRating {
    private Integer idProduct;
    private Integer rate;
}
