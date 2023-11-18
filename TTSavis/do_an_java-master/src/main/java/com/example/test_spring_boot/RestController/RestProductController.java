package com.example.test_spring_boot.RestController;

import com.example.test_spring_boot.Dto.CartDto;
import com.example.test_spring_boot.Dto.ProductDto;
import com.example.test_spring_boot.Dto.ProductHistoryDto;
import com.example.test_spring_boot.Dto.SearchDto.SearchReportDto;
import com.example.test_spring_boot.Repository.*;
import com.example.test_spring_boot.Service.MailService;
import com.example.test_spring_boot.Service.ProductHistoryService;
import com.example.test_spring_boot.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/product")
public class RestProductController {
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

    @GetMapping("/product_detail/{id}")
    public ResponseEntity<?> getProduct(@PathVariable("id") Long id){
        ProductDto getProduct = productRepository.getDtoById(id);
        return ResponseEntity.ok().body(getProduct);
    }
    @PostMapping("/create_update_product")
    public ProductDto addUpdateProduct(ProductDto dto) throws IOException {
        return productService.createOrUpdate(dto);
    }

    @GetMapping("/remove_product/{id}")
    public Boolean removeProduct(@PathVariable("id") Long id){
        productRepository.deleteById(id);
        return true;
    }

    @PostMapping("/search_report_product")
    public List<ProductHistoryDto> reportProduct(SearchReportDto searchReportDto) throws ParseException {
        return productHistoryService.getBySearch(searchReportDto);
    }

    @PostMapping("/search_report")
    public List<ProductDto> searchReport(@RequestBody SearchReportDto searchReportDto){
        return productService.searchByDto(searchReportDto);
    }
    @PostMapping("/search_report_by_page")
    public Page<ProductDto> searchReportByPage(@RequestBody  SearchReportDto searchReportDto){
        return productService.searchPageByDto(searchReportDto);
    }



    @PostMapping("/add_cart/{id}")
    public List<CartDto> addToCart(@PathVariable("id")Long id, HttpServletRequest request, CartDto cartDto){
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
        return lstProduct;
    }
    @GetMapping("/cart")
    public List<ProductDto> gotoCart(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        List<CartDto> lstCart= (List<CartDto>) session.getAttribute("cart");
        List<ProductDto> lstProductDto = productService.getProductByCartDto(lstCart);
        return lstProductDto;
    }

}
