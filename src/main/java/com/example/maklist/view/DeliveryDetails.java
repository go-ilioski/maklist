package com.example.maklist.view;

import lombok.Data;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@Entity(name = "delivery_details")
@Immutable
public class DeliveryDetails  implements Serializable {

    @Id
    private Long deliveryId;

    private Long transactionId;

    private Instant transactioDate;

    private BigDecimal transactionAmount;

    private BigDecimal discountAmount;

    private Long listingId;

    private String listingName;

    private Long deliveryCompanyId;

    private String deliveryCompanyName;

    private Long buyerId;

    private Long sellerId;

}
