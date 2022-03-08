package com.mkuligowski.cardsapi.security;

import org.springframework.stereotype.Component;

@Component
public class OurOnlyCustomerSecurityContextImpl implements SecurityContext {

    private static final int OUR_ONLY_CUSTOMER_ID = 123;

    @Override
    public long getAuthenticatedUserId() {
        return OUR_ONLY_CUSTOMER_ID;
    }
}
