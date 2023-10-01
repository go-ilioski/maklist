package com.example.maklist.dto;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity(name = "categories")
public class Categories {

    @Id
    private Long id;

    private String name;

    private Long parentCategoryId;

    private String description;

}
