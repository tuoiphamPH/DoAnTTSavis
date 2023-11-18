package com.example.test_spring_boot.Configuration;

import com.example.test_spring_boot.Dto.UserDto;
import com.example.test_spring_boot.Entity.RoleEntity;
import com.example.test_spring_boot.Entity.UserEntity;
import com.example.test_spring_boot.Repository.RoleRepository;
import com.example.test_spring_boot.Repository.UserRepository;
import com.example.test_spring_boot.Service.MailService;
import com.example.test_spring_boot.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class LoginController {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserService userService;
    @Autowired
    MailService mailService;

//    @GetMapping("/add")
//    public String addUser(){
//        RoleEntity r = roleRepository.findById(1L).get();
//        Set<RoleEntity> roles = new HashSet<>();
//        roles.add(r);
//        UserEntity userEntity = new UserEntity();
//        userEntity.setEmail("quyenproxxxxx@gmail.com");
//        userEntity.setFullname("Nguyễn Hữu Quyền");
//        userEntity.setRoles(roles);
//        userEntity.setPassword(bCryptPasswordEncoder.encode("123456"));
//        userEntity.setUsername("admin");
//        userRepository.save(userEntity);
//        return "redirect:/login";
//    }
    @GetMapping("/resetADmin")
    public String addUser(){
        UserEntity userEntity = userRepository.getByUsername("admin");
        userEntity.setPassword(bCryptPasswordEncoder.encode("123456"));
        userRepository.save(userEntity);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginForm(Model model, HttpServletRequest request ){
//        List<UserEntity> ac = userRepository.findAll();
//        for(UserEntity item : ac){
//            item.setPassword(bCryptPasswordEncoder.encode("123456"));
//            userRepository.save(item);
//        }
        HttpSession session = request.getSession();
        String a = (String) session.getAttribute("error");
        String b = (String) session.getAttribute("dangnhaperror");
        model.addAttribute("error",a);
        if(a == null){
            model.addAttribute("dangnhaperror",b);
        }
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(){
        return "register";
    }

    @PostMapping("/register_account")
    public ResponseEntity<?> registerAccount(UserDto userDto){
        userDto = userService.registerAcc(userDto, bCryptPasswordEncoder);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/reset_account")
    public ResponseEntity<?> resetAccount(UserDto userDto){
        try{
            UserEntity userEntity = userRepository.getByEmail(userDto.getEmail());
            int min = 1;
            int max = 10000;
            int range = max - min + 1;
            int token = (int)(Math.random() * range);
            userEntity.setToken(token);
            userRepository.save(userEntity);
            mailService.resetPassWord(userDto.getEmail(),null,token);
            return ResponseEntity.ok(new UserDto(userEntity));
        }catch (Exception e){
            return ResponseEntity.ok(null);
        }
    }

    @GetMapping("/403")
    public String forbiedanView(){
        return "/403";
    }
}
