package com.example.test_spring_boot.Service;

import com.example.test_spring_boot.Dto.CategoryDto;
import com.example.test_spring_boot.Dto.SearchDto.SearchReportDto;
import org.springframework.data.domain.Page;

public interface CategoryService {
    CategoryDto createOrUpdate(CategoryDto categoryDto);
    Page<CategoryDto > searchByDto(SearchReportDto searchReportDto);

    Page<CategoryDto > pageAll();
}
