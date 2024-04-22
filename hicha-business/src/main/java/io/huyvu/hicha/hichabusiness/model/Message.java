package io.huyvu.hicha.hichabusiness.model;

public record Message(Long messageId, Long conversationId, Long senderId, String messageText, String sentAt) {
}
