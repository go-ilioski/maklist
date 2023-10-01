package com.example.maklist.repository;

import com.example.maklist.view.WishlistByUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistByUserRepository extends JpaRepository<WishlistByUser,Long> {
    Slice<WishlistByUser> findByUserId(Long Id, Pageable pageable);
}
