package com.example.test_spring_boot.RestController;

import com.example.test_spring_boot.Dto.CategoryDto;
import com.example.test_spring_boot.Dto.ProductDto;
import com.example.test_spring_boot.Dto.SearchDto.SearchReportDto;
import com.example.test_spring_boot.Entity.CategoryEntity;
import com.example.test_spring_boot.Repository.CategoryRepository;
import com.example.test_spring_boot.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping(value = "/api/category")
public class RestCategoryController {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/get_all_category")
    public  List<CategoryDto> categoryIndex(){
        return categoryRepository.getAllDto();
    }

    @PostMapping("/create_update_category")
    public CategoryDto createCategory(CategoryDto dto){
        return categoryService.createOrUpdate(dto);
    }

    @GetMapping("/remove_cateogry/{id}")
    public Boolean removeCategory(@PathVariable("id") Long id){
        if(id != null)
            categoryRepository.deleteById(id);
        return true;
    }

    @GetMapping("/category_detail/{id}")
    public CategoryDto getCategory(@PathVariable("id") Long id){
        return new CategoryDto(categoryRepository.findById(id).get());
    }

}
