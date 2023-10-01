package com.example.maklist.view;

import lombok.Data;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Entity(name = "auction_pages")
@Immutable
public class AuctionPages {
    @Id
    private Long auctionId;

    private Long listingId;

    private String listingName;

    private Long bidId;

    private BigDecimal biggestBid;

    private Long sellerId;

    private String sellerName;

    private String sellerSurname;

    private BigDecimal minimumBid;
}
