package com.example.maklist.repository;

import com.example.maklist.view.AuctionDetails;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionDetailsRepository extends JpaRepository<AuctionDetails, Long> {
    //AuctionDetails findByBidderId(Long id);
    Slice<AuctionDetails> findByBidderId(Long id,Pageable pageable);
}
