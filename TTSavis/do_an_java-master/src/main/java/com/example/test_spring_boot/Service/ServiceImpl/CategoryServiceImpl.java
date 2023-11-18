package com.example.test_spring_boot.Service.ServiceImpl;

import com.example.test_spring_boot.Dto.CategoryDto;
import com.example.test_spring_boot.Dto.ProductDto;
import com.example.test_spring_boot.Dto.SearchDto.SearchReportDto;
import com.example.test_spring_boot.Entity.CategoryEntity;
import com.example.test_spring_boot.Repository.CategoryRepository;
import com.example.test_spring_boot.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public CategoryDto createOrUpdate(CategoryDto categoryDto) {
        CategoryEntity category = null;
        if(categoryDto.getId() != null){
            category = categoryRepository.findById(categoryDto.getId()).get();
        }
        if(category == null){
            category = new CategoryEntity();
        }
        category.setName(categoryDto.getName());
        category.setActive(1);
        return  new CategoryDto(categoryRepository.save(category));
    }

    @Override
    public Page<CategoryDto> searchByDto(SearchReportDto searchDto) {
        int pageSize = 5;
        int pageIndex = searchDto.getPageIndex();
        Pageable pageable = PageRequest.of(pageIndex,pageSize);
        Page<CategoryDto> list;
        if(searchDto.getTextSearch() != null){
            list = categoryRepository.getAllByName(pageable,searchDto.getTextSearch()).map(x -> new CategoryDto(x));
        }else {
            list = categoryRepository.getAll(pageable).map(x -> new CategoryDto(x));
        }
        return list;
    }

    @Override
    public Page<CategoryDto> pageAll() {
        int pageSize = 5;
        int pageIndex = 0;
        Pageable pageable = PageRequest.of(pageIndex,pageSize);
        Page<CategoryDto> list = categoryRepository.getAll(pageable).map(x -> new CategoryDto(x));
        return list;
    }
}
