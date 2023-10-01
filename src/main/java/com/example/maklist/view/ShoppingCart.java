package com.example.maklist.view;

import lombok.Data;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Entity(name = "shopping_cart_items_by_user")
@Immutable
public class ShoppingCart {

    private Long userId;

    private String userName;

    private String userSurname;

    @Id
    private Long shoppingCartItemId;

    private Long listingId;

    private String listingName;

    private BigDecimal listingPrice;
}
