package com.mkuligowski.cardsapi.cards;

public interface CVVChecker {
    boolean cvvMatch(String cardNumber, String cvv);
}
