package com.example.maklist.view;

import lombok.Data;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity(name = "listing_seller_buyer_reviews")
@Immutable
public class ListingSellerBuyerReviews implements Serializable {

    @Id
    private Long reviewId;

    private Integer reviewRating;

    private String reviewText;

    private Long listingId;

    private String reviewerName;

    private String listingName;

    private Long sellerId;

    private String sellerName;

    private String sellerSurname;
}
