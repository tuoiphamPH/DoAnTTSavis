package com.example.test_spring_boot.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultDto {
    private List<ProductDto> productDtos;
    private ReceiptDto receiptDto;
    private Double sumPrice;
    private Long sumtotal;
    private int length;
}
