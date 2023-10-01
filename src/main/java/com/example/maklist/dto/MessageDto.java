package com.example.maklist.dto;

import lombok.Data;

@Data
public class MessageDto {
    private Integer messageId;
    private String message;
    private Integer conversationId;
    private Integer fromUserId;
}
