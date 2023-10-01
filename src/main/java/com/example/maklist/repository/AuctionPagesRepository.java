package com.example.maklist.repository;

import com.example.maklist.view.AuctionDetails;
import com.example.maklist.view.AuctionPages;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionPagesRepository extends JpaRepository<AuctionPages, Long> {
    Slice<AuctionPages> findBySellerId(Long id, Pageable pageable);
}
