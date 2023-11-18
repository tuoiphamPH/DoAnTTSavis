package com.example.test_spring_boot.Service;

import com.example.test_spring_boot.Dto.CartDto;
import com.example.test_spring_boot.Dto.ProductDto;
import com.example.test_spring_boot.Dto.SearchDto.SearchReportDto;
import com.example.test_spring_boot.Entity.ProductEntity;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface ProductService {
    Page<ProductDto> getByPage(Integer pageIndex, Integer pageSize);
    ProductDto createOrUpdate(ProductDto productDto) throws IOException;
    List<ProductDto> searchByDto(SearchReportDto searchReportDto);
    Workbook exportBySearchDto(SearchReportDto searchReportDto, HttpServletResponse response);
    List<ProductDto> getProductByCartDto(List<CartDto> lstCart);
    Page<ProductDto> searchPageByDto(SearchReportDto searchReportDto);
    List<ProductEntity> findAllByListId(List<Long> id);

    Page<ProductDto> getProductByCartDto(List<CartDto> lstCart,Pageable pageable);
}
