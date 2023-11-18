package com.example.test_spring_boot.Service.ServiceImpl;

import com.example.test_spring_boot.Dto.DataDto;
import com.example.test_spring_boot.Dto.ProductHistoryDto;
import com.example.test_spring_boot.Dto.ReceiptDto;
import com.example.test_spring_boot.Dto.SearchDto.SearchReportDto;
import com.example.test_spring_boot.Entity.ProductEntity;
import com.example.test_spring_boot.Entity.ProductHistory;
import com.example.test_spring_boot.Entity.ReceiptEntity;
import com.example.test_spring_boot.Repository.ProductRepository;
import com.example.test_spring_boot.Repository.ReceiptRepository;
import com.example.test_spring_boot.Repository.UserRepository;
import com.example.test_spring_boot.Service.ReceiptService;
import lombok.Data;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.valueOf;

@Service
public class ReceiptServiceImpl implements ReceiptService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ReceiptRepository receiptRepository;

    @Override
    public ReceiptDto createOrUpdate(ReceiptDto receiptDto) throws IOException {

        ReceiptEntity receiptEntity = new ReceiptEntity();
        receiptEntity.setId(receiptDto.getId());
        receiptEntity.setAddress(receiptDto.getAddress());
        receiptEntity.setPhoneNumber(receiptDto.getPhoneNumber());
        receiptEntity.setStatus(receiptDto.getStatus());
        receiptEntity.setPriceShip(receiptDto.getPriceShip());
        receiptEntity.setName(receiptDto.getName());
       if(receiptDto.getListProductDTO() != null){
           List<ProductHistory> list = new ArrayList<>();
           for(ProductHistoryDto x : receiptDto.getListProductDTO()){
               ProductHistory productHistory = new ProductHistory();
               productHistory.setId(x.getId());
               productHistory.setUserEntity(userRepository.getByUsername(x.getUsername()));
               productHistory.setTotalItem(x.getTotalItem());
               productHistory.setProductEntity(productRepository.getByName(x.getNameProduct()));
               productHistory.setSale(productRepository.getByName(x.getNameProduct()).getSale());
             list.add(productHistory);
           }
           receiptEntity.setProductHistorys(list);
       }
        receiptRepository.save(receiptEntity);
        return new ReceiptDto(receiptEntity);
    }

    public List<ReceiptDto> getAll(){
        return receiptRepository.getAll();
    }

    @Override
    public Page<ReceiptDto> searchByDto(SearchReportDto searchReportDto) {
        int pageSize = 5;

        int pageIndex = searchReportDto.getPageIndex();
        if (pageIndex > 0)
            pageIndex = pageIndex - 1;
        else
            pageIndex = 0;
        Pageable pageable = PageRequest.of(pageIndex,pageSize);
        Page<ReceiptDto> list;
        String textSearch = searchReportDto.getTextSearch();

        if (searchReportDto.getTextSearch() != null && searchReportDto.getToDate() != null && searchReportDto.getFromDate() != null){
            Date fromDate;
            Date toDate;
            SimpleDateFormat formatter6 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                fromDate = formatter6.parse(searchReportDto.getFromDate().replace("T", " "));
                toDate = formatter6.parse(searchReportDto.getToDate().replace("T", " "));
                list = receiptRepository.pageSearchByAll(textSearch,fromDate , toDate ,searchReportDto.getStatus(), pageable).map(x-> new ReceiptDto(x));
            } catch (ParseException e) {
                    list = receiptRepository.pageSearchByTextSearch(textSearch,searchReportDto.getStatus(),pageable).map(x-> new ReceiptDto(x));
            }
        }else {
            if(searchReportDto.getStatus() == null){
                list = receiptRepository.pageSearch(pageable).map(x-> new ReceiptDto(x));
            }else {
                list = receiptRepository.pageSearch(searchReportDto.getStatus(),pageable).map(x-> new ReceiptDto(x));
            }
        }

        return list;
    }

    @Override
    public Workbook exportBySearchDto(List<ReceiptDto> receiptDtos, HttpServletResponse response) {
        HSSFWorkbook workbook = null;
        workbook = new HSSFWorkbook();
        CreationHelper createHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet("Report");
        sheet.addMergedRegion(new CellRangeAddress(0, 0 , 0, 7));

        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);
        font.setColor(HSSFFont.COLOR_NORMAL);
        font.setBold(true);

        HSSFCellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(font);
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerCellStyle.setWrapText(false);
        headerCellStyle.setBorderBottom(BorderStyle.THIN);
        headerCellStyle.setBorderLeft(BorderStyle.THIN);
        headerCellStyle.setBorderRight(BorderStyle.THIN);
        headerCellStyle.setBorderTop(BorderStyle.THIN);

        HSSFFont fontcontent = workbook.createFont();
        fontcontent.setFontHeightInPoints((short) 11);
        fontcontent.setColor(HSSFFont.COLOR_NORMAL);

        HSSFCellStyle contentCellStyle = workbook.createCellStyle();
        contentCellStyle.setFont(fontcontent);
        contentCellStyle.setAlignment(HorizontalAlignment.CENTER);
        contentCellStyle.setWrapText(false);
        contentCellStyle.setBorderBottom(BorderStyle.THIN);
        contentCellStyle.setBorderLeft(BorderStyle.THIN);
        contentCellStyle.setBorderRight(BorderStyle.THIN);
        contentCellStyle.setBorderTop(BorderStyle.THIN);

        HSSFCellStyle contentCellStyleDate = workbook.createCellStyle();
        contentCellStyleDate.setFont(fontcontent);
        contentCellStyleDate.setAlignment(HorizontalAlignment.CENTER);
        contentCellStyleDate.setWrapText(false);
        contentCellStyleDate.setBorderBottom(BorderStyle.THIN);
        contentCellStyleDate.setBorderLeft(BorderStyle.THIN);
        contentCellStyleDate.setBorderRight(BorderStyle.THIN);
        contentCellStyleDate.setBorderTop(BorderStyle.THIN);
        contentCellStyleDate.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy"));

        Integer rowIndex = 0;
        Integer cellIndex = 0;
        Row rowHeader = sheet.createRow(rowIndex);
        Cell cellHeader = null;

        rowHeader.setHeightInPoints(33);
        cellHeader = rowHeader.createCell(cellIndex);
        cellHeader.setCellValue("THỐNG KÊ CÁC ĐƠN ĐẶT HÀNG");
        cellHeader.setCellStyle(headerCellStyle);

        for (ReceiptDto r :receiptDtos){
            rowHeader = sheet.createRow(rowIndex +=1);
            cellHeader = rowHeader.createCell(cellIndex);
            cellHeader.setCellValue("STT");
            cellHeader.setCellStyle(headerCellStyle);

            cellHeader = rowHeader.createCell(cellIndex +=1);
            cellHeader.setCellValue("Tên sản phẩm");
            cellHeader.setCellStyle(headerCellStyle);

            cellHeader = rowHeader.createCell(cellIndex += 1);
            cellHeader.setCellValue("Tên danh mục");
            cellHeader.setCellStyle(headerCellStyle);

            cellHeader = rowHeader.createCell(cellIndex += 1);
            cellHeader.setCellValue("Đơn giá");
            cellHeader.setCellStyle(headerCellStyle);

            cellHeader = rowHeader.createCell(cellIndex += 1);
            cellHeader.setCellValue("Số lượng mua");
            cellHeader.setCellStyle(headerCellStyle);

            cellHeader = rowHeader.createCell(cellIndex += 1);
            cellHeader.setCellValue("Họ tên người mua");
            cellHeader.setCellStyle(headerCellStyle);

            cellHeader = rowHeader.createCell(cellIndex += 1);
            cellHeader.setCellValue("Tên tài khoản");
            cellHeader.setCellStyle(headerCellStyle);

            cellHeader = rowHeader.createCell(cellIndex += 1);
            cellHeader.setCellValue("Ngày mua");
            cellHeader.setCellStyle(headerCellStyle);
            Integer numberOfItem = 0;
            List<ProductHistoryDto> lstProductDto = r.getListProductDTO();
            for (ProductHistoryDto item : lstProductDto) {
                numberOfItem +=1;
                rowIndex += 1;
                cellIndex = 0;
                rowHeader = sheet.createRow(rowIndex);
                cellHeader = rowHeader.createCell(cellIndex);
                cellHeader.setCellValue(numberOfItem);
                cellHeader.setCellStyle(contentCellStyle);

                cellHeader = rowHeader.createCell(cellIndex +=1);
                cellHeader.setCellValue(item.getNameProduct());
                cellHeader.setCellStyle(contentCellStyle);

                cellHeader = rowHeader.createCell(cellIndex += 1);
                cellHeader.setCellValue(item.getNameCategory());
                cellHeader.setCellStyle(contentCellStyle);

                cellHeader = rowHeader.createCell(cellIndex += 1);
                cellHeader.setCellValue(item.getPrice());
                cellHeader.setCellStyle(contentCellStyle);

                cellHeader = rowHeader.createCell(cellIndex += 1);
                cellHeader.setCellValue(item.getTotalItem());
                cellHeader.setCellStyle(contentCellStyle);

                cellHeader = rowHeader.createCell(cellIndex += 1);
                cellHeader.setCellValue(item.getFullname());
                cellHeader.setCellStyle(contentCellStyle);

                cellHeader = rowHeader.createCell(cellIndex += 1);
                cellHeader.setCellValue(item.getUsername());
                cellHeader.setCellStyle(contentCellStyle);

                cellHeader = rowHeader.createCell(cellIndex += 1);
                cellHeader.setCellValue(item.getCreateDate());
                cellHeader.setCellStyle(contentCellStyleDate);

            }
            rowIndex += 1;
            cellIndex = 0;
            sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex+1 , 0, 0));
            rowHeader = sheet.createRow(rowIndex);
            cellHeader = rowHeader.createCell(cellIndex);
            cellHeader.setCellValue("Tài khoản: "+r.getName());
            cellHeader.setCellStyle(headerCellStyle);
            contentCellStyleDate.setBorderBottom(BorderStyle.THIN);
            sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex+1 , 1, 1));
            cellHeader = rowHeader.createCell(cellIndex += 1);
            cellHeader.setCellValue("Tổng tiền: "+r.getPrice());
            cellHeader.setCellStyle(headerCellStyle);
            contentCellStyleDate.setBorderBottom(BorderStyle.THIN);
            contentCellStyleDate.setBorderRight(BorderStyle.THIN);
            sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex+1 , 2, 2));
            cellHeader = rowHeader.createCell(cellIndex += 1);
            cellHeader.setCellValue("Tổng số lượng: "+r.getTotal());
            cellHeader.setCellStyle(headerCellStyle);
            contentCellStyleDate.setBorderBottom(BorderStyle.THIN);
            rowIndex += 1;

            sheet.setColumnWidth(0, 7500);
            sheet.setColumnWidth(1, 7500);
            sheet.setColumnWidth(2, 7500);
            sheet.setColumnWidth(3, 7500);
            sheet.setColumnWidth(4, 7500);
            sheet.setColumnWidth(5, 7500);
            sheet.setColumnWidth(6, 7500);
            sheet.setColumnWidth(7, 7500);
        }
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setHeader("Content-Disposition", "attachment; filename=ReportsData.xls");
            ServletOutputStream out = response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return workbook;
    }

    @Override
    public List<ReceiptDto> searchPageByDto(SearchReportDto searchReportDto) {
        List<ReceiptDto> list = new ArrayList<>();
        if (searchReportDto.getTextSearch() != null && searchReportDto.getToDate() != null && searchReportDto.getFromDate() != null){
            Date fromDate;
            Date toDate;

            SimpleDateFormat formatter6 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                fromDate = formatter6.parse(searchReportDto.getFromDate().replace("T", " "));
                toDate = formatter6.parse(searchReportDto.getToDate().replace("T", " "));
                list = receiptRepository.listSearchByAll(searchReportDto.getTextSearch(),fromDate , toDate,searchReportDto.getStatus());
            } catch (ParseException e) {
                list = receiptRepository.listSearchByName(searchReportDto.getTextSearch(),searchReportDto.getStatus());
            }

        }else {
            list = receiptRepository.getAllByStatus(searchReportDto.getStatus());
        }
        return list;
    }

    @Override
    public boolean deleteById(Long id){
        try{
            receiptRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    @Override
     public Integer changStatus(Long id,Integer status) throws IOException {

        ReceiptDto receiptDto = findById(id);
        receiptDto.setStatus(status);
        createOrUpdate(receiptDto);
        return status;
     }

    @Override
    public List<ReceiptEntity> getAllByTimeNow(Date date) {
        List<ReceiptEntity> receiptEntities = receiptRepository.getAllByTimeNow(date);
        return receiptEntities;
    }

    @Override
    public void setActiveByTime() {
        Date date = new Date();
        List<ReceiptEntity> list = getAllByTimeNow(date);
        for (ReceiptEntity r : list){
            r.setStatus(4);
            receiptRepository.save(r);
        }
    }

    @Override
    public ReceiptDto findById(Long id) {
        return new ReceiptDto(receiptRepository.findById(id).get());
    }
}
