package com.example.maklist.repository;

import com.example.maklist.view.ShoppingCart;
import com.example.maklist.view.WishlistByUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Slice<ShoppingCart> findByUserId(Long Id, Pageable pageable);
}
