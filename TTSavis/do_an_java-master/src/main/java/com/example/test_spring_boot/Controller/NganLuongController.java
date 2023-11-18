package com.example.test_spring_boot.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/nganluong")
public class NganLuongController {

    @GetMapping("/success")
    public String success(){
        return "nganluong/success";
    }

    @GetMapping("/error")
    public String error(){
        return "nganluong/error";
    }

}
