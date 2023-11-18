package com.example.test_spring_boot.Controller;

import com.example.test_spring_boot.Dto.CategoryDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homePage(){
        return "redirect:/product/index_user";
    }
}
