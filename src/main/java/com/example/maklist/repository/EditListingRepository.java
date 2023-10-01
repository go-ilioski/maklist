package com.example.maklist.repository;

import com.example.maklist.dto.AddListingDto;
import com.example.maklist.dto.EditListingDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EditListingRepository extends JpaRepository<EditListingDto, Long> {
    EditListingDto findByIdAndSellerUserId(Long id, Integer sellerId);
}
