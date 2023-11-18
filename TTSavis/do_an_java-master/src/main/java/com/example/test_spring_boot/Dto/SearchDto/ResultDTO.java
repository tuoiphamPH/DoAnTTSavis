package com.example.test_spring_boot.Dto.SearchDto;


import com.example.test_spring_boot.Dto.ProductHistoryDto;
import com.example.test_spring_boot.Dto.ReceiptDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultDTO {
    private List<ReceiptDto> receiptDtos;
    private int totalPage;
    private ReceiptDto receiptDto;
    private int number;
    private Long length;
}
