package com.example.maklist.view;

import lombok.Data;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity(name = "listing_details")
@Immutable
public class ListingDetails implements Serializable {

    @Id
    private Long listingId;

    private String listingName;

    private BigDecimal listingPrice;

    private String listingDescription;

    private Long sellerId;

    private String sellerName;

    private String sellerSurname;
}
