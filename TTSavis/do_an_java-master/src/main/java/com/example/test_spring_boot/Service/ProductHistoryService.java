package com.example.test_spring_boot.Service;

import com.example.test_spring_boot.Dto.CartDto;
import com.example.test_spring_boot.Dto.ProductDto;
import com.example.test_spring_boot.Dto.ProductHistoryDto;
import com.example.test_spring_boot.Dto.ReceiptDto;
import com.example.test_spring_boot.Dto.SearchDto.ResultDTO;
import com.example.test_spring_boot.Dto.SearchDto.SearchReportDto;
import com.example.test_spring_boot.Entity.ProductHistory;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface ProductHistoryService {

    List<ProductHistoryDto> getBySearch(SearchReportDto searchReportDto) throws ParseException;

    List<ProductHistory> getALlGruopByProductHistory();
    List<ReceiptDto> getAll();

    ResultDTO searchReceipDto(SearchReportDto searchReportDto, HttpServletResponse response);
    Workbook exportBySearchDto(List<ReceiptDto> receiptDtos, HttpServletResponse response);

    Page<ProductDto> getProductByCartDto(List<CartDto> lstCart,Pageable pageable);
}
