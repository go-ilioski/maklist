package com.example.maklist.view;

import lombok.Data;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Entity(name = "wishlist_by_user")
@Immutable
public class WishlistByUser {

    private Long userId;

    private String userName;

    private String userSurname;

    @Id
    private Long wishlistItemId;

    private Long listingId;

    private String listingName;

    private BigDecimal listingPrice;

}
