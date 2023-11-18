package com.example.test_spring_boot.Service.ServiceImpl;

import com.example.test_spring_boot.Dto.CartDto;
import com.example.test_spring_boot.Dto.ProductDto;
import com.example.test_spring_boot.Dto.ProductHistoryDto;
import com.example.test_spring_boot.Dto.ReceiptDto;
import com.example.test_spring_boot.Dto.SearchDto.ResultDTO;
import com.example.test_spring_boot.Dto.SearchDto.SearchReportDto;
import com.example.test_spring_boot.Entity.ProductHistory;
import com.example.test_spring_boot.Repository.ProductHistoryRepostiory;
import com.example.test_spring_boot.Repository.UserRepository;
import com.example.test_spring_boot.Service.ProductHistoryService;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductHistoryServiceImpl implements ProductHistoryService {

    @Autowired
    ProductHistoryRepostiory productHistoryRepostioryl;

    @Autowired
    EntityManager manager;
    @Autowired
    UserRepository userRepository;


    @Override
    public List<ProductHistoryDto> getBySearch(SearchReportDto searchDto) throws ParseException {
        int pageIndex = searchDto.getPageIndex();
//        int pageSize = searchDto.getPageSize();
        int pageSize = 999999;
        if (pageIndex > 0)
            pageIndex = pageIndex - 1;
        else
            pageIndex = 0;

        String sql = "select new com.example.test_spring_boot.Dto.ProductHistoryDto(p) from ProductHistory p where (1=1)";
        String whereClause = "";

        if (searchDto.getCategoryId() != null && searchDto.getCategoryId() > 0) {
            whereClause += " and p.productEntity.categoryEntity.id =:categoryId ";
        }
        if(searchDto.getRating() != null && searchDto.getRating()>0){
            whereClause += " and p.productEntity.reviewEntity.rating =:rating ";
        }
        if(searchDto.getLstCategory() != null && searchDto.getLstCategory().size()>0){
            whereClause += " and p.productEntity.categoryEntity.id in :lstCategory ";
        }
        if(searchDto.getLstStar() != null && searchDto.getLstStar().size()>0){
            whereClause += " and p.productEntity.reviewEntity.rating in :lstStar ";
        }
        if(searchDto.getTextSearch() != null && searchDto.getTextSearch() != ""){
            whereClause += " and p.userEntity.fullname LIKE :textSearch ";
        }
        if(searchDto.getFromDate() != null && searchDto.getFromDate() != ""){
            whereClause += " and p.CreateDate >= :fromDate ";
        }
        if(searchDto.getToDate() != null && searchDto.getToDate() != ""){
            whereClause += " and p.CreateDate <= :toDate ";
        }

        sql += whereClause;
        Query q = manager.createQuery(sql, ProductHistoryDto.class);

        if (searchDto.getCategoryId() != null && searchDto.getCategoryId() > 0) {
            q.setParameter("categoryId", searchDto.getCategoryId());
        }
        if(searchDto.getRating() != null && searchDto.getRating()>0){
            q.setParameter("rating", searchDto.getRating());
        }
        if(searchDto.getLstCategory() != null && searchDto.getLstCategory().size()>0){
            q.setParameter("lstCategory", searchDto.getLstCategory());
        }
        if(searchDto.getLstStar() != null && searchDto.getLstStar().size()>0){
            q.setParameter("lstStar", searchDto.getLstStar());
        }
        if(searchDto.getTextSearch() != null && searchDto.getTextSearch() != ""){
            q.setParameter("textSearch", "%"+searchDto.getTextSearch()+"%");
        }
        if(searchDto.getFromDate() != null && searchDto.getFromDate() != ""){
            String format = "yyyy-MM-dd HH:mm";
            SimpleDateFormat df = new SimpleDateFormat(format);
            Date from = df.parse(searchDto.getFromDate().replace("T", " "));
            q.setParameter("fromDate", from);
        }
        if(searchDto.getToDate() != null && searchDto.getToDate() != ""){
            String format = "yyyy-MM-dd HH:mm";
            SimpleDateFormat df = new SimpleDateFormat(format);
            Date to = df.parse(searchDto.getToDate().replace("T", " "));
            q.setParameter("toDate", to);
        }

        q.setFirstResult((pageIndex) * pageSize);
        q.setMaxResults(pageSize);

        List<ProductHistoryDto> lstProduct = q.getResultList();

        return lstProduct;
    }

    @Override
    public List<ProductHistory> getALlGruopByProductHistory() {
        return productHistoryRepostioryl.getALlGroupByProductHistory();
    }

    @Override
    public List<ReceiptDto> getAll() {
        List<ProductHistory> ProductHistoryGruopBy = getALlGruopByProductHistory();
        List<ReceiptDto> receiptDtos =  new ArrayList<>();
        for(ProductHistory p : ProductHistoryGruopBy){
            Double price = 0d;
            int total = 0;
            List<ProductHistory> productHistories = productHistoryRepostioryl.getProductHistoryByTimeCreateAndIdUser(p.getCreateDate(),p.getUserEntity().getId());
            for (ProductHistory pp : productHistories){
                price += pp.getProductEntity().getPrice()*pp.getTotalItem();
                total += pp.getTotalItem();
            }
            ReceiptDto receiptDto = new ReceiptDto();
            receiptDto.setName(p.getUserEntity().getUsername());
            receiptDto.setCreateTime(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(p.getCreateDate()));
            receiptDto.setPrice(price);
            receiptDto.setTotal(total);
            receiptDto.setListProductDTO(productHistories.stream().map(x -> new ProductHistoryDto(x)).collect(Collectors.toList()));
            receiptDtos.add(receiptDto);
        }
        return receiptDtos;
    }

    @Override
    public ResultDTO searchReceipDto(SearchReportDto searchReportDto , HttpServletResponse response) {
        ResultDTO resultDTO = new ResultDTO();
        int pageIndex = searchReportDto.getPageIndex();
        int pageSize = 5;


        List<ReceiptDto> receiptDtos = new ArrayList<>();
        List<ReceiptDto> receiptDtoAll = getAll();

        if (searchReportDto.getTextSearch() != null && searchReportDto.getToDate() != null && searchReportDto.getFromDate() != null){
            Date fromDate;
            Date toDate;

            SimpleDateFormat formatter6 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                fromDate = formatter6.parse(searchReportDto.getFromDate().replace("T", " "));
                toDate = formatter6.parse(searchReportDto.getToDate().replace("T", " "));
                receiptDtoAll.stream().forEach(x -> {
                    Date timeCreate;
                    try {
                        timeCreate = formatter6.parse(x.getCreateTime().replace("T", " "));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    if (timeCreate.after(toDate) && timeCreate.before(fromDate) && x.getName().contains(searchReportDto.getTextSearch())) {
                        receiptDtos.add(x);
                    }
                });
            } catch (ParseException e) {
                receiptDtoAll.stream().forEach(x -> {
                    if (x.getName().contains(searchReportDto.getTextSearch())) {
                        receiptDtos.add(x);
                    }
                });
            }

        }
        if (searchReportDto.getTextSearch() == null && searchReportDto.getToDate() == null && searchReportDto.getFromDate() == null){
            getAll().stream().forEach(x ->{
                receiptDtos.add(x);
            });
        }
        List<ReceiptDto> dtoList = new ArrayList<>();
        pageIndex = pageIndex -1;
        if(pageIndex == 0){
            try{
                dtoList = receiptDtos.subList(0,4);
            }catch (Exception e){
                dtoList = receiptDtos.subList(0,receiptDtos.size());
            }
        }else {
            try{
                dtoList = receiptDtos.subList(pageIndex*5,pageIndex*5 + 4);
            }catch (Exception e){
                dtoList = receiptDtos.subList(pageIndex*5,receiptDtos.size() );
            }

        }
        if(searchReportDto.getExport() != null && searchReportDto.getExport().contains("yes")){
            resultDTO.setReceiptDtos(receiptDtos);
        }else {
            resultDTO.setReceiptDtos(dtoList);
        }
        int pageTotal = (int) Math.ceil((double) receiptDtos.size() / pageSize);
        resultDTO.setTotalPage(pageTotal);
        resultDTO.setNumber(pageIndex);

        if(searchReportDto.getNameUser() != null && searchReportDto.getTimeCreate() != null){
            for (ReceiptDto r : resultDTO.getReceiptDtos()){
                if (r.getCreateTime().contains(searchReportDto.getTimeCreate()) && r.getName().contains(searchReportDto.getNameUser())){
                    resultDTO.setReceiptDto(r);
                    break;
                }
            }
        }
        return resultDTO;
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
        Double price = 0D;
        Integer totalProduct = 0;
        Integer totalRecept = receiptDtos.size();

        for (ReceiptDto r : receiptDtos){
            price += r.getPrice();
            totalProduct += r.getTotal();
        }
        rowHeader = sheet.createRow(rowIndex +=2);
        cellIndex = 0;
        rowHeader = sheet.createRow(rowIndex);
        cellHeader = rowHeader.createCell(cellIndex);
        cellHeader.setCellValue("Tổng số lượng hóa đơn: "+totalRecept);
        cellHeader.setCellStyle(headerCellStyle);

        cellHeader = rowHeader.createCell(cellIndex +=1);
        cellHeader.setCellValue("Tổng số sản phẩm: " +totalProduct);
        cellHeader.setCellStyle(headerCellStyle);

        cellHeader = rowHeader.createCell(cellIndex += 1);
        cellHeader.setCellValue("Tổng tiền: "+price);
        cellHeader.setCellStyle(headerCellStyle);
        rowIndex += 1;
        Integer numberOfItem = 0;
        for (ReceiptDto r :receiptDtos){
                List<ProductHistoryDto> lstProductDto = r.getListProductDTO();
            rowHeader = sheet.createRow(rowIndex +=1);
            cellIndex = 0;

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
                numberOfItem =0;
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
                rowIndex += 1;
                cellIndex = 0;
//                sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex+1 , 0, 0));
                rowHeader = sheet.createRow(rowIndex);
                cellHeader = rowHeader.createCell(cellIndex);
                cellHeader.setCellValue("Tài khoản: "+r.getName());
                cellHeader.setCellStyle(headerCellStyle);
                contentCellStyleDate.setBorderBottom(BorderStyle.THIN);
//                sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex+1 , 1, 1));
                cellHeader = rowHeader.createCell(cellIndex += 1);
                cellHeader.setCellValue("Tổng tiền: "+r.getPrice());
                cellHeader.setCellStyle(headerCellStyle);
                contentCellStyleDate.setBorderBottom(BorderStyle.THIN);
//                sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex+1 , 2, 2));
                cellHeader = rowHeader.createCell(cellIndex += 1);
                cellHeader.setCellValue("Tổng số lượng: "+r.getTotal());
                cellHeader.setCellStyle(headerCellStyle);
                contentCellStyleDate.setBorderBottom(BorderStyle.THIN);
                rowIndex += 1;
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
    public Page<ProductDto> getProductByCartDto(List<CartDto> lstCart, Pageable pageable) {
        return null;
    }


}
