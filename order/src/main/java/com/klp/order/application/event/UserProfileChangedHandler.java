package com.klp.order.application.event;

import com.klp.order.application.cache.UserProfileCache;
import com.klp.order.application.cache.UserProfileCacheInvalidationService;
import com.klp.order.infrastructure.event.dto.UserProfileChangedMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserProfileChangedHandler {

//    private final UserProfileCache userProfileCache;
    private final UserProfileCacheInvalidationService invalidationService;

    public void handle(UserProfileChangedMessage msg) {
        switch (msg.eventType()) {
//            case "PROFILE_UPDATED" -> userProfileCache.evictUserProfile(msg.userId());
            case "PROFILE_UPDATED" -> invalidationService.invalidateUserProfile(msg.userId());
            default ->
                log.warn("[UserProfileChangedHandler] Unknown eventType received: {} for userId={}",
                    msg.eventType(), msg.userId());
        }
    }
}
