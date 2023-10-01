package com.example.maklist.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class AddListingDto {
    private Integer sellerId;
    private String description;
    private BigDecimal price;
    private String name;
    private Integer quantity;
    private List<Integer> categories;
}
