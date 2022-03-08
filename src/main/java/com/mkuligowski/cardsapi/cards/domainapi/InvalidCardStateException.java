package com.mkuligowski.cardsapi.cards.domainapi;

public class InvalidCardStateException extends RuntimeException {

    public InvalidCardStateException(String message) {
        super(message);
    }
}
