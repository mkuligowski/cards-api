package com.mkuligowski.cardsapi.cards.config;

import com.mkuligowski.cardsapi.cards.CVVChecker;

public class FakeCVVChecker implements CVVChecker {

    public static final String DEFAULT_MATCHING_CVV = "123";

    private String matchingCvv = DEFAULT_MATCHING_CVV;

    @Override
    public boolean cvvMatch(String cardNumber, String cvv) {
        return matchingCvv.equals(cvv);
    }

    public void setMatchingCvv(String matchingCvv) {
        this.matchingCvv = matchingCvv;
    }
}
