package com.example.maklist.dto;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "listings")
public class EditListingDto {

    @Id
    private Long id;

    private Integer sellerUserId;
    private String description;
    private BigDecimal price;
    private String name;
    private Integer quantity;

}
