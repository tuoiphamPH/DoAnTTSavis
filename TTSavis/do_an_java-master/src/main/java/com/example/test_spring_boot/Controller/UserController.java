package com.example.test_spring_boot.Controller;

import com.example.test_spring_boot.Common.Config;
import com.example.test_spring_boot.Dto.*;
import com.example.test_spring_boot.Dto.SearchDto.SearchReportDto;
import com.example.test_spring_boot.Entity.ProductHistory;
import com.example.test_spring_boot.Entity.ReceiptEntity;
import com.example.test_spring_boot.Entity.UserEntity;
import com.example.test_spring_boot.Repository.CategoryRepository;
import com.example.test_spring_boot.Repository.ReceiptRepository;
import com.example.test_spring_boot.Repository.UserRepository;
import com.example.test_spring_boot.Service.MailService;
import com.example.test_spring_boot.Service.ReceiptService;
import com.example.test_spring_boot.Service.UserService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RequestMapping(value = "/user")
@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    MailService mailService;
    @Autowired
    ReceiptService receiptService;
    @Autowired
    ReceiptRepository receiptRepository;


    @Secured({"ROLE_ADMIN"})
    @GetMapping("/manager_user")
    public String managerUser(Model model, HttpServletRequest request){
        List<UserDto> userDtoList = userRepository.getAllDto();
        model.addAttribute("lstUser", userDtoList);
        model.addAttribute("userDto", new UserDto());
        HttpSession session = request.getSession();
        String uzxc = session.getAttribute("nameUser").toString();
        model.addAttribute("nameUser", uzxc);
        List<CategoryDto> lstCategory = categoryRepository.getAllDto();
        model.addAttribute("categories",lstCategory);
        return "view_admin/user/index";
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/create_update_user")
    public String registerAccount(UserDto userDto ,Model model){
        if(userDto.getId() != null){
            userDto = userService.updateAcc(userDto, bCryptPasswordEncoder);
        }else {
            userDto = userService.registerAcc(userDto,bCryptPasswordEncoder);
        }
        List<CategoryDto> lstCategory = categoryRepository.getAllDto();
        model.addAttribute("categories",lstCategory);
        return "redirect:/user/manager_user";
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/user_detail/{id}")
    public String user_detail(@PathVariable("id") Long id, Model model, HttpServletRequest request){
        model.addAttribute("userDto", new UserDto(userRepository.getById(id)));
        HttpSession session = request.getSession();
        String uzxc = session.getAttribute("nameUser").toString();
        model.addAttribute("nameUser", uzxc);
        List<CategoryDto> lstCategory = categoryRepository.getAllDto();
        model.addAttribute("categories",lstCategory);
        return "view_admin/user/user_detail";
    }

    @GetMapping("/remove_user/{id}")
    @Secured({"ROLE_ADMIN"})
    public String removeUser(@PathVariable("id") Long id){
        List<CategoryDto> lstCategory = categoryRepository.getAllDto();
        UserEntity userEntity = userRepository.findById(id).get();
        userEntity.setActive(0);
        userRepository.save(userEntity);
        return "redirect:/user/manager_user";
    }
    @PostMapping("/sendMailResetPassWord")
    public String sendMailResetPassWord(@ModelAttribute UserDto userDto){
        int max = 10000;
        int min = 1;
        int range = max - min + 1;
        int token = (int)(Math.random() * range);
        UserEntity userEntity = userRepository.getByEmail(userDto.getEmail());

        if(userEntity != null){
            userEntity.setToken(token);
            userRepository.save(userEntity);
            mailService.resetPassWord(userDto.getEmail(),"Thông báo đăng ký lại mật khẩu",token);
            return "redirect:/";
        }else {
            return "redirect:/404";
        }
    }
    @GetMapping("/formEmail")
    public String formEmail(){
        return "/view_user/formEmail";
    }

    @GetMapping("/reset-password")
    public String formResetPassWord(@RequestParam("token") int token ,@RequestParam("email") String email,Model model){
        UserEntity userEntity = userRepository.getByEmailAndToken(email,token);
        model.addAttribute("userdto",new UserDto(userEntity));
        if(userEntity != null){
            return "/view_user/formResetPassWord";
        }else {
            return "redirect:/403";
        }
    }

    @PostMapping("/resetpassWord")
    public String formResetPassWord(UserDto userDto){
        UserEntity userEntity = userRepository.findById(userDto.getId()).get();
        userEntity.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userRepository.save(userEntity);
        return "redirect:/login";
    }

    @GetMapping("/pay")
    public String pay(Model model, @RequestParam("id") Long id, HttpServletRequest request) throws IOException {
        receiptService.changStatus(id, 1);
        ReceiptDto receiptDto1 = receiptService.findById(id);
                HttpSession session = request.getSession();
        String uzxc = session.getAttribute("nameUser").toString();
        model.addAttribute("nameUser", uzxc);
        model.addAttribute("idReceipt", id);
        UserEntity userEntity = userRepository.getByUsername(uzxc);

        List<CartDto> cartDtos = new ArrayList<>();
        for(ProductHistoryDto r: receiptDto1.getListProductDTO()){
            CartDto cartDto = new CartDto();
            cartDto.setTotalItem(r.getTotalItem());
            cartDto.setIdProduct(r.getProductDto().getId());
            cartDtos.add(cartDto);
        }
        try{
            mailService.sendMail(receiptDto1.getFullname(), "Thanh toán thành công!!!",null,null,null,cartDtos);
        }catch (Exception e){
            System.out.printf("emial ko coos");
        }
        return "paySuccess";
    }
    @PostMapping("/payvn")
    public String payvn(ReceiptDto receiptDto) throws UnsupportedEncodingException{
        ReceiptDto receiptDto1 = receiptService.findById(receiptDto.getId());

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_OrderInfo = receiptDto1.getFullname();
        String orderType = "100000";
        String vnp_TxnRef = Config.getRandomNumber(8);
        String vnp_IpAddr = "127.0.0.1";
        String vnp_TmnCode = Config.vnp_TmnCode;

        int amount = (int) (receiptDto1.getPrice() *100);
        Map vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        String bank_code = "NCB";
        if (bank_code != null && !bank_code.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bank_code);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = "vn";
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_Returnurl+receiptDto.getId());
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());

        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        //Add Params of 2.1.0 Version
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        //Billing
        vnp_Params.put("vnp_Bill_Mobile", "09123891");
        vnp_Params.put("vnp_Bill_Email", "huuquangnh@gmail.com");
        String fullName = "Quang dz".trim();
        if (fullName != null && !fullName.isEmpty()) {
            int idx = fullName.indexOf(' ');
            String firstName = fullName.substring(0, idx);
            String lastName = fullName.substring(fullName.lastIndexOf(' ') + 1);
            vnp_Params.put("vnp_Bill_FirstName", firstName);
            vnp_Params.put("vnp_Bill_LastName", lastName);

        }
        vnp_Params.put("vnp_Bill_Address", "Test thui");
        vnp_Params.put("vnp_Bill_City", "Ha Noi");
        vnp_Params.put("vnp_Bill_Country", "Viet Nam");
//        if (req.getParameter("txt_bill_state") != null && !req.getParameter("txt_bill_state").isEmpty()) {
//            vnp_Params.put("vnp_Bill_State", req.getParameter("txt_bill_state"));
//        }
        // Invoice
        vnp_Params.put("vnp_Inv_Phone", "0963089510");
        vnp_Params.put("vnp_Inv_Email", "coolquanghuu@gmail.com");
        vnp_Params.put("vnp_Inv_Customer","nguyen huu quang");
        vnp_Params.put("vnp_Inv_Address", "Ha Noi");
        vnp_Params.put("vnp_Inv_Company", "CY");
        vnp_Params.put("vnp_Inv_Taxcode", "32222");
//        vnp_Params.put("vnp_Inv_Type", req.getParameter("cbo_inv_type"));
        //Build data to hash and querystring
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
        com.google.gson.JsonObject job = new JsonObject();
        job.addProperty("code", "00");
        job.addProperty("message", "success");
        job.addProperty("data", paymentUrl);
        Gson gson = new Gson();
        return  "redirect:"+paymentUrl;
    }
    @Secured({"ROLE_ADMIN"})
    @PostMapping(value = "/searchPage")
    public ResponseEntity<?> searchReportByPage(SearchReportDto searchReportDto){
        Page<UserDto> a = userService.findPage(searchReportDto);
        return ResponseEntity.ok(a);
    }
}
