package com.klp.order.infrastructure.event.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.klp.order.application.port.UserProfileInvalidationPublisher;
import com.klp.order.infrastructure.event.dto.UserProfileInvalidationMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisUserProfileInvalidationPublisher implements UserProfileInvalidationPublisher {

    private static final String CHANNEL = "cache.invalidate.user-profile";

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void publishUserProfileInvalidation(Long userId) {
        try {
            String payload = objectMapper.writeValueAsString(
                new UserProfileInvalidationMessage(userId)
            );
            redisTemplate.convertAndSend(CHANNEL, payload);
        } catch (Exception e) {
            throw new RuntimeException("Failed to publish cache invalidation message", e);
        }
    }
}