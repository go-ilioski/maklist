package com.example.maklist.repository;

import com.example.maklist.view.ListingSellerBuyerReviews;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListingSellerBuyerReviewsRepository extends JpaRepository<ListingSellerBuyerReviews,Long> {
    Slice<ListingSellerBuyerReviews> findByListingId(Long Id, Pageable pageable);
}
