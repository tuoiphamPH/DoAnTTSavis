package com.example.test_spring_boot.Dto.SearchDto;

import com.example.test_spring_boot.Dto.CategoryDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchReportDto {
    private Long categoryId;
    private String fromDate;
    private String toDate;
    private Integer rating;
    private Integer pageIndex;
    private Integer pageSize;
    private Integer pageTotal;
    private List<Long> lstCategory;
    private List<Integer> lstStar;
    private String textSearch;
    private String sort;
    private String export;
    private String nameUser;
    private String timeCreate;
    private Integer status;
    private Long idProduct;
    private Integer total;
}
