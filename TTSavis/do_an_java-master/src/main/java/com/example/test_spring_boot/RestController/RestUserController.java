package com.example.test_spring_boot.RestController;

import com.example.test_spring_boot.Dto.UserDto;
import com.example.test_spring_boot.Repository.UserRepository;
import com.example.test_spring_boot.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class RestUserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/get_all_user")
    public List<UserDto> managerUser(){
        return userRepository.findAll().stream().map(x-> new UserDto(x)).collect(Collectors.toList());
    }

    @PostMapping("/create_update_user")
    public UserDto registerAccount(UserDto userDto){
        return userService.updateAcc(userDto, bCryptPasswordEncoder);
    }

    @GetMapping("/user_detail/{id}")
    public UserDto user_detail(@PathVariable("id") Long id){
        return new UserDto(userRepository.getById(id));
    }

    @GetMapping("/remove_user/{id}")
    public Boolean removeUser(@PathVariable("id") Long id){
        userRepository.deleteById(id);
        return true;
    }
}
