package com.example.test_spring_boot.Service;

import com.example.test_spring_boot.Dto.*;
import com.example.test_spring_boot.Dto.SearchDto.ResultDTO;
import com.example.test_spring_boot.Dto.SearchDto.SearchReportDto;
import com.example.test_spring_boot.Entity.ProductHistory;
import com.example.test_spring_boot.Entity.ReceiptEntity;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
@Service
public interface ReceiptService{

    ReceiptDto createOrUpdate(ReceiptDto receiptDto) throws IOException;
    Page<ReceiptDto> searchByDto(SearchReportDto searchReportDto);
    Workbook exportBySearchDto(List<ReceiptDto> receiptDtos, HttpServletResponse response);
    List<ReceiptDto> searchPageByDto(SearchReportDto searchReportDto);
    boolean deleteById(Long id);
     ReceiptDto findById(Long id);
     Integer changStatus(Long id,Integer status) throws IOException;

     List<ReceiptEntity> getAllByTimeNow(Date date);

     void setActiveByTime();

     List<ReceiptDto> getAll();

}
