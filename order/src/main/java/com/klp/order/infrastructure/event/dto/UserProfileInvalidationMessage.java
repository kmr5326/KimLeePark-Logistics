package com.klp.order.infrastructure.event.dto;

public record UserProfileInvalidationMessage(
    Long userId
) {
}
