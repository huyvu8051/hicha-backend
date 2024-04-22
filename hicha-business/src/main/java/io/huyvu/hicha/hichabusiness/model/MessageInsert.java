package io.huyvu.hicha.hichabusiness.model;

public record MessageInsert(Long conversationId, Long senderId, String messageText) {
}
