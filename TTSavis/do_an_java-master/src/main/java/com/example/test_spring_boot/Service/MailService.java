package com.example.test_spring_boot.Service;

import com.example.test_spring_boot.Dto.CartDto;

import java.util.List;

public interface MailService {
    public void sendMail(String toAddress, String subject, Object model, String filePath, String content, List<CartDto> lstCart);
    public void resetPassWord( String toAddress, String content,int tokken);
}
