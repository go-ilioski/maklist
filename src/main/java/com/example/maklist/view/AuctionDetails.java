package com.example.maklist.view;

import lombok.Data;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@Entity(name = "auction_details")
@Immutable
public class AuctionDetails implements Serializable {
    @Id
    private Long auctionId;

    private Long listingId;

    private String listingName;

    private String listingDescription;

    private BigDecimal minimumBid;

    private BigDecimal reservePrice;

    private Instant auctionStartDate;
    private Instant auctionEndDate;
    private Long bidId;
    private Long bidderId;
    private String bidderName;
    private String bidderSurname;
    private BigDecimal bidAmount;
}
