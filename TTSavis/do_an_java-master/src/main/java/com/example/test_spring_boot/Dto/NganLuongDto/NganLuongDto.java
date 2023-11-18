package com.example.test_spring_boot.Dto.NganLuongDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NganLuongDto {
    String bankCode;
    String fullName;
    String email;
    String mobile;
    String paymentMethod;
}
