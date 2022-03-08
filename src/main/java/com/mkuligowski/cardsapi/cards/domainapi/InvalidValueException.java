package com.mkuligowski.cardsapi.cards.domainapi;

public class InvalidValueException extends RuntimeException {

    public InvalidValueException(String message) {
        super(message);
    }
}
