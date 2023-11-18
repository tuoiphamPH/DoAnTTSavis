package com.example.test_spring_boot.Service.ServiceImpl;

import com.example.test_spring_boot.Dto.CartDto;
import com.example.test_spring_boot.Dto.CategoryDto;
import com.example.test_spring_boot.Dto.ProductDto;
import com.example.test_spring_boot.Dto.ProductHistoryDto;
import com.example.test_spring_boot.Dto.SearchDto.SearchReportDto;
import com.example.test_spring_boot.Entity.CategoryEntity;
import com.example.test_spring_boot.Entity.ProductEntity;
import com.example.test_spring_boot.Entity.ReviewEntity;
import com.example.test_spring_boot.Repository.CategoryRepository;
import com.example.test_spring_boot.Repository.ProductRepository;
import com.example.test_spring_boot.Service.ProductHistoryService;
import com.example.test_spring_boot.Service.ProductService;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;
    @Value("${config.upload_folder}")
    String UPLOAD_FILE;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    EntityManager manager;

    @Autowired
    ProductHistoryService productHistoryService;

    @Override
    public Page<ProductDto> getByPage(Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<ProductEntity> pageEntity = productRepository.getByPage(pageable);
        return pageEntity.map(ProductDto::new);
    }

    @Override
    public ProductDto createOrUpdate(ProductDto productDto) throws IOException {
        ProductEntity productEntity1 = null;
        if(productDto.getId()!= null){
            productEntity1 = productRepository.findById(productDto.getId()).get();
        }
        if(productEntity1 == null){
            productEntity1 = new ProductEntity();
        }
        productEntity1.setName(productDto.getName());
        productEntity1.setPrice(productDto.getPrice());
        if(!productDto.getFile().isEmpty()){
            String realativeFilePath = null;
            Date date = new Date();
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int year = localDate.getYear();
            int month = localDate.getMonthValue();
            String subFolder = month +"_"+year;
            String fullUploadDir = UPLOAD_FILE + subFolder;
            File checkDir = new File(fullUploadDir);
            if(checkDir.exists() == true || checkDir.isFile() == true){
                checkDir.mkdir();
            }
            realativeFilePath = subFolder + Instant.now().getEpochSecond() + productDto.getFile().getOriginalFilename().replace(" ","_");
            Files.write(Paths.get(UPLOAD_FILE + realativeFilePath), productDto.getFile().getBytes());
            productEntity1.setImage(realativeFilePath);
        }
        CategoryEntity ce = null;
        if(productDto.getCategoryId() != null){
            ce = categoryRepository.getById(productDto.getCategoryId());
            productEntity1.setCategoryEntity(ce);
        }
        productEntity1.setSale(productDto.getSale());
        productEntity1 = productRepository.save(productEntity1);
        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setRating(5);
        productEntity1.setReviewEntity(reviewEntity);
        productEntity1.setActive(1);
        productEntity1 = productRepository.save(productEntity1);
        return new ProductDto(productEntity1);
    }

    @Override
    public List<ProductDto> searchByDto(SearchReportDto searchDto) {
        int pageIndex = searchDto.getPageIndex();
        int pageSize = searchDto.getPageSize();
        if (pageIndex > 0)
            pageIndex = pageIndex - 1;
        else
            pageIndex = 0;

        String sql = "select new com.example.test_spring_boot.Dto.ProductDto(p) from ProductEntity p where (1=1) and p.active = 1 ";
        String whereClause = "";

        if (searchDto.getCategoryId() != null && searchDto.getCategoryId() > 0) {
            whereClause += " and p.categoryEntity.id =:categoryId ";
        }
        if(searchDto.getRating() != null && searchDto.getRating()>0){
            whereClause += " and p.reviewEntity.rating =:rating ";
        }
        if(searchDto.getLstCategory() != null && searchDto.getLstCategory().size()>0){
            whereClause += " and p.categoryEntity.id in :lstCategory ";
        }
        if(searchDto.getLstStar() != null && searchDto.getLstStar().size()>0){
            whereClause += " and p.reviewEntity.rating in :lstStar ";
        }
        if(searchDto.getTextSearch() != null && searchDto.getTextSearch() != ""){
            whereClause += " and p.name LIKE :textSearch ";
        }
        if(searchDto.getSort() != null && searchDto.getSort() != ""){
            if(searchDto.getSort().equals("price")){
                whereClause += " order by p.price DESC ";
            }
            else{
                whereClause += " order by p.reviewEntity.rating DESC ";
            }
        }

        sql += whereClause;
        Query q = manager.createQuery(sql, ProductDto.class);

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

        q.setFirstResult((pageIndex) * pageSize);
        q.setMaxResults(pageSize);

        List<ProductDto> lstProduct = q.getResultList();
        return lstProduct;
    }

    @Override
    public Workbook exportBySearchDto(SearchReportDto searchReportDto , HttpServletResponse response) {
        HSSFWorkbook workbook = null;
        try {
            workbook = new HSSFWorkbook();
            CreationHelper createHelper = workbook.getCreationHelper();
            Sheet sheet = workbook.createSheet("Report");
            sheet.addMergedRegion(new CellRangeAddress(0, 0 , 0, 7));

            List<ProductHistoryDto> lstProductDto = productHistoryService.getBySearch(searchReportDto);

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
            cellHeader.setCellValue("THỐNG KÊ CÁC SẢN PHẨM ĐÃ BÁN");
            cellHeader.setCellStyle(headerCellStyle);

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
            sheet.setColumnWidth(0, 7500);
            sheet.setColumnWidth(1, 7500);
            sheet.setColumnWidth(2, 7500);
            sheet.setColumnWidth(3, 7500);
            sheet.setColumnWidth(4, 7500);
            sheet.setColumnWidth(5, 7500);
            sheet.setColumnWidth(6, 7500);
            sheet.setColumnWidth(7, 7500);

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
    public List<ProductDto> getProductByCartDto(List<CartDto> lstCart) {
        List<ProductDto> lstProductDto = new ArrayList<>();
        if(lstCart != null && lstCart.size() > 0){
            ProductEntity p = null;
            for(CartDto cartDto : lstCart){
                if(cartDto.getIdProduct() != null && cartDto.getTotalItem() != null){
                    p = productRepository.findById(cartDto.getIdProduct()).get();
                    if(p != null){
                        lstProductDto.add(new ProductDto(p, cartDto));
                    }
                }
            }
        }
        return lstProductDto;
    }

    @Override
    public Page<ProductDto> searchPageByDto(SearchReportDto searchDto) {
        int pageIndex = searchDto.getPageIndex();
        int pageSize = searchDto.getPageSize();
        if (pageIndex > 0)
            pageIndex = pageIndex - 1;
        else
            pageIndex = 0;
        Pageable pageable = PageRequest.of(pageIndex, pageSize);

        String sql = "select new com.example.test_spring_boot.Dto.ProductDto(p) from ProductEntity p where (1=1) and p.active = 1 ";
        String sqlCount = "select COUNT(p.id) from ProductEntity p where (1=1) and p.active = 1";
        String whereClause = "";

        if (searchDto.getCategoryId() != null && searchDto.getCategoryId() > 0) {
            whereClause += " and p.categoryEntity.id =:categoryId ";
        }
        if(searchDto.getRating() != null && searchDto.getRating()>0){
            whereClause += " and p.reviewEntity.rating =:rating ";
        }
        if(searchDto.getLstCategory() != null && searchDto.getLstCategory().size()>0){
            whereClause += " and p.categoryEntity.id IN :lstcategory ";
        }
        if(searchDto.getLstStar() != null && searchDto.getLstStar().size()>0){
            whereClause += " and p.reviewEntity.rating IN :lststar ";
        }
        if(searchDto.getTextSearch() != null && searchDto.getTextSearch() != ""){
            whereClause += " and p.name LIKE :textSearch ";
        }
        if(searchDto.getSort() != null && searchDto.getSort() != ""){
            if(searchDto.getSort().equals("price")){
                whereClause += " order by p.price DESC, p.CreateDate DESC ";
            }
            else{
                whereClause += " order by p.reviewEntity.rating DESC, p.CreateDate DESC ";
            }

        }
        else{
            whereClause += " order by p.CreateDate DESC ";
        }

        sql += whereClause;
        sqlCount += whereClause;
        Query q = manager.createQuery(sql, ProductDto.class);
        Query qCount = manager.createQuery(sqlCount);

        if (searchDto.getCategoryId() != null && searchDto.getCategoryId() > 0) {
            q.setParameter("categoryId", searchDto.getCategoryId());
            qCount.setParameter("categoryId", searchDto.getCategoryId());
        }
        if(searchDto.getRating() != null && searchDto.getRating()>0){
            q.setParameter("rating", searchDto.getRating());
            qCount.setParameter("rating", searchDto.getRating());
        }
        if(searchDto.getLstCategory() != null && searchDto.getLstCategory().size()>0){
            q.setParameter("lstcategory", searchDto.getLstCategory());
            qCount.setParameter("lstcategory", searchDto.getLstCategory());
        }
        if(searchDto.getLstStar() != null && searchDto.getLstStar().size()>0){
            q.setParameter("lststar", searchDto.getLstStar());
            qCount.setParameter("lststar", searchDto.getLstStar());
        }
        if(searchDto.getTextSearch() != null && searchDto.getTextSearch() != ""){
            q.setParameter("textSearch", "%"+searchDto.getTextSearch()+"%");
            qCount.setParameter("textSearch", "%"+searchDto.getTextSearch()+"%");
        }

        q.setFirstResult((pageIndex) * pageSize);
        q.setMaxResults(pageSize);
        List<ProductDto> lstProduct = q.getResultList();
        Page<ProductDto> page = null;
        if(lstProduct.size() ==0){
             page = new PageImpl<ProductDto>(lstProduct, pageable, 0);
        }
        else
            page = new PageImpl<ProductDto>(lstProduct, pageable, (long) qCount.getSingleResult());
        return page;
    }

    @Override
    public List<ProductEntity> findAllByListId(List<Long> id) {
        return null;
    }

    @Override
    public Page<ProductDto> getProductByCartDto(List<CartDto> lstCart, Pageable pageable) {
        return null;
    }

}
