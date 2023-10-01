package com.example.maklist.repository;

import com.example.maklist.view.ListingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListingDetailsRepository extends JpaRepository<ListingDetails,Long> {
    ListingDetails findByListingId(Long id);
}
