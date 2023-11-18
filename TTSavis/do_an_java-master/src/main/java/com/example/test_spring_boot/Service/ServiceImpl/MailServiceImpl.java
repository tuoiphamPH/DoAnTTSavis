package com.example.test_spring_boot.Service.ServiceImpl;

import com.example.test_spring_boot.Dto.CartDto;
import com.example.test_spring_boot.Dto.ProductDto;
import com.example.test_spring_boot.Repository.ProductRepository;
import com.example.test_spring_boot.Service.MailService;
import com.example.test_spring_boot.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    ProductRepository productRepository;

    @Value("${spring.mail.username}")
    String mailFrom;

    @Autowired
    ProductService productService;

    @Override
    public void sendMail(String toAddress, String subject, Object model, String filePath, String content, List<CartDto> lstCart) {
        try{
            List<ProductDto> lstCartDto = productService.getProductByCartDto(lstCart);
            final Context ctx = new Context(LocaleContextHolder.getLocale());
            ctx.setVariable("lstCartDto", lstCartDto);
            Integer total = 0;
            Double price =0d;
            for (ProductDto p : lstCartDto){
                total += p.getTotalItem();
                price += p.getPrice();
            }
            price += 30000;
            ctx.setVariable("total", total);
            ctx.setVariable("price", price);
            ctx.setVariable("dateBuy",lstCartDto.get(0).getCreateDate());

            final MimeMessage message = this.javaMailSender.createMimeMessage();
            final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, "UTF-8");
            mimeMessageHelper.setTo(toAddress);
            mimeMessageHelper.setFrom(mailFrom);
            mimeMessageHelper.setSubject(subject);
            String templateHtml = templateEngine.process("MailTemplate", ctx);
            mimeMessageHelper.setText(templateHtml, true);
            this.javaMailSender.send(message);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    public void resetPassWord(String toAddress, String content, int tokken) {
        try{
            final Context ctx = new Context(LocaleContextHolder.getLocale());
            ctx.setVariable("token", tokken);
            ctx.setVariable("email", toAddress);
            final MimeMessage message = this.javaMailSender.createMimeMessage();
            final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, "UTF-8");
            mimeMessageHelper.setTo(toAddress);
            mimeMessageHelper.setFrom(mailFrom);
            mimeMessageHelper.setSubject("Thông báo đăng ký lại mật khẩu");
            String templateHtml = templateEngine.process("mailResetPass", ctx);
            mimeMessageHelper.setText(templateHtml, true);
            this.javaMailSender.send(message);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
