package com.example.test_spring_boot.Dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataDto {
    private Long total;
    private Double price;
    private Long totalReceipt;
    private Long totalReceiptPay;
}
