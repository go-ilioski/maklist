package com.example.maklist.view;

import lombok.Data;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@Entity(name = "listing_page")
@Immutable
public class ListingPage implements Serializable {

    @Id
    private Long listingId;

    private String listingName;

    private Instant dateCreated;

    private BigDecimal price;

    private Long sellerId;

    private String sellerName;

    private String sellerSurname;

}
