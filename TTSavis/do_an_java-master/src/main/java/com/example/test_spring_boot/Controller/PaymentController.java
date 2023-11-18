package com.example.test_spring_boot.Controller;

import com.example.test_spring_boot.Dto.NganLuongDto.*;
import com.example.test_spring_boot.Gateway.Gateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;

@RestController
@RequestMapping(value = "/nganluong")
public class PaymentController {

    @Value("${nganluong.merchantid}")
    String merchantId;

    @Value("${nganluong.merchantpassword}")
    String merchantPassword;

    @Value("${nganluong.merchantemail}")
    String merchantEmail;

    @PostMapping  ("/")
    public ResponseInfo testApi() throws Exception {
        return this.createProfile("EXB", "Nguyen Huu Quyen","nguyenquyen5120@gmail.com","0976490413","ATM_ONLINE");
    }

    public String createProfile_custom(String bankCode, String fullName, String email, String mobile, String paymentMethod) throws Exception {
        Gateway alepayGateway = new Gateway();
        RequestInfo input = clonePayment(bankCode, fullName, email, mobile, paymentMethod);
        String url = alepayGateway.getUrlToken(input);
        return url;
    }

    public ResponseInfo createProfile(String bankCode, String fullName, String email, String mobile, String paymentMethod) throws Exception {
        Gateway alepayGateway = new Gateway();
        RequestInfo input = clonePayment(bankCode, fullName, email, mobile, paymentMethod);
        ResponseInfo responseCreate = alepayGateway.chage(input);
        return responseCreate;
    }
    
    public ResponseCheckOrder checkOrder(String token) throws Exception {
        Gateway alepayGateway = new Gateway();
        RequestCheckOrder input = cloneCheckOrder(token);
        ResponseCheckOrder responseCheckOrder = alepayGateway.checkOrder(input);
        return responseCheckOrder;
    }
    
    private RequestCheckOrder cloneCheckOrder(String token){
        RequestCheckOrder request = new RequestCheckOrder();
        request.setFuntion("GetTransactionDetail");
        request.setVersion("3.1");
        request.setMerchant_id(merchantId);
        request.setMerchant_password(merchantPassword);
        request.setToken(token);
        return request;
    }

    private RequestInfo clonePayment(String bankCode, String fullName, String email, String mobile, String paymentMethod) {
        RequestInfo payment = new RequestInfo();
        try {
            payment.setFuntion("SetExpressCheckout");
            payment.setVersion("3.1");
            payment.setPayment_method(paymentMethod);
            payment.setMerchant_id(merchantId);
            payment.setMerchant_password(merchantPassword);
            payment.setReceiver_email(merchantEmail);
            payment.setCur_code("vnd");
            payment.setBank_code(bankCode);
            payment.setOrder_code("ma_don_hang01");
            payment.setTotal_amount("2000");
            payment.setFee_shipping("0");
            payment.setDiscount_amount("0");
            payment.setTax_amount("0");
            payment.setOrder_description("Thanh toan test thu dong hang");
            payment.setReturn_url("http://localhost:8890/nganluong/success");
            payment.setCancel_url("http://localhost:8890/nganluong/error");
            payment.setBuyer_fullname(URLEncoder.encode(fullName, "UTF-8"));
            payment.setBuyer_email(email);
            payment.setBuyer_mobile(mobile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return payment;
    }
}
