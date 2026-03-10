package com.klp.order.application.port;

public interface UserProfileInvalidationPublisher {
    void publishUserProfileInvalidation(Long userId);
}
