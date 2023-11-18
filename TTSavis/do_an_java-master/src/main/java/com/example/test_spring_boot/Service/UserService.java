package com.example.test_spring_boot.Service;

import com.example.test_spring_boot.Dto.SearchDto.SearchReportDto;
import com.example.test_spring_boot.Dto.UserDto;
import com.example.test_spring_boot.Entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpResponse;

public interface UserService {
    UserEntity checkExistUserOauth(String email, String method,String username);
    UserDto registerAcc(UserDto userDto, BCryptPasswordEncoder bCryptPasswordEncoder);
    UserDto updateAcc(UserDto userDto, BCryptPasswordEncoder bCryptPasswordEncoder);
    Page<UserDto> findPage(SearchReportDto searchReportDto);
}
