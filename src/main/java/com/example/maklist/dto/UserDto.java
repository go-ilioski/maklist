package com.example.maklist.dto;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@Entity(name = "users")
public class UserDto {

    @Id
    private Long id;

    private String name;

    private String surname;

    private Boolean status;

    private String email;

    private String address;

    //private String password;

    private BigDecimal phoneNumber;

    private Integer type;

}
