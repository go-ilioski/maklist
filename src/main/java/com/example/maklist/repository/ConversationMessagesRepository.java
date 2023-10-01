package com.example.maklist.repository;

import com.example.maklist.view.ConversationMessages;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationMessagesRepository extends JpaRepository<ConversationMessages,Long> {
    Slice<ConversationMessages> findBySellerIdOrBuyerId(Long sid, Long bid , Pageable pageable);
}
