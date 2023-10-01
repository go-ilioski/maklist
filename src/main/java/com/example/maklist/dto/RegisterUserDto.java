package com.example.maklist.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RegisterUserDto {

    private String name;

    private String surname;

    private String email;

    private String address;

    private String password;

    private BigDecimal phoneNumber;

}
