package com.klp.order.application.cache;

import com.klp.order.application.port.UserProfileInvalidationPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserProfileCacheInvalidationService {

    private final UserProfileCache userProfileCache;
    private final UserProfileInvalidationPublisher invalidationPublisher;

    public void invalidateUserProfile(Long userId) {
        userProfileCache.evictUserProfile(userId);

        // 다른 인스턴스들에 브로드캐스트
        invalidationPublisher.publishUserProfileInvalidation(userId);

        log.debug("[UserProfileCacheInvalidationService] invalidated and broadcasted. userId={}", userId);
    }

    public void invalidateLocalOnly(Long userId) {
        userProfileCache.evictLocal(userId);
        log.debug("[UserProfileCacheInvalidationService] local invalidated. userId={}", userId);
    }
}
