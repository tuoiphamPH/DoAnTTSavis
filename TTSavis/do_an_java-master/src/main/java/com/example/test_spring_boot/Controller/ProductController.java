package com.example.test_spring_boot.Controller;

import com.example.test_spring_boot.Configuration.Oauth2.CustomOauth2User;
import com.example.test_spring_boot.Configuration.Security.UserDetailCustom;
import com.example.test_spring_boot.Dto.*;
import com.example.test_spring_boot.Dto.SearchDto.SearchReportDto;
import com.example.test_spring_boot.Entity.ProductEntity;
import com.example.test_spring_boot.Entity.ProductHistory;
import com.example.test_spring_boot.Entity.UserEntity;
import com.example.test_spring_boot.Repository.*;
import com.example.test_spring_boot.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    MailService mailService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductHistoryRepostiory productHistoryRepostiory;

    @Autowired
    ProductHistoryService productHistoryService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ReceiptService receiptService;


    @Secured({"ROLE_ADMIN"})
    @GetMapping("/categoryProduct/index")
    public String getAllCategoryProduct(Model model, HttpServletRequest request){
        List<CategoryDto> categoryEntities = categoryRepository.getAllDto();
        model.addAttribute("categories", categoryEntities);
        model.addAttribute("categoryDto", new CategoryDto());
        model.addAttribute("productDto", new ProductDto());
        HttpSession session = request.getSession();
        String uzxc = session.getAttribute("nameUser").toString();
        model.addAttribute("nameUser", uzxc);
        return "view_admin/product/allProduct";
    }
    @Secured({"ROLE_ADMIN"})
    @GetMapping("/index")
    public String productAdmin(Model model, HttpServletRequest request){
        model.addAttribute("lstProduct", productService.getByPage(0,10));
        model.addAttribute("categories", categoryRepository.getAllDto());
        model.addAttribute("productDto", new ProductDto());
//        HttpSession session = request.getSession();
//        String uzxc = session.getAttribute("nameUser").toString();
        String usernamez = null;
        if(SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null){
            UserDetailCustom userDetailCustom = (UserDetailCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(userDetailCustom != null)
                usernamez = userDetailCustom.getUsername();
        }
        model.addAttribute("nameUser", usernamez);
        return "view_admin/product/index";
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/category/getAll/{id}")
    public String getAllproductByIdCategory(@PathVariable("id") Long id,Model model, HttpServletRequest request){
        model.addAttribute("lstProduct", productService.getByPage(0,10));
        model.addAttribute("categories", categoryRepository.getAllDto());
        model.addAttribute("categoryDto", categoryRepository.findById(id).get());
        model.addAttribute("productDto", new ProductDto());
        HttpSession session = request.getSession();
        String uzxc = session.getAttribute("nameUser").toString();
        model.addAttribute("nameUser", uzxc);
        return "view_admin/product/index";
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/product_detail/{id}")
    public String getProduct(Model model, @PathVariable("id") Long id, HttpServletRequest request){
        ProductDto getProduct = productRepository.getDtoById(id);
        if(getProduct == null){
            return "redirect:/product/index";
        }
        model.addAttribute("product",getProduct);
        List<CategoryDto> lstCategory = categoryRepository.getAllDto();
        model.addAttribute("categories",lstCategory);
        HttpSession session = request.getSession();
        String uzxc = session.getAttribute("nameUser").toString();
        model.addAttribute("nameUser", uzxc);
        return "view_admin/product/productDetail";
    }
    @Secured({"ROLE_ADMIN"})
    @PostMapping("/create_update_productAtCategory")
    public String addUpdateProductAtCategory(ProductDto dto) throws IOException {
        productService.createOrUpdate(dto);
        return "redirect:/product/category/getAll/"+ dto.getCategoryId();
    }
    @Secured({"ROLE_ADMIN"})
    @PostMapping("/create_update_product")
    public String addUpdateProduct(ProductDto dto) throws IOException {
        productService.createOrUpdate(dto);
        return "redirect:/product/categoryProduct/index";
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/remove_product/{id}")
    public String removeProduct(@PathVariable("id") Long id){
        ProductDto productDto = new ProductDto(productRepository.findById(id).get());
        ProductEntity productEntity = productRepository.findById(id).get();
        productEntity.setActive(0);
        productRepository.save(productEntity);
        return "redirect:/product/category/getAll/"+productDto.getCategoryId();
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/report")
    public String redirectReport(Model model, HttpServletRequest request){
        model.addAttribute("categories", categoryRepository.getAllDto());
        HttpSession session = request.getSession();
        String uzxc = session.getAttribute("nameUser").toString();
        model.addAttribute("nameUser", uzxc);
        model.addAttribute("yes3", "true");
        return "view_admin/report/productindex";
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/search_report_product")
    public ResponseEntity<?> reportProduct(Model model, SearchReportDto searchReportDto) throws ParseException {
        return ResponseEntity.ok(productHistoryService.getBySearch(searchReportDto));
    }

    //Admin and user
    @PostMapping("/search_report")
    public ResponseEntity<?> searchReport(Model model, SearchReportDto searchReportDto){
        return ResponseEntity.ok(productService.searchByDto(searchReportDto));
    }
    @PostMapping("/search_report_by_page")
    public ResponseEntity<?> searchReportByPage(Model model, SearchReportDto searchReportDto){
        return ResponseEntity.ok(productService.searchPageByDto(searchReportDto));
    }

///////////////////////////////////////Usser
    @GetMapping("/index_user")
    public String userIndex(Model model, HttpServletRequest request){
        model.addAttribute("categories", categoryRepository.getAllDto());
//        HttpSession session = request.getSession();
//        String uzxc = null;
//        try{
//            uzxc = session.getAttribute("nameUser").toString();
//        }
//        catch (Exception e){
//            uzxc = null;
//        }
//
//        model.addAttribute("nameUser", uzxc);
        String usernamez = null;
        if(SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null){
            try{
                UserDetailCustom userDetailCustom = (UserDetailCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                if(userDetailCustom != null)
                    usernamez = userDetailCustom.getUsername();
            }
            catch (Exception e){

            }
        }
        model.addAttribute("nameUser", usernamez);
        return "view_user/index";
    }

    @PostMapping("/add_cart/{id}")
    public ResponseEntity<?> addToCart(@PathVariable("id")Long id, HttpServletRequest request, CartDto cartDto,Model model){
        HttpSession session = request.getSession();
        List<CartDto> lstProduct = (List<CartDto>) session.getAttribute("cart");
        CartDto cartDto1 = null;
        if(lstProduct == null){
            lstProduct = new ArrayList<>();
            if(cartDto.getIdProduct() == null && cartDto.getTotalItem() == null){
                cartDto1 = new CartDto(id, 1);
                lstProduct.add(cartDto1);
            }
            else{
                lstProduct.add(cartDto);
            }

        }
        else{
            if(cartDto != null && cartDto.getTotalItem() != null && cartDto.getIdProduct() != null){
                cartDto1 = lstProduct.stream().filter(x->x.getIdProduct() == cartDto.getIdProduct()).findFirst().orElse(null);
                if(cartDto1 != null){
                    cartDto1.setTotalItem(cartDto1.getTotalItem() + cartDto.getTotalItem());
                }
                else {
                    cartDto1 = new CartDto(cartDto.getIdProduct(), cartDto.getTotalItem());
                    lstProduct.add(cartDto1);
                }
            }
            else{
                cartDto1 = lstProduct.stream().filter(x->x.getIdProduct() == id).findFirst().orElse(null);
                if(cartDto1 != null){
                    cartDto1.setTotalItem(cartDto1.getTotalItem() + 1);
                }
                else {
                    cartDto1 = new CartDto(id, 1);
                    lstProduct.add(cartDto1);
                }
            }
        }
        session.setAttribute("cart", lstProduct);
        String username = (String) session.getAttribute("nameUser");
        model.addAttribute("nameUser",username);
        return ResponseEntity.ok(true);
    }
    @GetMapping("/cart")
    public String gotoCart(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        List<CartDto> lstCart= (List<CartDto>) session.getAttribute("cart");
        List<ProductDto> lstProductDto = productService.getProductByCartDto(lstCart);
        model.addAttribute("lstProduct",lstProductDto);
        Double sumPrice = lstProductDto.stream().mapToDouble(o -> o.getPrice()).sum();
        model.addAttribute("receiptDto",new ReceiptDto());
        model.addAttribute("sumPrice",sumPrice + 30000);
        Long total =0l;
       if ( lstCart != null && !lstCart.isEmpty()){
           for(CartDto p : lstCart){
               total += p.getTotalItem();
           }
       }
        String username = (String) session.getAttribute("nameUser");
        model.addAttribute("nameUser",username);
        model.addAttribute("sumtotal",total);
        return "view_user/cart";
    }
    @GetMapping("/product_detail_user/{id}")
    public String getProductByUser(Model model, @PathVariable("id") Long id, HttpServletRequest request){
        ProductDto getProduct = productRepository.getDtoById(id);
        if(getProduct == null){
            return "redirect:/product/index_user";
        }
        model.addAttribute("product",getProduct);
        List<CategoryDto> lstCategory = categoryRepository.getAllDto();
        model.addAttribute("categories",lstCategory);
        model.addAttribute("rate", getProduct.getRating());
        if(getProduct.getReviewDto() != null && getProduct.getReviewDto().getId() != null)
            model.addAttribute("posts", postRepository.getDtoByReviewId(getProduct.getReviewDto().getId()));
        HttpSession session = request.getSession();
        String uzxc = null;
        try{
            uzxc = session.getAttribute("nameUser").toString();
        }
        catch (Exception e){
            uzxc = null;
        }
        model.addAttribute("nameUser", uzxc);
        return "view_user/productDetail";
    }
    @PostMapping("/pay")
    public String pay(HttpServletRequest request, Model model, ReceiptDto receiptDto) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getPrincipal().toString() == "anonymousUser")
            return "payError";
        else{
            HttpSession session = request.getSession();
            List<CartDto> lstCart =(List<CartDto>) session.getAttribute("cart");
            CustomOauth2User oAuth2User = null;
            UserDetailCustom userDetailsCustom = null;
            String email = "";
            try{
                userDetailsCustom = (UserDetailCustom) authentication.getPrincipal();
                UserEntity userEntity = userRepository.getByUsername(userDetailsCustom.getUsername());
                email = userEntity.getEmail();
            }
            catch (Exception e){
                if(userDetailsCustom == null){
                    oAuth2User = (CustomOauth2User) authentication.getPrincipal();
                    email = oAuth2User.getAttribute("email");
                }
            }
            UserEntity userEntity = userRepository.getByEmail(email);
//            mailService.sendMail(userEntity.getEmail(), "Thanh toán thành công!!!",null,null,null,lstCart);
            List<Long> lstLong = new ArrayList<>();
            List<ProductHistoryDto> productHistoryDtos = new ArrayList<>();
            ProductHistory productHistory = null;
            for (CartDto cartDto : lstCart){
                ProductEntity p = productRepository.getById(cartDto.getIdProduct());
                productHistory = new ProductHistory();
                productHistory.setUserEntity(userEntity);
                productHistory.setProductEntity(p);
                productHistory.setTotalItem(cartDto.getTotalItem());
                lstLong.add(cartDto.getIdProduct());
                productHistoryDtos.add(new ProductHistoryDto(productHistory));
            }
            receiptDto.setListProductDTO(productHistoryDtos);
            receiptDto.setPriceShip(30000d);
            receiptDto.setStatus(2);
            ReceiptDto receiptDto1 = receiptService.createOrUpdate(receiptDto);
            model.addAttribute("idProducts", lstLong);
            session.setAttribute("cart", null);
            String username = (String) session.getAttribute("nameUser");
            model.addAttribute("nameUser",username);
            return "orderSucess";
        }
    }
    @PostMapping("/removeCart")
    public String removeCart(@ModelAttribute("CartDto") CartDto cartDto , HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        List<CartDto> lstCart= (List<CartDto>) session.getAttribute("cart");
        for (int i =0 ; i < lstCart.size(); i++){
            if(lstCart.get(i).getIdProduct() == cartDto.getIdProduct()){
                lstCart.remove(lstCart.get(i));
            }
        }
        session.setAttribute("cart",lstCart);
        return "redirect:/product/cart";
    }

//    @PostMapping("/formEditCart")
//    public String editCart(@ModelAttribute("CartDto") CartDto cartDto , HttpServletRequest request, Model model){
//        HttpSession session = request.getSession();
//        List<CartDto> lstCart= (List<CartDto>) session.getAttribute("cart");
//        ProductHistory productHistory = new ProductHistory();
//        for (int i =0 ; i < lstCart.size(); i++){
//            if(lstCart.get(i).getIdProduct() == cartDto.getIdProduct()){
//                productHistory = productHistoryRepostiory.getById(lstCart.get(i).getIdProduct());
//                break;
//            }
//        }
//        model.addAttribute("product",productHistory);
//        return "";
//    }

    @PostMapping("/cartTotal")
    public ResponseEntity<?> cartTotal(Model model, SearchReportDto searchReportDto , HttpServletRequest request) throws ParseException {
        HttpSession session = request.getSession();
        ResultDto resultDto = new ResultDto();

        List<CartDto> lstCart= (List<CartDto>) session.getAttribute("cart");
        if(searchReportDto.getIdProduct() != null && searchReportDto.getIdProduct() >0){
            for(CartDto p : lstCart){
                if(p.getIdProduct() == searchReportDto.getIdProduct()){
                    p.setTotalItem(searchReportDto.getTotal());
                }
            }
        }
        List<ProductDto> lstProductDto = productService.getProductByCartDto(lstCart);
//        model.addAttribute("lstProduct",lstProductDto);
        resultDto.setProductDtos(lstProductDto);

        Double sumPrice = 0D;
        if(lstProductDto != null && lstProductDto.size() > 0){
            for(ProductDto item : lstProductDto){
                if(item.getSale() != null){
                    sumPrice += item.getPrice() / 100 * (100 - item.getSale());
                }
                else{
                    sumPrice += item.getPrice();
                }
            }
        }
        resultDto.setSumPrice(sumPrice);
//        model.addAttribute("receiptDto",new ReceiptDto());
//
//        model.addAttribute("sumPrice",sumPrice + 30000);
        Long total =0l;
        if ( lstCart != null && !lstCart.isEmpty()){
            for(CartDto p : lstCart){
                total += p.getTotalItem();
            }
        }
        resultDto.setLength(lstCart.size());
//        model.addAttribute("sumtotal",total);
        resultDto.setSumtotal(total);
        return ResponseEntity.ok(resultDto);
    }

}
