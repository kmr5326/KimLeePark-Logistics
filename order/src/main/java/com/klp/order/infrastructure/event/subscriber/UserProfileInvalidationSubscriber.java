package com.klp.order.infrastructure.event.subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.klp.order.application.cache.UserProfileCacheInvalidationService;
import com.klp.order.infrastructure.event.dto.UserProfileInvalidationMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserProfileInvalidationSubscriber {

    private final ObjectMapper objectMapper;
    private final UserProfileCacheInvalidationService invalidationService;

    public void onMessage(String message) {
        try {
            UserProfileInvalidationMessage msg =
                objectMapper.readValue(message, UserProfileInvalidationMessage.class);

            invalidationService.invalidateLocalOnly(msg.userId());

            log.debug("[UserProfileInvalidationSubscriber] local cache invalidated. userId={}", msg.userId());
        } catch (Exception e) {
            log.error("[UserProfileInvalidationSubscriber] failed to process message={}", message, e);
        }
    }
}
