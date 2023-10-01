package com.example.maklist.repository;

import com.example.maklist.view.DeliveryDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryDetailsRepository extends JpaRepository<DeliveryDetails,Long> {
    //Page<DeliveryDetails> findByListingName(String name, Pageable pageable);
    Slice<DeliveryDetails> findByBuyerId(Long id, Pageable pageable);
    //Slice<DeliveryDetails> findAll(Pageable pageable);
}
