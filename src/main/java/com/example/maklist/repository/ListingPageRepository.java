package com.example.maklist.repository;

import com.example.maklist.view.ListingPage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListingPageRepository extends JpaRepository<ListingPage, Long> {
    Slice<ListingPage> findBySellerId(Long id, Pageable pageable);
}
