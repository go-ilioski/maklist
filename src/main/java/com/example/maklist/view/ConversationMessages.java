package com.example.maklist.view;

import lombok.Data;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.Instant;

@Data
@Entity(name = "conversation_messages")
@Immutable
public class ConversationMessages implements Serializable {

    @Id
    private Long conversationId;

    private Long messageId;

    private Instant messageSent;

    private Long listingId;

    private String forListing;

    private String fromUserName;

    private String fromUserSurname;

    private String messageText;

    private Long buyerId;

    private Long sellerId;

}
