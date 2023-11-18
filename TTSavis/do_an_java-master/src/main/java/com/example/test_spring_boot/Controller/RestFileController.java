package com.example.test_spring_boot.Controller;

import com.example.test_spring_boot.Dto.SearchDto.SearchReportDto;
import com.example.test_spring_boot.Service.ProductService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/file")
public class RestFileController {
    @Autowired
    ProductService productService;

    @PostMapping("/export_report_by_search")
    public Workbook exportBySearchReport(Model model, SearchReportDto searchReportDto, HttpServletResponse response){
        return productService.exportBySearchDto(searchReportDto, response);
    }


}
