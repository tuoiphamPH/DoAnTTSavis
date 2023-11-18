package com.example.test_spring_boot.Controller;


import com.example.test_spring_boot.Configuration.Security.UserDetailCustom;
import com.example.test_spring_boot.Dto.*;
import com.example.test_spring_boot.Dto.SearchDto.SearchReportDto;
import com.example.test_spring_boot.Repository.CategoryRepository;
import com.example.test_spring_boot.Repository.ReceiptRepository;
import com.example.test_spring_boot.Repository.UserRepository;
import com.example.test_spring_boot.Service.MailService;
import com.example.test_spring_boot.Service.ReceiptService;
import com.example.test_spring_boot.Service.ServiceImpl.ProductHistoryServiceImpl;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/receipt")
public class ReceiptController {
    @Autowired
    ProductHistoryServiceImpl productHistoryService;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ReceiptService receiptService;
    @Autowired
    ReceiptRepository receiptRepository;
    @Autowired
    UserRepository userService;
    @Autowired
    MailService mailService;

    @PostMapping("/search_report_product")
    public ResponseEntity<?> searchPageReceipt(SearchReportDto searchReportDto, HttpServletResponse response){
        Page<ReceiptDto> resultDTO = receiptService.searchByDto(searchReportDto);
        return ResponseEntity.ok(resultDTO);
    }
    @GetMapping("/status/{id}")
    @Secured({"ROLE_ADMIN"})
    public String home(Model model, @PathVariable("id") Long id,HttpServletRequest request){
        List<CategoryDto> lstCategory = categoryRepository.getAllDto();
        model.addAttribute("categories",lstCategory);
        receiptService.setActiveByTime();
        String url = "view_admin/report/index";
        if(id == 3){
            url = "view_admin/report/Receipt-pending-ship-view";
        }else if(id==4){
            url = "view_admin/report/Receipt-pending-pay-view";
        }else if (id==2){
            url="view_admin/report/Receipt-pending-confirm-view";
        }else if (id==5){
            url="view_admin/report/Receipt-Detroy-view";
        }else if(id == 1){
            url = "view_admin/report/Receipt-susscess-view";
        }
        HttpSession session = request.getSession();
        String uzxc = session.getAttribute("nameUser").toString();
        model.addAttribute("nameUser", uzxc);
        model.addAttribute("yes1", "true");
        return url;
    }
    @PostMapping("/export_report_by_search")
    public Workbook exportBySearchReport(Model model, SearchReportDto searchReportDto, HttpServletResponse response){
       List<ReceiptDto> resultDTO = receiptService.searchPageByDto(searchReportDto);
        return productHistoryService.exportBySearchDto(resultDTO, response);
    }

    @PostMapping("/detail")
    @Secured({"ROLE_ADMIN"})
    public String detail(Model model, ReceiptDto receiptDto,HttpServletRequest request){
        ReceiptDto r = receiptService.findById(receiptDto.getId());
        List<CategoryDto> lstCategory = categoryRepository.getAllDto();
        model.addAttribute("receipt",r);
        model.addAttribute("categories",lstCategory);
        HttpSession session = request.getSession();
        String uzxc = session.getAttribute("nameUser").toString();
        model.addAttribute("nameUser", uzxc);
        return "view_admin/report/detail-view";
    }
    @PostMapping("/user/detail")
    public String userDetail(Model model, ReceiptDto receiptDto , HttpServletRequest request){
        ReceiptDto r = receiptService.findById(receiptDto.getId());
        List<CategoryDto> lstCategory = categoryRepository.getAllDto();
        model.addAttribute("receipt",r);
        model.addAttribute("categories",lstCategory);
        HttpSession session = request.getSession();
        String uzxc = session.getAttribute("nameUser").toString();
        model.addAttribute("nameUser", uzxc);
        return "view_user/receiptDetail";
    }
    @PostMapping("/delete")
    @Secured({"ROLE_ADMIN"})
    public String deleteById(Model model, ReceiptDto receiptDto,HttpServletRequest request) throws IOException {
        receiptService.changStatus(receiptDto.getId(), 5);
        return "redirect:/receipt/status/"+receiptService.findById(receiptDto.getId()).getStatus();
    }
    @PostMapping("/ship")
    @Secured({"ROLE_ADMIN"})
    public String ship(Model model, ReceiptDto receiptDto,HttpServletRequest request) throws IOException {
        receiptService.changStatus(receiptDto.getId(), 3);
        HttpSession session = request.getSession();
        String uzxc = session.getAttribute("nameUser").toString();
        model.addAttribute("nameUser", uzxc);
        return "redirect:/receipt/status/3";
    }
    @PostMapping("/pay")
    @Secured({"ROLE_ADMIN"})
    public String pay(Model model, ReceiptDto receiptDto ,HttpServletRequest request) throws IOException {
        receiptService.changStatus(receiptDto.getId(), 1);
        HttpSession session = request.getSession();
        ReceiptDto receiptDto1 = receiptService.findById(receiptDto.getId());
        String uzxc = session.getAttribute("nameUser").toString();
        model.addAttribute("nameUser", uzxc);

        List<CartDto> cartDtos = new ArrayList<>();
        for(ProductHistoryDto r: receiptDto1.getListProductDTO()){
            CartDto cartDto = new CartDto();
            cartDto.setTotalItem(r.getTotalItem());
            cartDto.setIdProduct(r.getProductDto().getId());
            cartDtos.add(cartDto);
        }
        mailService.sendMail(receiptDto1.getFullname(), "Thanh toán thành công!!!",null,null,null,cartDtos);
        return "redirect:/receipt/status/1";
    }

    @GetMapping ("/user")
    public String userReceipt(Model model, @RequestParam("username") String username, HttpSession session) throws IOException {
       UserDetailCustom userDetailCustom = (UserDetailCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       String usernamez = userDetailCustom.getUsername();
        UserDto userDto = new UserDto(userService.getByUsername(usernamez));
        List<ReceiptDto> a = receiptRepository.findByUserName(usernamez);
       model.addAttribute("nameUser",usernamez);
        session.setAttribute("nameUser",userDto.getUsername());
        model.addAttribute("listReceipt",a);
        return "view_user/receipt";
    }
}
