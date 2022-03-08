package com.mkuligowski.cardsapi.cards;

import org.springframework.stereotype.Component;

@Component
public class CVVCheckerImpl implements CVVChecker {

    private static final String THE_ONLY_CORRECT_CVV = "999";

    @Override
    public boolean cvvMatch(String cardNumber, String cvv) {
        // here we suppose to connect to some API in order to validate CVV
        return THE_ONLY_CORRECT_CVV.equals(cvv);
    }
}
